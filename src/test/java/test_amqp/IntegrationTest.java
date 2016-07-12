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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import test_amqp.api.TicketRequestController;
import test_amqp.calculator.DistanceCalculator;
import test_amqp.entities.TicketPriceDetails;
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
@ContextConfiguration(classes = {TicketDistributionService.class, DistanceCalculator.class, JourneyTicketsAmqp.class})
@WebAppConfiguration
public class IntegrationTest {

    @Mock
    RabbitTemplate template;

    @InjectMocks
    @Autowired
    TicketRequestController ticketRequestController;

    @Autowired
    MessageConverter messageConverter;

    @Autowired
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
                Object mock = invocation.getMock();
                return ticketRequestProcesor.receiveTicketRequestAndProcess((Message)args[1]);
            }
        });

        Mockito.when(template.sendAndReceive(eq("payment"), any(Message.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws Exception {
                Object[] args = invocation.getArguments();
                Object mock = invocation.getMock();
                return ticketRequestProcesor.receivePaymentAndGenerateTicket((Message)args[1]);
            }
        });
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
        ticketPriceDetailsRepository.deleteAll();
    }

    @Test
    public void testStatusOkIsReturned() throws Exception {
        mockMvc.perform(
                post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(withContent("json/validModel/ValidTicketRequest.json")))
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
                        .content(withContent("json/validModel/ValidTicketPayment.json")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketType", is("RETURN")))
                .andExpect(jsonPath("$.journeyDirections.to", is("HOVE")))
                .andExpect(jsonPath("$.journeyDirections.from", is("BRIGHTON")))
                .andExpect(jsonPath("$.totalPrice", equalTo(6.0)));

    }

    private String withContent(String filePath) throws IOException {
        return IOUtils.toString(getClass().getClassLoader().getResourceAsStream(filePath));
    }

}
