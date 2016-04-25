package test_amqp;


import org.joda.time.DateTime;

import java.io.Serializable;

public class TicketRequest implements Serializable{

    private TicketType ticketType;

    private DateTime dateTime;

    private boolean student;

    private boolean adult;

    private Integer numberOfTickets;

    private JourneyDirections journeyDirections;

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public JourneyDirections getJourneyDirections() {
        return journeyDirections;
    }

    public void setJourneyDirections(JourneyDirections journeyDirections) {
        this.journeyDirections = journeyDirections;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    @Override
    public String toString() {
        return ticketType.description + "Ticket Request from " + journeyDirections.getFrom() + " to " + journeyDirections.getTo();
    }
}
