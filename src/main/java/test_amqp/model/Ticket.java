package test_amqp.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

@JsonSerialize
public class Ticket {

    protected BigDecimal totalPrice;

    protected JourneyDirections journeyDirections;

    protected TicketType ticketType;

    public Ticket(BigDecimal totalPrice, JourneyDirections journeyDirections, TicketType ticketType) {
        this.totalPrice = totalPrice;
        this.journeyDirections = journeyDirections;
        this.ticketType = ticketType;
    }

    public Ticket() {}


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
}
