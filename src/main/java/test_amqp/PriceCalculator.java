package test_amqp;

import java.math.BigDecimal;

public class PriceCalculator {
    private static final BigDecimal PRICE_PER_KM = new BigDecimal("0.5");

    private static final BigDecimal STUDENT_DISCOUNT = new BigDecimal("0.2");

    public static BigDecimal calculatePricePerDistanceAndNumberofTickets(BigDecimal distance, boolean studentPrice, int numberOfTickets) {
        BigDecimal standardPricePerOneTicket = distance.multiply(PRICE_PER_KM);
        BigDecimal totalPrice = standardPricePerOneTicket.multiply(BigDecimal.valueOf(numberOfTickets));
        if (studentPrice) {
            return totalPrice.subtract(totalPrice.multiply(STUDENT_DISCOUNT));
        }
        return totalPrice;
    }
}
