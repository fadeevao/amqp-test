package test_amqp.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonSerialize
public class Ticket implements Serializable{

    private BigDecimal totalPrice;

    private JourneyDirections journeyDirections;

    private TicketType ticketType;


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

    @Override
    public String toString() {
        return "Ticket with ticket type: " + ticketType.toString() + ", journey directions: " + journeyDirections.toString();
    }

    public static class TicketBuilder {
        private BigDecimal totalPrice;

        private JourneyDirections journeyDirections;

        private TicketType ticketType;

        public TicketBuilder withTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public TicketBuilder withJourneyDirections(JourneyDirections journeyDirections) {
            this.journeyDirections = journeyDirections;
            return this;
        }


        public TicketBuilder withTicketType(TicketType ticketType) {
            this.ticketType = ticketType;
            return this;
        }

        public Ticket build() {
            return new Ticket(totalPrice, journeyDirections, ticketType);
        }
    }
}
