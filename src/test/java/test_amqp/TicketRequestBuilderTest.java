package test_amqp;

import org.junit.Test;
import test_amqp.model.JourneyDirections;
import test_amqp.model.TicketRequest;
import test_amqp.model.TicketType;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TicketRequestBuilderTest {

    @Test
    public void testBuildTicketRequest() {
        TicketType type = TicketType.RETURN;
        Date dateTime = new Date();
        boolean  student = true;
        boolean adult = true;
        Integer numberOfTickets = 5;
        JourneyDirections journeyDirections = new JourneyDirections("Riga", "London");

        TicketRequest ticketRequest = new TicketRequest.TicketRequestBuilder().withTicketType(type)
                .withDateTime(dateTime).withStudentOption(student)
                .withAdultOption(adult).withNumberOfTickets(numberOfTickets)
                .withJourneyDirections(journeyDirections).build();

        assertEquals(ticketRequest.getTicketType(), type);
        assertEquals(ticketRequest.getDateTime(), dateTime);
        assertEquals(ticketRequest.getJourneyDirections(), journeyDirections);
        assertEquals(ticketRequest.getNumberOfTickets(), numberOfTickets);
        assertEquals(ticketRequest.isAdultTicket(), adult);
        assertEquals(ticketRequest.isStudentTicket(), student);
    }
}
