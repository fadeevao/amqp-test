package test_amqp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_amqp.calculator.DistanceCalculator;
import test_amqp.calculator.PriceCalculator;
import test_amqp.calculator.PriceRequestInternal;
import test_amqp.entities.TicketPriceDetails;
import test_amqp.model.*;
import test_amqp.repos.TicketPriceDetailsRepository;

import java.math.BigDecimal;

@Service
public class TicketDistributionService {

    @Autowired
    private DistanceCalculator distanceCalculator;

    @Autowired
    private TicketPriceDetailsRepository ticketPriceDetailsRepository;

    public TicketDistributionService(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    public TicketDistributionService() {}


    public PriceInformation generatePriceInformation(TicketRequest ticketRequest){
        BigDecimal price = calculatePriceBasedOnDistanceAndTicketType(ticketRequest);
        TicketPriceDetails ticketPriceDetails = new TicketPriceDetails();
        ticketPriceDetails.setPrice(price);
        ticketPriceDetails.setTicketType(ticketRequest.getTicketType());
        ticketPriceDetails.setTo(ticketRequest.getJourneyDirections().getTo());
        ticketPriceDetails.setFromDirection(ticketRequest.getJourneyDirections().getFrom());
        ticketPriceDetailsRepository.save(ticketPriceDetails);
        return new PriceInformation.PriceInformationBuilder().withJourneyDirections(ticketRequest.getJourneyDirections())
                .withTicketType(ticketRequest.getTicketType())
                .withTotalPrice(price)
                .withTicketId(ticketPriceDetails.getId())
                .build();
    }


    private BigDecimal calculatePriceBasedOnDistanceAndTicketType(TicketRequest ticketRequest) {
        JourneyDirections journeyDirections = ticketRequest.getJourneyDirections();
        BigDecimal distance = distanceCalculator.calculateDistance(journeyDirections.getFrom(), journeyDirections.getTo());
        PriceRequestInternal priceRequestInternal = PriceRequestInternal.PriceRequestInternalBuilder.aPriceRequestInternal()
                .withStudentPrice(ticketRequest.isStudentTicket())
                .withDistance(distance)
                .withNumberOfTickets(ticketRequest.getNumberOfTickets())
                .withTicketType(ticketRequest.getTicketType())
                .build();
        return PriceCalculator.calculatePricePerDistanceTicketTypeAndNumberofTickets(priceRequestInternal);
    }

    public Ticket generateTicket(TicketPayment payment) {
        TicketPriceDetails ticketPriceDetails = ticketPriceDetailsRepository.findById(payment.getTicketId());
        BigDecimal expectedPayment = ticketPriceDetails.getPrice();
        BigDecimal paidAmount = payment.getPaymentAmount();
        JourneyDirections journeyDirections = new JourneyDirections(ticketPriceDetails.getFromDirection(), ticketPriceDetails.getTo());
        Ticket ticket;
        if (expectedPayment.compareTo(paidAmount) == 0) {
            ticket = new Ticket(payment.getPaymentAmount(), journeyDirections, ticketPriceDetails.getTicketType());
            ticketPriceDetailsRepository.delete(ticketPriceDetails);
            return ticket;
        } else if (expectedPayment.compareTo(paidAmount) > 0) {
            BigDecimal leftToPay = expectedPayment.subtract(paidAmount);
            ticketPriceDetails.setPrice(leftToPay);
            ticketPriceDetailsRepository.save(ticketPriceDetails);
            PriceInformation priceInformation = new PriceInformation.PriceInformationBuilder().withJourneyDirections(journeyDirections)
                    .withTicketType(ticketPriceDetails.getTicketType())
                    .withTotalPrice(leftToPay)
                    .withTicketId(ticketPriceDetails.getId())
                    .build();
            return priceInformation;
        }
        return new Ticket();
    }
}
