package com.ecommerce.order.interfaces.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItemRequest {

    @NotNull(message = "ProductId e obrigatorio")
    private UUID productId;

    @NotBlank(message = "ProductName e obrigatorio")
    private String productName;

    @Min(value = 1, message = "Quantity deve ser ao menos 1")
    private int quantity;

    @NotNull
    @DecimalMin(value = "0.01", message = "UnitPrice deve ser maior que zero")
    private BigDecimal unitPrice;

    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}
