package test_amqp;


import org.joda.time.DateTime;

import java.io.Serializable;

public class TicketRequest implements Serializable{

    private TicketType ticketType;

    private DateTime dateTime;

    private boolean isStudentTicket;

    private boolean isAdultTicket;

    private Integer numberOfTickets;

    private JourneyDirections journeyDirections;

    public TicketRequest(TicketType ticketType, DateTime dateTime, boolean isStudentTicket, boolean adult, Integer numberOfTickets, JourneyDirections journeyDirections) {
        this.ticketType = ticketType;
        this.dateTime = dateTime;
        this.isStudentTicket = isStudentTicket;
        this.isAdultTicket = adult;
        this.numberOfTickets = numberOfTickets;
        this.journeyDirections = journeyDirections;
    }

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

    public boolean isStudentTicket() {
        return isStudentTicket;
    }

    public void setStudentTicket(boolean studentTicket) {
        this.isStudentTicket = studentTicket;
    }

    public boolean isAdultTicket() {
        return isAdultTicket;
    }

    public void setAdultTicket(boolean adultTicket) {
        this.isAdultTicket = adultTicket;
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

    public static class TicketRequestBuilder {
        private TicketType ticketType;

        private DateTime dateTime;

        private boolean student;

        private boolean adult;

        private Integer numberOfTickets;

        private JourneyDirections journeyDirections;

        public TicketRequestBuilder withTicketType(TicketType type) {
            ticketType = type;
            return this;
        }

        public TicketRequestBuilder withDateTime(DateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public TicketRequestBuilder withStudentOption(boolean isStudentTicket) {
            student = isStudentTicket;
            return this;
        }

        public TicketRequestBuilder withAdultOption(boolean isAdultTicket) {
            adult = isAdultTicket;
            return this;
        }

        public TicketRequestBuilder withNumberOfTickets(Integer numberOfTickets) {
            this.numberOfTickets = numberOfTickets;
            return this;
        }

        public TicketRequestBuilder withJourneyDirections(JourneyDirections journeyDirections) {
            this.journeyDirections = journeyDirections;
            return this;
        }

        public TicketRequest build() {
            return new TicketRequest(ticketType, dateTime, student, adult, numberOfTickets, journeyDirections);
        }
    }
}
