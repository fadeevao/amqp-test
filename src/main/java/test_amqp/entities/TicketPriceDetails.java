package test_amqp.entities;

import test_amqp.model.Direction;
import test_amqp.model.TicketType;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class TicketPriceDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_direction")
    private Direction to;


    @Enumerated(EnumType.STRING)
    @Column(name="from_direction")
    private Direction fromDirection;


    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type")
    private TicketType ticketType;

    public TicketPriceDetails() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Direction getFromDirection() {
        return fromDirection;
    }

    public Direction getTo() {
        return to;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public void setTo(Direction to) {
        this.to = to;
    }

    public void setFromDirection(Direction fromDirection) {
        this.fromDirection = fromDirection;
    }
}
