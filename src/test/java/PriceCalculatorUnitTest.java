import org.junit.Test;
import test_amqp.PriceCalculator;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PriceCalculatorUnitTest {

    @Test
    public void calculateStudentPrice() {
        assertEquals(new BigDecimal("40.00"), PriceCalculator.calculatePricePerDistanceAndNumberofTickets(new BigDecimal(100), true, 1));
    }

    @Test
    public void calculateStandardPrice() {
        assertEquals(new BigDecimal("50.0"), PriceCalculator.calculatePricePerDistanceAndNumberofTickets(new BigDecimal(100), false, 1));
    }
}
