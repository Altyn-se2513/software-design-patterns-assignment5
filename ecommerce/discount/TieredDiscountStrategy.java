package ecommerce.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class TieredDiscountStrategy implements DiscountStrategy {
    private static final int PERCENT_BASE = 100;
    private static final int MONEY_SCALE = 2;

    private static final BigDecimal FIRST_THRESHOLD = new BigDecimal("500.00");
    private static final BigDecimal SECOND_THRESHOLD = new BigDecimal("1000.00");
    private static final int FIRST_RATE = 5;
    private static final int SECOND_RATE = 15;

    @Override
    public BigDecimal applyDiscount(BigDecimal amount) {
        Objects.requireNonNull(amount, "amount must not be null");

        int rate = 0;

        if (amount.compareTo(SECOND_THRESHOLD) >= 0) {
            rate = SECOND_RATE;
        } else if (amount.compareTo(FIRST_THRESHOLD) >= 0) {
            rate = FIRST_RATE;
        }

        BigDecimal discount = amount.multiply(BigDecimal.valueOf(rate))
                .divide(BigDecimal.valueOf(PERCENT_BASE), MONEY_SCALE, RoundingMode.HALF_UP);

        return amount.subtract(discount)
                .max(BigDecimal.ZERO)
                .setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescription() {
        return FIRST_RATE + "% off orders of $" + FIRST_THRESHOLD + " or more"
                + ", " + SECOND_RATE + "% off orders of $" + SECOND_THRESHOLD + " or more";
    }
}
