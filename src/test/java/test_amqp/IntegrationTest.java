package test_amqp;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
import test_amqp.testConfig.TestQueueConfig;
import test_amqp.api.TicketRequestController;
import test_amqp.calculator.DistanceCalculator;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TicketRequestController.class, TestQueueConfig.class, TicketDistributionService.class, DistanceCalculator.class})
@WebAppConfiguration
public class IntegrationTest {

    @Mock
    RabbitTemplate template;

    @InjectMocks
    TicketRequestController ticketRequestController;

    @Mock
    MessageConverter messageConverter;

    @Mock
    TicketDistributionService ticketDistributionService;

    @Autowired
    private WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(template).send(any(Message.class));
        doReturn(new Message("Message".getBytes(), new MessageProperties())).when(template).sendAndReceive(any(Message.class));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
    }

    @Test
    public void testStatusOkIsReturned() throws Exception {
        mockMvc.perform(
                post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(withContent("json/validModel/ValidTicketRequest.json")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.ticketType", is("RETURN")))
                .andExpect(jsonPath("$.journeyDirections.to", is("HOVE")))
                .andExpect(jsonPath("$.journeyDirections.from", is("BRIGHTON")));
    }

    private String withContent(String filePath) throws IOException {
        return IOUtils.toString(getClass().getClassLoader().getResourceAsStream(filePath));
    }

}
