package test_amqp.calculator;


import org.springframework.stereotype.Component;
import test_amqp.model.Direction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DistanceCalculator {


    public  BigDecimal calculateDistance(Direction from, Direction to) {
        if (from.reachableDirections.containsKey(to)) {
            return from.reachableDirections.get(to);
        }
        return calculateDistanceBasedOnStops(from, to);
    }


    private BigDecimal calculateDistanceBasedOnStops(Direction from, Direction to) {
        List<Direction> stops = new ArrayList<>(Arrays.asList(from));
        to.visited = true;
        from.visited = true;
        findPath(from, to, stops);
        BigDecimal distance = BigDecimal.ZERO;
        for (int i =0; i<stops.size()-1; i++) {
            distance = distance.add(distanceBetween(stops.get(i), stops.get(i+1)));
        }

        resetBooleanValues();
        return distance;
    }

    private void findPath(Direction from, Direction to, List<Direction> stops) {
        for (Direction direction : from.reachableDirections.keySet()) {
            if (direction.reachableDirections.containsKey(to) && !direction.visited) {
                direction.visited=true;
                addRequiredStopsToThePath(from, to, stops, direction);
                findPath(from, direction, stops);
            } else if (!direction.visited) {
                direction.visited=true;
                findPath(direction, to, stops);
            }
        }
    }

    private void addRequiredStopsToThePath(Direction from, Direction to, List<Direction> stops, Direction direction) {
        if (!stops.contains(from)) stops.add(from);
        if (!stops.contains(direction)) stops.add(direction);
        if (!stops.contains(to))stops.add(to);
    }

    private void resetBooleanValues() {
        for (Direction direction : Direction.values()) {
            direction.visited = false;
        }
    }

    private BigDecimal distanceBetween(Direction from, Direction to) {
        return from.reachableDirections.get(to);
    }

}
