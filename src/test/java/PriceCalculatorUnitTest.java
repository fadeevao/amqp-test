import org.junit.Test;
import test_amqp.PriceCalculator;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PriceCalculatorUnitTest {

    @Test
    public void calculateStudentPrice() {
        assertEquals(new BigDecimal("40.00"), PriceCalculator.calculatePricePerDistance(new BigDecimal("100"), true));
    }

    @Test
    public void calculateStandardPrice() {
        assertEquals(new BigDecimal("50.00"), PriceCalculator.calculatePricePerDistance(new BigDecimal(100), false));
    }
}
