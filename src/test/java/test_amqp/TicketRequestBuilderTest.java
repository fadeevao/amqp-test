package test_amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
