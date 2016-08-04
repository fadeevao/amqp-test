package test_amqp;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;
import test_amqp.api.TicketRequestController;
import test_amqp.calculator.GoogleMapsDistanceCalculator;
import test_amqp.entities.TicketPriceDetails;
import test_amqp.exception.TicketReferenceNotFoundException;
import test_amqp.model.Direction;
import test_amqp.repos.TicketPriceDetailsRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TicketDistributionService.class, GoogleMapsDistanceCalculator.class, JourneyTicketsAmqp.class})
@WebAppConfiguration
@PropertySource("classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationTest {

    @Mock
    RabbitTemplate template;

    @InjectMocks
    @Autowired
    TicketRequestController ticketRequestController;

    @Autowired
    MessageConverter messageConverter;

    @Mock
    GoogleMapsDistanceCalculator googleMapsDistanceCalculator;

    @Autowired
    @InjectMocks
    TicketDistributionService ticketDistributionService;

    @Autowired
    TicketPriceDetailsRepository ticketPriceDetailsRepository;

    @Autowired
    private WebApplicationContext wac;

    MockMvc mockMvc;
    
    @Autowired
    TicketRequestProcessor ticketRequestProcesor = new TicketRequestProcessor(ticketDistributionService, messageConverter);

    @Before
    public void setUp() {
    	
        MockitoAnnotations.initMocks(this);
        Mockito.when(template.sendAndReceive(eq("request"), any(Message.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws Exception {
                Object[] args = invocation.getArguments();
                return ticketRequestProcesor.receiveTicketRequestAndProcess((Message)args[1]);
            }
        });

        Mockito.when(template.sendAndReceive(eq("payment"), any(Message.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws Exception {
                Object[] args = invocation.getArguments();
                return ticketRequestProcesor.receivePaymentAndGenerateTicket((Message)args[1]);
            }
        });
        Mockito.when(googleMapsDistanceCalculator.calculateDistance(any(Direction.class), any(Direction.class))).thenReturn(new BigDecimal("10.0"));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
        ticketPriceDetailsRepository.deleteAll();
    }



    @Test
    public void testHappyPath() throws Exception {
        mockMvc.perform(
                post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(withContent("json/validModel/request/ValidTicketRequest.json")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketType", is("RETURN")))
                .andExpect(jsonPath("$.journeyDirections.to", is("HOVE")))
                .andExpect(jsonPath("$.journeyDirections.from", is("BRIGHTON")))
                .andExpect(jsonPath("$.totalPrice", equalTo(6.0)))
                .andExpect(jsonPath("$.ticketId", equalTo(1)));
        assertEquals(((Collection< TicketPriceDetails>)ticketPriceDetailsRepository.findAll()).size(), 1);
        TicketPriceDetails ticketPriceDetails = ticketPriceDetailsRepository.findAll().iterator().next();
        assertEquals(new Long(1), ticketPriceDetails.getId());
        assertEquals(new BigDecimal("6.00"), ticketPriceDetails.getPrice());

        mockMvc.perform(
                post("/ticket/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(withContent("json/validModel/payment/ValidTicketPayment.json")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketType", is("RETURN")))
                .andExpect(jsonPath("$.journeyDirections.to", is("HOVE")))
                .andExpect(jsonPath("$.journeyDirections.from", is("BRIGHTON")))
                .andExpect(jsonPath("$.totalPrice", equalTo(6.0)));

        assertEquals(((Collection< TicketPriceDetails>)ticketPriceDetailsRepository.findAll()).size(), 0);
    }

    @Test
    public void testInsufficientPayment() throws Exception {
        postTicket();

        mockMvc.perform(
                post("/ticket/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(withContent("json/validModel/payment/InsufficientTicketPayment.json")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketType", is("RETURN")))
                .andExpect(jsonPath("$.journeyDirections.to", is("HOVE")))
                .andExpect(jsonPath("$.journeyDirections.from", is("BRIGHTON")))
                .andExpect(jsonPath("$.totalPrice", equalTo(5.0)))
                .andExpect(jsonPath("$.ticketId", equalTo(1)));

        assertEquals(((Collection< TicketPriceDetails>)ticketPriceDetailsRepository.findAll()).size(), 1);
        TicketPriceDetails ticketPriceDetails = ticketPriceDetailsRepository.findAll().iterator().next();
        assertEquals(new Long(1), ticketPriceDetails.getId());
        assertEquals(new BigDecimal("5.00"), ticketPriceDetails.getPrice());
    }

    @Test(expected = NestedServletException.class)
    public void testExceptionThrownWhenTicketPaymentReferenceNotFound() throws Exception {
        try {
            mockMvc.perform(
                    post("/ticket/payment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(withContent("json/validModel/payment/InsufficientTicketPayment.json")))
                    .andExpect(status().isBadRequest());
        } catch (TicketReferenceNotFoundException e) {
            assertEquals(e.getCause().getClass(), TicketReferenceNotFoundException.class);
            assertEquals(e.getCause().getMessage(), "Ticket with the given ID not found: 1");
        }
    }

    @Test
    public void testPaymentWithChangeRequired() throws Exception {
        postTicket();

        mockMvc.perform(
                post("/ticket/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(withContent("json/validModel/payment/TicketPaymentGreaterThanRequired.json")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketType", is("RETURN")))
                .andExpect(jsonPath("$.journeyDirections.to", is("HOVE")))
                .andExpect(jsonPath("$.journeyDirections.from", is("BRIGHTON")))
                .andExpect(jsonPath("$.totalPrice", equalTo(7.0)))
                .andExpect(jsonPath("$.change", equalTo(1.0)));

        assertEquals(((Collection< TicketPriceDetails>)ticketPriceDetailsRepository.findAll()).size(), 0);
    }

    private void postTicket() throws Exception {
        mockMvc.perform(
                post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(withContent("json/validModel/request/ValidTicketRequest.json")));
    }

    private String withContent(String filePath) throws IOException {
        return IOUtils.toString(getClass().getClassLoader().getResourceAsStream(filePath));
    }

}
