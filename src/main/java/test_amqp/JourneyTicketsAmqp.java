package test_amqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import test_amqp.config.QueueConfig;

@SpringBootApplication
@EnableJpaRepositories
public class JourneyTicketsAmqp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(JourneyTicketsAmqp.class, args);
    }
}