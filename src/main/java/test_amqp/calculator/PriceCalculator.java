package test_amqp.calculator;

import test_amqp.model.TicketType;

import java.math.BigDecimal;

public class PriceCalculator {
    private static final BigDecimal PRICE_PER_KM = new BigDecimal("0.5");

    private static final BigDecimal STUDENT_DISCOUNT = new BigDecimal("0.2");

    private static final BigDecimal RETURN_TICKET_RATE = new BigDecimal("1.5");

    public static BigDecimal calculatePricePerDistanceTicketTypeAndNumberofTickets(PriceRequestInternal priceRequestInternal) {
        BigDecimal standardPricePerOneTicket = priceRequestInternal.getDistance().multiply(PRICE_PER_KM);
        BigDecimal totalPrice = standardPricePerOneTicket.multiply(BigDecimal.valueOf(priceRequestInternal.getNumberOfTickets()));
        if (priceRequestInternal.getTicketType().equals(TicketType.RETURN)) {
            totalPrice = totalPrice.multiply(RETURN_TICKET_RATE);
        }
        if (priceRequestInternal.isStudentPrice()) {
            return totalPrice.subtract(totalPrice.multiply(STUDENT_DISCOUNT));
        }

        return totalPrice;
    }
}
