package test_amqp.model;

public enum TicketType {

    SINGLE("Single ticket"),
    RETURN("Return ticket");

    String description;

     TicketType(String description) {
        this.description = description;
    }
}
