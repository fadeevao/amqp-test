package test_amqp;


public class JourneyDirections {

    private String from;

    private String to;

    public JourneyDirections(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public JourneyDirections() {}

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String directionTo) {
        to = directionTo;
    }

}
