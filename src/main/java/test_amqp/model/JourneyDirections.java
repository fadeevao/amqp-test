package test_amqp.model;


import javax.validation.constraints.NotNull;

public class JourneyDirections {

    @NotNull(message = "Journey starting point can't be empty")
    private Direction from;

    @NotNull(message = "Journey destination must be provided")
    private Direction to;

    public JourneyDirections(Direction from, Direction to) {
        this.from = from;
        this.to = to;
    }

    public JourneyDirections() {}

    public Direction getFrom() {
        return from;
    }

    public void setFrom(Direction from) {
        this.from = from;
    }

    public Direction getTo() {
        return to;
    }

    public void setTo(Direction directionTo) {
        to = directionTo;
    }

    @Override
    public String toString() {
        return "to: " + String.valueOf(to) + ", from: " + String.valueOf(from);
    }
}
