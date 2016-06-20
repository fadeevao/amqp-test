package test_amqp.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonSerialize
public class PriceInformation implements Serializable{

    private BigDecimal totalPrice;

    private JourneyDirections journeyDirections;

    private TicketType ticketType;

    private Long ticketId;


    public PriceInformation(BigDecimal totalPrice, JourneyDirections journeyDirections, TicketType ticketType, Long ticketId) {
        this.totalPrice = totalPrice;
        this.journeyDirections = journeyDirections;
        this.ticketType = ticketType;
        this.ticketId = ticketId;
    }

    public PriceInformation() {}

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

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "PriceInformation with ticket type: " + ticketType.toString() + ", journey directions: " + journeyDirections.toString();
    }

    public static class PriceInformationBuilder {
        private BigDecimal totalPrice;

        private JourneyDirections journeyDirections;

        private TicketType ticketType;

        private Long ticketId;

        public PriceInformationBuilder withTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public PriceInformationBuilder withJourneyDirections(JourneyDirections journeyDirections) {
            this.journeyDirections = journeyDirections;
            return this;
        }

        public PriceInformationBuilder withTicketId(Long id) {
            this.ticketId = id;
            return this;
        }


        public PriceInformationBuilder withTicketType(TicketType ticketType) {
            this.ticketType = ticketType;
            return this;
        }

        public PriceInformation build() {
            return new PriceInformation(totalPrice, journeyDirections, ticketType, ticketId);
        }
    }
}
