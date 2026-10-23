package ecommerce.context;

import ecommerce.discount.DiscountStrategy;
import ecommerce.model.Cart;

import java.math.BigDecimal;
import java.util.Objects;

public class CheckoutContext {
    private DiscountStrategy strategy;

    public CheckoutContext(DiscountStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy must not be null");
    }

    public void setDiscountStrategy(DiscountStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy must not be null");
    }

    public BigDecimal calculateTotal(Cart cart) {
        Objects.requireNonNull(cart, "cart must not be null");
        return strategy.applyDiscount(cart.getSubtotal());
    }

    public String getActiveStrategyDescription() {
        return strategy.getDescription();
    }
}