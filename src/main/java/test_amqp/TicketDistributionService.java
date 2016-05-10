package test_amqp;

import org.springframework.stereotype.Service;
import test_amqp.model.JourneyDirections;
import test_amqp.model.Ticket;
import test_amqp.model.TicketRequest;

import java.math.BigDecimal;

@Service
public class TicketDistributionService {

    public Ticket processTicketRequest(TicketRequest ticketRequest){
        return new Ticket.TicketBuilder().withJourneyDirections(ticketRequest.getJourneyDirections())
                .withTicketType(ticketRequest.getTicketType())
                .withTotalPrice(calculatePrice(ticketRequest.getJourneyDirections()))
                .withDiscount(calculateDiscount(ticketRequest))
                .build();
    }

    private BigDecimal calculateDiscount(TicketRequest ticketRequest) {
        return BigDecimal.ONE;
    }

    private BigDecimal calculatePrice(JourneyDirections journeyDirections) {
        return BigDecimal.ONE;
    }
}
