package com.ecommerce.inventory.infrastructure.messaging.consumer;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderCreatedMessage {
    private UUID orderId;
    private UUID customerId;
    private BigDecimal totalAmount;
    private List<OrderItemMessage> items;

    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public List<OrderItemMessage> getItems() { return items; }
    public void setItems(List<OrderItemMessage> items) { this.items = items; }

    public static class OrderItemMessage {
        private UUID productId;
        private int quantity;
        private BigDecimal unitPrice;

        public UUID getProductId() { return productId; }
        public void setProductId(UUID productId) { this.productId = productId; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    }
}