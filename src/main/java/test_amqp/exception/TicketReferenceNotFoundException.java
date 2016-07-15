package test_amqp.exception;


public class TicketReferenceNotFoundException extends Exception{

    public TicketReferenceNotFoundException(String msg) {
        super(msg);
    }
}
