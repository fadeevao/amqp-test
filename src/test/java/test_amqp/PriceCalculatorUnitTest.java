package test_amqp;

import org.junit.Test;
import test_amqp.calculator.PriceCalculator;
import test_amqp.calculator.PriceRequestInternal;
import test_amqp.model.TicketType;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PriceCalculatorUnitTest {

    @Test
    public void calculateStudentPriceOneTicketSingleType() {
        PriceRequestInternal priceRequestInternal = PriceRequestInternal.PriceRequestInternalBuilder.aPriceRequestInternal()
                .withTicketType(TicketType.SINGLE)
                .withStudentPrice(true)
                .withNumberOfTickets(1)
                .withDistance(new BigDecimal(100))
                .build();
        assertEquals(new BigDecimal("40.00"), PriceCalculator.calculatePricePerDistanceTicketTypeAndNumberofTickets(priceRequestInternal));
    }

    @Test
    public void calculateStandardPriceOneTicketSingleType() {
        PriceRequestInternal priceRequestInternal = PriceRequestInternal.PriceRequestInternalBuilder.aPriceRequestInternal()
                .withTicketType(TicketType.SINGLE)
                .withStudentPrice(false)
                .withNumberOfTickets(1)
                .withDistance(new BigDecimal(100))
                .build();
        assertEquals(new BigDecimal("50.0"), PriceCalculator.calculatePricePerDistanceTicketTypeAndNumberofTickets(priceRequestInternal));
    }
}
