package test_amqp;

import java.math.BigDecimal;

public class PriceCalculator {
    private static final BigDecimal PRICE_PER_KM = new BigDecimal("0.5");

    private static final BigDecimal STUDENT_DISCOUNT = new BigDecimal("0.2");

    public static BigDecimal calculatePricePerDistance(BigDecimal distance, boolean studentPrice) {
        BigDecimal standardPrice = distance.multiply(PRICE_PER_KM);
        if (studentPrice) {
            return standardPrice.subtract(standardPrice.multiply(STUDENT_DISCOUNT));
        }
        return standardPrice;
    }
}
