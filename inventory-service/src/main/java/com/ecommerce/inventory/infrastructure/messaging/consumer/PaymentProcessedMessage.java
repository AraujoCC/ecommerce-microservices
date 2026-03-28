package com.ecommerce.inventory.infrastructure.messaging.consumer;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentProcessedMessage {
    private UUID orderId;
    private UUID customerId;
    private BigDecimal amount;
    private String status;

    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}