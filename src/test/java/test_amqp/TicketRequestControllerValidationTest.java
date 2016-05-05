package test_amqp;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import test_amqp.api.TicketRequestController;
import test_amqp.config.QueueConfiguration;

import java.io.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TicketRequestController.class, QueueConfiguration.class})
@WebAppConfiguration
public class TicketRequestControllerValidationTest {

    @Mock
    RabbitTemplate template;

    @InjectMocks
    TicketRequestController ticketRequestController = new TicketRequestController();

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
        MvcResult result = mockMvc.perform(
                post("/ticket")
                        .contentType("application/json")
                        .content(withFile("/home/olga/IntellijProjects/amqp-test/src/test/resources/json/invalidModel/MissingTicketType.json")))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testBadRequestIsReturnedWhenDateTimeIsMissing() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/ticket")
                        .contentType("application/json")
                        .content(withFile("/home/olga/IntellijProjects/amqp-test/src/test/resources/json/invalidModel/MissingDateTime.json")))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testBadRequestIsReturnedWhenDateTimeFormatIsWrong() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/ticket")
                        .contentType("application/json")
                        .content(withFile("/home/olga/IntellijProjects/amqp-test/src/test/resources/json/invalidModel/DateTimeWrongFormat.json")))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testBadRequestIsReturnedWhenNumberOfTicketsIsMissing() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/ticket")
                        .contentType("application/json")
                        .content(withFile("/home/olga/IntellijProjects/amqp-test/src/test/resources/json/invalidModel/NumberOfTicketsIsZero.json")))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testBadRequestIsReturnedWhenJourneyDirectionsIsMissing() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/ticket")
                        .contentType("application/json")
                        .content(withFile("/home/olga/IntellijProjects/amqp-test/src/test/resources/json/invalidModel/JourneyDirectionsMissing.json")))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testBadRequestIsReturnedWhenJourneyDirectionToIsMissing() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/ticket")
                        .contentType("application/json")
                        .content(withFile("/home/olga/IntellijProjects/amqp-test/src/test/resources/json/invalidModel/ToDirectionMissing.json")))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    private String withFile(String filePath) throws IOException {
        return FileUtils.readFileToString(new File(filePath));
    }

}
