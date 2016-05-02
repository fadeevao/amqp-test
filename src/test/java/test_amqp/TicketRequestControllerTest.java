package test_amqp;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TicketRequestController.class, QueueConfiguration.class})
@WebAppConfiguration
public class TicketRequestControllerTest {

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
    public void testStatusOkIsReturned() throws Exception {
        MvcResult result = this.mockMvc.perform(
                post("/ticket")
                        .contentType("application/json")
                        .content(readFile()))
                .andExpect(status().isOk())
                .andReturn();
    }

    private String readFile() {

        FileInputStream fisTargetFile = null;
        try {
            fisTargetFile = new FileInputStream(new File("/home/olga/IntellijProjects/amqp-test/src/test/java/test_amqp/ValidTicketRequest.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String json = "";
        try {
            json = org.apache.commons.io.IOUtils.toString(fisTargetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

}
