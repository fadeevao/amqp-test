package test_amqp.model;


import java.math.BigDecimal;

public class TicketPayment {

    private BigDecimal paymentAmount;

    private Long ticketId;

    public TicketPayment() {}

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
}
