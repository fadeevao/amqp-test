package test_amqp.queue;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test_amqp.api.TicketRequestController;
import test_amqp.model.TicketRequest;

@EnableRabbit
@Component
public class QueueListener {

    @Autowired
    AmqpTemplate template;

    @Autowired
    TicketRequestController controller;

    @Autowired
    MessageConverter messageConverter;

    Logger logger = Logger.getLogger(QueueListener.class);

    @RabbitListener(queues = "queue")
    public void onMessage(Message message) {
        System.out.println("Listening to the queue...");
        try {
            TicketRequest request = (TicketRequest) messageConverter.fromMessage(message);
        } catch (MessageConversionException e) {
            e.printStackTrace();
        }
        //logger.info("Received a ticket request: "  + request);
    }
}
