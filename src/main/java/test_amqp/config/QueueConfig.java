package test_amqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.net.URISyntaxException;

@EnableRabbit
@Configuration
@ComponentScan(basePackages = "test_amqp.calculator")
public class QueueConfig {

    private final static String EXCHANGE= "service";
    private final static String REQUEST_ROUTING_KEY = "request";
    private final static String PAYMENT_ROUTING_KEY = "payment";

    @Bean
    public CachingConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        return connectionFactory;
    }

    @Bean
    public static PropertyPlaceholderConfigurer properties() throws URISyntaxException {
        PropertyPlaceholderConfigurer proprty =  new PropertyPlaceholderConfigurer();
        proprty.setLocations(new Resource[] {
                new ClassPathResource("application.properties")});
        return proprty;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setConnectionFactory(rabbitConnectionFactory());
        rabbitTemplate.setExchange(EXCHANGE);
        return rabbitTemplate;
    }

    @Bean
    public Queue requestQueue() {
        return new Queue("requestQueue");
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue("paymentQueue");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE, false, false);
    }

    @Bean
    public Binding requestQueueBinding() {
        return BindingBuilder.bind(requestQueue()).to(directExchange()).with(REQUEST_ROUTING_KEY);
    }

    @Bean
    public Binding paymentQueueBinding() {
        return BindingBuilder.bind(paymentQueue()).to(directExchange()).with(PAYMENT_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return rabbitTemplate().getMessageConverter();
    }

}
