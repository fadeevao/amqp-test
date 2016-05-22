package test_amqp.TestConfig;


import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import test_amqp.DistanceCalculator;
import test_amqp.TicketDistributionService;
import test_amqp.TicketRequestProcesor;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class TestQueueConfig {


    private TicketRequestProcesor ticketRequestProcesor = new TicketRequestProcesor(ticketDistributionService(), messageConverter());

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);

        doNothing().when(rabbitTemplate).send(any(Message.class));



//        doReturn(messageConverter().toMessage(new Ticket.TicketBuilder()
//                .withTicketType(TicketType.RETURN)
//                .withJourneyDirections(new JourneyDirections(Direction.BRIGHTON, Direction.HOVE))
//                .withTotalPrice(BigDecimal.TEN).build(),
//                new MessageProperties()))
//                .when(rabbitTemplate).sendAndReceive(any(Message.class));

        Mockito.when(rabbitTemplate.sendAndReceive(any(Message.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws Exception {
                Object[] args = invocation.getArguments();
                Object mock = invocation.getMock();
                return ticketRequestProcesor.receiveTicketRequestAndProcess((Message)args[0]);
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public DistanceCalculator distanceCalculator() {
        return new DistanceCalculator();
    }

    @Bean
    public TicketDistributionService ticketDistributionService () {
        return new TicketDistributionService(distanceCalculator());
    }

}
