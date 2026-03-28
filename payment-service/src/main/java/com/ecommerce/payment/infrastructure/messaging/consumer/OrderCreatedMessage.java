package com.ecommerce.payment.infrastructure.messaging.consumer;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderCreatedMessage {
    private UUID orderId;
    private UUID customerId;
    private BigDecimal totalAmount;
    private String occurredAt;

    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getOccurredAt() { return occurredAt; }
    public void setOccurredAt(String occurredAt) { this.occurredAt = occurredAt; }
}