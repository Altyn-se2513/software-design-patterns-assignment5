package ecommerce.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class NoDiscountStrategy implements DiscountStrategy {
    private static final int MONEY_SCALE = 2;

    @Override
    public BigDecimal applyDiscount(BigDecimal amount) {
        Objects.requireNonNull(amount, "amount must not be null");
        return amount.setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescription() {
        return "No Discount";
    }
}
