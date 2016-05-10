package test_amqp.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonSerialize
public class Ticket implements Serializable{

    private BigDecimal totalPrice;

    private JourneyDirections journeyDirections;

    private BigDecimal discount;

    private TicketType ticketType;

    public Ticket(BigDecimal totalPrice, JourneyDirections journeyDirections, TicketType ticketType) {
        this(totalPrice, journeyDirections, null, ticketType);
    }

    public Ticket(BigDecimal totalPrice, JourneyDirections journeyDirections, BigDecimal discount, TicketType ticketType) {
        this.totalPrice = totalPrice;
        this.journeyDirections = journeyDirections;
        this.discount = discount;
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

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public static class TicketBuilder {
        private BigDecimal totalPrice;

        private JourneyDirections journeyDirections;

        private BigDecimal discount;

        private TicketType ticketType;

        public TicketBuilder withTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public TicketBuilder withJourneyDirections(JourneyDirections journeyDirections) {
            this.journeyDirections = journeyDirections;
            return this;
        }

        public TicketBuilder withDiscount(BigDecimal discount) {
            this.discount = discount;
            return this;
        }

        public TicketBuilder withTicketType(TicketType ticketType) {
            this.ticketType = ticketType;
            return this;
        }

        public Ticket build() {
            return new Ticket(totalPrice, journeyDirections, discount, ticketType);
        }
    }
}
