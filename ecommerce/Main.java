package ecommerce;

import ecommerce.context.CheckoutContext;
import ecommerce.discount.FixedAmountDiscountStrategy;
import ecommerce.discount.NoDiscountStrategy;
import ecommerce.discount.PercentageDiscountStrategy;
import ecommerce.discount.TieredDiscountStrategy;
import ecommerce.model.Cart;
import ecommerce.model.Product;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.addProduct(new Product("1", "Laptop", new BigDecimal("800.00")));
        cart.addProduct(new Product("2", "Headphones", new BigDecimal("150.00")));
        cart.addProduct(new Product("3", "Mouse", new BigDecimal("50.00")));

        System.out.println("Cart Subtotal: $" + cart.getSubtotal());

        CheckoutContext checkout = new CheckoutContext(new NoDiscountStrategy());
        printTotal(checkout, cart);

        checkout.setDiscountStrategy(new PercentageDiscountStrategy(10));
        printTotal(checkout, cart);

        checkout.setDiscountStrategy(new FixedAmountDiscountStrategy(
                new BigDecimal("50.00"),
                new BigDecimal("900.00")
        ));
        printTotal(checkout, cart);

        checkout.setDiscountStrategy(new TieredDiscountStrategy());
        printTotal(checkout, cart);
    }

    private static void printTotal(CheckoutContext checkout, Cart cart) {
        System.out.println(checkout.getActiveStrategyDescription()
                + " -> Final: $" + checkout.calculateTotal(cart));
    }
}
