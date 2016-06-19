package test_amqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;
import test_amqp.api.TicketRequestController;
import test_amqp.config.QueueConfig;

@SpringBootApplication
@ContextConfiguration(classes = {TicketRequestController.class, QueueConfig.class})
public class AmqpTest {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AmqpTest.class, args);
    }
}