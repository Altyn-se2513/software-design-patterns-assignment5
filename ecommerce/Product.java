package ecommerce.model;

import java.math.BigDecimal;
import java.util.Objects;

public record Product(String id, String name, BigDecimal price) {
    public Product {
        Objects.requireNonNull(price, "price must not be null");
        if (price.signum() < 0) {
            throw new IllegalArgumentException("Price must not be negative, got: " + price);
        }
    }
}
