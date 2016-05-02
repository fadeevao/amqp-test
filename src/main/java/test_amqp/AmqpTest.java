package test_amqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class AmqpTest {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AmqpTest.class, args);
    }
}