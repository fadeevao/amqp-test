package test_amqp.api;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import test_amqp.model.TicketRequest;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@EnableWebMvc
public class TicketRequestController {
    Logger logger = Logger.getLogger(TicketRequestController.class);

    @Autowired
    RabbitTemplate template;

    @Autowired
    MessageConverter messageConverter;


    @RequestMapping(value = "/ticket", method = POST, consumes = "application/json")
    public @ResponseBody  ResponseEntity receiveTicketRequest(@Valid @RequestBody TicketRequest ticketRequest) {
        logger.info("Received a ticket request" + ticketRequest.toString());
        template.send(buildMessage(ticketRequest));
        return new ResponseEntity(HttpStatus.OK);
    }


    private Message buildMessage(TicketRequest request) {
        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/json");
        Message message = messageConverter.toMessage(request, properties);
        return message;
    }

}
