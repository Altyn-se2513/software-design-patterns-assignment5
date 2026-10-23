package ecommerce.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class FixedAmountDiscountStrategy implements DiscountStrategy {
    private static final int MONEY_SCALE = 2;

    private final BigDecimal discountAmount;
    private final BigDecimal minimumSpend;

    public FixedAmountDiscountStrategy(BigDecimal discountAmount, BigDecimal minimumSpend) {
        this.discountAmount = Objects.requireNonNull(discountAmount, "discountAmount must not be null");
        this.minimumSpend = Objects.requireNonNull(minimumSpend, "minimumSpend must not be null");

        if (this.discountAmount.signum() < 0) {
            throw new IllegalArgumentException("Discount amount must be non-negative, got: " + this.discountAmount);
        }
        if (this.minimumSpend.signum() < 0) {
            throw new IllegalArgumentException("Minimum spend must be non-negative, got: " + this.minimumSpend);
        }
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal amount) {
        Objects.requireNonNull(amount, "amount must not be null");

        if (amount.compareTo(minimumSpend) < 0) {
            return amount.setScale(MONEY_SCALE, RoundingMode.HALF_UP);
        }

        return amount.subtract(discountAmount).max(BigDecimal.ZERO)
                .setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescription() {
        return "$" + discountAmount + " off orders of $" + minimumSpend + " or more";
    }
}
