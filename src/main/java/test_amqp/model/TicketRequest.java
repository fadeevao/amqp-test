package test_amqp.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketRequest implements Serializable{

    @NotNull
    private TicketType ticketType;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date dateTime;

    private boolean isStudentTicket;

    private boolean isAdultTicket;

    @NotNull
    @Min(1)
    private Integer numberOfTickets;

    @NotNull
    @Valid
    private JourneyDirections journeyDirections;

    public TicketRequest(TicketType ticketType, Date dateTime, boolean isStudentTicket, boolean adult, Integer numberOfTickets, JourneyDirections journeyDirections) {
        this.ticketType = ticketType;
        this.dateTime = dateTime;
        this.isStudentTicket = isStudentTicket;
        this.isAdultTicket = adult;
        this.numberOfTickets = numberOfTickets;
        this.journeyDirections = journeyDirections;
    }

    //for jackson
    public TicketRequest() {}

    public JourneyDirections getJourneyDirections() {
            return journeyDirections;
    }

    public void setJourneyDirections(JourneyDirections journeyDirections) {
        this.journeyDirections = journeyDirections;
    }

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public boolean isAdultTicket() {
        return isAdultTicket;
    }

    public void setAdultTicket(boolean adultTicket) {
        isAdultTicket = adultTicket;
    }

    public boolean isStudentTicket() {
        return isStudentTicket;
    }

    public void setStudentTicket(boolean studentTicket) {
        isStudentTicket = studentTicket;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }


    @Override
    public String toString() {
        if (ticketType != null && journeyDirections != null) {
            return ticketType.description + "PriceInformation Request from " + journeyDirections.getFrom() + " to " + journeyDirections.getTo();
        } else {
            return "PriceInformation request";
        }
    }

    public static class TicketRequestBuilder {
        private TicketType ticketType;

        private Date dateTime;

        private boolean student;

        private boolean adult;

        private Integer numberOfTickets;

        private JourneyDirections journeyDirections;

        public TicketRequestBuilder withTicketType(TicketType type) {
            ticketType = type;
            return this;
        }

        public TicketRequestBuilder withDateTime(Date dateTime) {
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
