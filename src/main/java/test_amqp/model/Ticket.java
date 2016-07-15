package test_amqp.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Ticket {

    @DecimalMin("0.00")
    protected BigDecimal totalPrice;

    @NotNull
    protected JourneyDirections journeyDirections;

    @NotNull
    protected TicketType ticketType;


    protected BigDecimal change;

    public Ticket(BigDecimal totalPrice, JourneyDirections journeyDirections, TicketType ticketType) {
        this.totalPrice = totalPrice;
        this.journeyDirections = journeyDirections;
        this.ticketType = ticketType;
    }

    public Ticket(BigDecimal totalPrice, JourneyDirections journeyDirections, TicketType ticketType, BigDecimal change) {
        this(totalPrice, journeyDirections, ticketType);
        this.change = change;
    }

    public Ticket() {}


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
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
