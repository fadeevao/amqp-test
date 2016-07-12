package test_amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test_amqp.model.PriceInformation;
import test_amqp.model.Ticket;
import test_amqp.model.TicketPayment;
import test_amqp.model.TicketRequest;

@Component
public class TicketRequestProcessor {

	@Autowired
    TicketDistributionService ticketDistributionService;
    
    @Autowired
    MessageConverter messageConverter;
    
    public TicketRequestProcessor() {}

    @Autowired
    public TicketRequestProcessor(TicketDistributionService ticketDistributionService, MessageConverter messageConverter) {
        this.ticketDistributionService = ticketDistributionService;
        this.messageConverter = messageConverter;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "requestQueue", durable = "true"),
            exchange = @Exchange(value = "service"),
            key = "request")
    )
    public Message receiveTicketRequestAndProcess(Message request) throws Exception {
        TicketRequest ticketRequest = (TicketRequest) messageConverter.fromMessage(request);
        PriceInformation ticket = ticketDistributionService.generatePriceInformation(ticketRequest);
        return MessageHelper.buildMessage(ticket, messageConverter);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "paymentQueue", durable = "true"),
            exchange = @Exchange(value = "service"),
            key = "payment")
    )
    public Message receivePaymentAndGenerateTicket(Message payment) throws Exception {
        TicketPayment ticketRequest = (TicketPayment) messageConverter.fromMessage(payment);
        Ticket ticket = ticketDistributionService.generateTicket(ticketRequest);
        return MessageHelper.buildMessage(ticket, messageConverter);
    }



}
