package test_amqp;


import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static test_amqp.model.Direction.*;

public class DistanceCalculatorUnitTest {

    DistanceCalculator distanceCalculator = new DistanceCalculator();
    @Test
    public void testDistanceWhenToIsReachableDirectly() {
        assertEquals( new BigDecimal(10), distanceCalculator.calculateDistance(BRIGHTON, HOVE));
        assertEquals( new BigDecimal(10), distanceCalculator.calculateDistance(HOVE, BRIGHTON));
        assertEquals(new BigDecimal(100), distanceCalculator.calculateDistance(BRIGHTON, GATWICK_AIRPORT));
        assertEquals(new BigDecimal(12), distanceCalculator.calculateDistance(HOVE, SHOREHAM_BY_SEA));
    }

    @Test
    public void testDistanceIsCalculatedCorrectlyWhenNoReachableDirectly() {
        assertEquals( new BigDecimal(300), distanceCalculator.calculateDistance(BRIGHTON, LONDON_VICTORIA));
        assertEquals(new BigDecimal(40), distanceCalculator.calculateDistance(BRIGHTON, WORTHING));
        assertEquals(new BigDecimal(310), distanceCalculator.calculateDistance(HOVE, LONDON_VICTORIA));
        assertEquals(new BigDecimal(22), distanceCalculator.calculateDistance(BRIGHTON, SHOREHAM_BY_SEA));
    }

}
