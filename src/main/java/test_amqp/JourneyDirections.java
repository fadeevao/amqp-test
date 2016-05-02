package test_amqp;


import javax.validation.constraints.NotNull;

public class JourneyDirections {

    @NotNull(message = "Journey starting point can't be empty")
    private String from;

    @NotNull(message = "Journey destination must be provided")
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
