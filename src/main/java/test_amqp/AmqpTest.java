package test_amqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmqpTest {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AmqpTest.class, args);
    }
}