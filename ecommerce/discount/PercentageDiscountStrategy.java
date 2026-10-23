package ecommerce.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class PercentageDiscountStrategy implements DiscountStrategy {
    private static final int PERCENT_BASE = 100;
    private static final int MONEY_SCALE = 2;

    private final int percentage;

    public PercentageDiscountStrategy(int percentage) {
        if (percentage < 0 || percentage > PERCENT_BASE) {
            throw new IllegalArgumentException(
                    "Percentage must be between 0 and 100, got: " + percentage);
        }
        this.percentage = percentage;
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal amount) {
        Objects.requireNonNull(amount, "amount must not be null");
        BigDecimal discountAmount = amount.multiply(BigDecimal.valueOf(percentage))
                .divide(BigDecimal.valueOf(PERCENT_BASE), MONEY_SCALE, RoundingMode.HALF_UP);
        return amount.subtract(discountAmount)
                .setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescription() {
        return percentage + "% off";
    }
}
