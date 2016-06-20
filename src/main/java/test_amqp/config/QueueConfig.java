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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class QueueConfig {

    private final static String ROUTING_KEY = "routing.key";
    private final static String EXCHANGE= "exchange";
    @Bean
    public CachingConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        return connectionFactory;
    }


    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setConnectionFactory(rabbitConnectionFactory());
        rabbitTemplate.setExchange(EXCHANGE);
        rabbitTemplate.setRoutingKey(ROUTING_KEY);
        return rabbitTemplate;
    }

    @Bean
    public Queue queue() {
        return new Queue("queue");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE, false, false);
    }

    @Bean
    public Binding bindQueue() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return rabbitTemplate().getMessageConverter();
    }
}
