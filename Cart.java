package ecommerce.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    private final List<Product> items = new ArrayList<>();

    public void addProduct(Product product) {
        items.add(Objects.requireNonNull(product, "product must not be null"));
    }

    public BigDecimal getSubtotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Product p : items) {
            total = total.add(p.price());
        }
        return total;
    }
}