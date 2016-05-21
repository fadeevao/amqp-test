package test_amqp;


import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static test_amqp.model.Direction.*;

public class DistanceCalculatorUnitTest {

    DistanceCalculator distanceCalculator = new DistanceCalculator();
    @Test
    public void testDistanceWhenToIsReachableDirectly() {
        assertEquals(distanceCalculator.calculateDistance(BRIGHTON, HOVE), new BigDecimal(10));
        assertEquals(distanceCalculator.calculateDistance(HOVE, BRIGHTON), new BigDecimal(10));
        assertEquals(distanceCalculator.calculateDistance(BRIGHTON, GATWICK_AIRPORT), new BigDecimal(100));
        assertEquals(distanceCalculator.calculateDistance(HOVE, SHOREHAM_BY_SEA), new BigDecimal(12));
    }

    @Test
    public void testDistanceIsCalculatedCorrectlyWhenNoReachableDirectly() {
        assertEquals( new BigDecimal(300), distanceCalculator.calculateDistance(BRIGHTON, LONDON_VICTORIA));
        assertEquals(new BigDecimal(40), distanceCalculator.calculateDistance(BRIGHTON, WORTHING));
        assertEquals(new BigDecimal(310), distanceCalculator.calculateDistance(HOVE, LONDON_VICTORIA));
        assertEquals(new BigDecimal(22), distanceCalculator.calculateDistance(BRIGHTON, SHOREHAM_BY_SEA));
    }

}
