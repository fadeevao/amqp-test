package test_amqp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_amqp.model.JourneyDirections;
import test_amqp.model.Ticket;
import test_amqp.model.TicketRequest;

import java.math.BigDecimal;

@Service
public class TicketDistributionService {

    @Autowired
    private DistanceCalculator distanceCalculator;

    public TicketDistributionService(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    public TicketDistributionService() {}


    public Ticket processTicketRequest(TicketRequest ticketRequest){
        return new Ticket.TicketBuilder().withJourneyDirections(ticketRequest.getJourneyDirections())
                .withTicketType(ticketRequest.getTicketType())
                .withTotalPrice(calculatePrice(ticketRequest.getJourneyDirections(), ticketRequest.isStudentTicket(),  ticketRequest.getNumberOfTickets()))
                .build();
    }


    private BigDecimal calculatePrice(JourneyDirections journeyDirections, boolean studentTicket, int numberOfTickets) {
        BigDecimal distance = distanceCalculator.calculateDistance(journeyDirections.getFrom(), journeyDirections.getTo());
        return PriceCalculator.calculatePricePerDistanceAndNumberofTickets(distance, studentTicket, numberOfTickets);
    }
}
