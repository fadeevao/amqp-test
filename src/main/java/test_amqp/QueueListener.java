package test_amqp;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@EnableRabbit
@Component
public class QueueListener {

    @Autowired
    AmqpTemplate template;

    @Autowired
    TicketRequestController controller;

    Logger logger = Logger.getLogger(QueueListener.class);

    @RabbitListener(queues = "queue")
    public void onMessage(Message message) {
        Object deserialiedMessage = deserialize(message.getBody());
        TicketRequest request;
        if (deserialiedMessage instanceof  TicketRequest) {
             request = (TicketRequest) deserialiedMessage;
             logger.info("Received a ticket request: " + request );
        } else {
            logger.info("Received a non ticket-request");
        }
    }

    public  Object deserialize(byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);

        Object object = null;
        try {
            ObjectInputStream is = new ObjectInputStream(in);
            object = is.readObject();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }
}
