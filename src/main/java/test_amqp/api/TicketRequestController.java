package test_amqp.api;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import test_amqp.MessageHelper;
import test_amqp.TicketDistributionService;
import test_amqp.model.PriceInformation;
import test_amqp.model.TicketRequest;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@EnableWebMvc
public class TicketRequestController {
    Logger logger = Logger.getLogger(TicketRequestController.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MessageConverter messageConverter;

    @Autowired
    TicketDistributionService ticketDistributionService;

    @RequestMapping(value = "/ticket", method = POST, consumes = "application/json")
    public  ResponseEntity receiveTicketRequest(@Valid @RequestBody TicketRequest ticketRequest) {
        logger.info("Received a ticket request" + ticketRequest.toString());
        Message message = rabbitTemplate.sendAndReceive(MessageHelper.buildMessage(ticketRequest, messageConverter));
        PriceInformation ticket;
        if (message != null) {
             ticket = (PriceInformation) messageConverter.fromMessage(message);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(ticket, HttpStatus.OK);

    }

}
