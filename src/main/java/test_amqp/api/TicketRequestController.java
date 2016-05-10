package test_amqp.api;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import test_amqp.TicketDistributionService;
import test_amqp.model.Ticket;
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
    TicketDistributionService ticketDistributionService;

    @RequestMapping(value = "/ticket", method = POST, consumes = "application/json")
    public @ResponseBody  ResponseEntity receiveTicketRequest(@Valid @RequestBody TicketRequest ticketRequest) {
        logger.info("Received a ticket request" + ticketRequest.toString());
        Message message = rabbitTemplate.sendAndReceive(buildMessage(ticketRequest));
        if (message != null) {
            Ticket ticket = (Ticket) rabbitTemplate.getMessageConverter().fromMessage(message);
        }
        return new ResponseEntity(HttpStatus.OK);

    }

    @RabbitListener(queues = "queue")
    public Message receiveTicketRequestAndProcess(Message request) throws Exception {
        TicketRequest ticketRequest = (TicketRequest) rabbitTemplate.getMessageConverter().fromMessage(request);
        Ticket ticket = ticketDistributionService.processTicketRequest(ticketRequest);
        return buildMessage(ticket);
    }

    private Message buildMessage(Object object) {
        MessageProperties messageProperties = new MessageProperties();
        return rabbitTemplate.getMessageConverter().toMessage(object, messageProperties);
    }

}
