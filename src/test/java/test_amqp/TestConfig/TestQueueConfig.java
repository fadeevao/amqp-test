package test_amqp.TestConfig;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import test_amqp.model.Direction;
import test_amqp.model.JourneyDirections;
import test_amqp.model.Ticket;
import test_amqp.model.TicketType;

import java.math.BigDecimal;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TestQueueConfig {
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
        doNothing().when(rabbitTemplate).send(any(Message.class));
        doReturn(messageConverter().toMessage(new Ticket.TicketBuilder()
                .withTicketType(TicketType.RETURN)
                .withJourneyDirections(new JourneyDirections(Direction.BRIGHTON, Direction.HOVE))
                .withTotalPrice(BigDecimal.TEN).build(),
                new MessageProperties()))
                .when(rabbitTemplate).sendAndReceive(any(Message.class));
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
