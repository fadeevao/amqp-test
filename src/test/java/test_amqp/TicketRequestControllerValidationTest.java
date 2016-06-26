package test_amqp;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import test_amqp.api.TicketRequestController;
import test_amqp.calculator.DistanceCalculator;
import test_amqp.repos.TicketPriceDetailsRepository;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TicketRequestController.class, TicketDistributionService.class, DistanceCalculator.class, JourneyTicketsAmqp.class})
@WebAppConfiguration
public class TicketRequestControllerValidationTest {

    @Mock
    RabbitTemplate template;

    @InjectMocks
    TicketRequestController ticketRequestController;

    @Mock
    MessageConverter messageConverter;

    @Mock
    TicketDistributionService ticketDistributionService;

    @Autowired
    TicketPriceDetailsRepository ticketPriceDetailsRepository;

    @Autowired
    private WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(template).send(any(Message.class));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
    }

    @Test
    public void testBadRequestIsReturnedWhenTicketTypeIsMissing() throws Exception {
        mockMvc.perform(
                post("/ticket")
                        .contentType("application/json")
                        .content(withContent("json/invalidModel/MissingTicketType.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBadRequestIsReturnedWhenDateTimeIsMissing() throws Exception {
        mockMvc.perform(
                post("/ticket")
                        .contentType("application/json")
                        .content(withContent("json/invalidModel/MissingDateTime.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBadRequestIsReturnedWhenDateTimeFormatIsWrong() throws Exception {
        mockMvc.perform(
                post("/ticket")
                        .contentType("application/json")
                        .content(withContent("json/invalidModel/DateTimeWrongFormat.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBadRequestIsReturnedWhenNumberOfTicketsIsMissing() throws Exception {
        mockMvc.perform(
                post("/ticket")
                        .contentType("application/json")
                        .content(withContent("json/invalidModel/NumberOfTicketsIsZero.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBadRequestIsReturnedWhenJourneyDirectionsIsMissing() throws Exception {
        mockMvc.perform(
                post("/ticket")
                        .contentType("application/json")
                        .content(withContent("json/invalidModel/JourneyDirectionsMissing.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBadRequestIsReturnedWhenJourneyDirectionToIsMissing() throws Exception {
        mockMvc.perform(
                post("/ticket")
                        .contentType("application/json")
                        .content(withContent("json/invalidModel/ToDirectionMissing.json")))
                .andExpect(status().isBadRequest());
    }


    private String withContent(String filePath) throws IOException {
        return IOUtils.toString(getClass().getClassLoader().getResourceAsStream(filePath));
    }

}
