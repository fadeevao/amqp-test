package test_amqp;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.ObjectOutputStream;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TicketRequestController {
    Logger logger = Logger.getLogger(TicketRequestController.class);

    @Autowired
    RabbitTemplate template;


    @RequestMapping(value = "/ticket", method = POST)
    @ResponseBody
    public ResponseEntity receiveTicketRequest(@RequestBody TicketRequest ticketRequest) {
        logger.info("Received a ticket request" + ticketRequest.toString());
        template.convertAndSend("queue.routing.key", buildMessage(ticketRequest));

        return new ResponseEntity(HttpStatus.OK);
    }

    private Message buildMessage(TicketRequest request) {
        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/json");
        Message message = new Message(convertRequestToBytes(request) ,properties);
        return message;
    }

    private byte[] convertRequestToBytes(TicketRequest request) {
        byte[] bytes;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);
            oos.flush();
            oos.reset();
            bytes = baos.toByteArray();
            oos.close();
            baos.close();
        } catch(IOException e){
            bytes = new byte[] {};
            logger.info("Failed to convert request to byte array");
        }
        return bytes;
    }

}
