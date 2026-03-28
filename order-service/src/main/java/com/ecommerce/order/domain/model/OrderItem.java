package com.ecommerce.order.domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class OrderItem {

    private final UUID productId;
    private final String productName;
    private final int quantity;
    private final BigDecimal unitPrice;

    public OrderItem(UUID productId, String productName, int quantity, BigDecimal unitPrice) {
        if (productId == null)
            throw new IllegalArgumentException("ProductId e obrigatorio");
        if (productName == null || productName.isBlank())
            throw new IllegalArgumentException("ProductName e obrigatorio");
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity deve ser maior que zero");
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("UnitPrice deve ser nao-negativo");

        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public BigDecimal subtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public UUID getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem that)) return false;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
