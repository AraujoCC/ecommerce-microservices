package com.ecommerce.notification.domain.event;

import java.math.BigDecimal;

public class OrderCreatedEvent {
    private String orderId;
    private String customerId;
    private BigDecimal totalAmount;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(String orderId, String customerId, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}
