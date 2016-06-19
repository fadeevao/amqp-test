package test_amqp.calculator;

import test_amqp.model.TicketType;

import java.math.BigDecimal;

public class PriceRequestInternal {

    public PriceRequestInternal() {}

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    private BigDecimal distance;

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public boolean isStudentPrice() {
        return studentPrice;
    }

    public void setStudentPrice(boolean studentPrice) {
        this.studentPrice = studentPrice;
    }

    private boolean studentPrice;
    private int numberOfTickets;
    private TicketType ticketType;

    public static final class PriceRequestInternalBuilder {
        private BigDecimal distance;
        private boolean studentPrice;
        private int numberOfTickets;
        private TicketType ticketType;

        private PriceRequestInternalBuilder() {
        }

        public static PriceRequestInternalBuilder aPriceRequestInternal() {
            return new PriceRequestInternalBuilder();
        }

        public PriceRequestInternalBuilder withDistance(BigDecimal distance) {
            this.distance = distance;
            return this;
        }

        public PriceRequestInternalBuilder withStudentPrice(boolean studentPrice) {
            this.studentPrice = studentPrice;
            return this;
        }

        public PriceRequestInternalBuilder withNumberOfTickets(int numberOfTickets) {
            this.numberOfTickets = numberOfTickets;
            return this;
        }

        public PriceRequestInternalBuilder withTicketType(TicketType ticketType) {
            this.ticketType = ticketType;
            return this;
        }

        public PriceRequestInternal build() {
            PriceRequestInternal priceRequestInternal = new PriceRequestInternal();
            priceRequestInternal.setDistance(distance);
            priceRequestInternal.setStudentPrice(studentPrice);
            priceRequestInternal.setNumberOfTickets(numberOfTickets);
            priceRequestInternal.setTicketType(ticketType);
            return priceRequestInternal;
        }
    }
}
