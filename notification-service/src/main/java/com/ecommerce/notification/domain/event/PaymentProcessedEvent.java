package com.ecommerce.notification.domain.event;

import java.math.BigDecimal;

public class PaymentProcessedEvent {
    private String paymentId;
    private String orderId;
    private BigDecimal amount;
    private String status;

    public PaymentProcessedEvent() {}

    public PaymentProcessedEvent(String paymentId, String orderId, BigDecimal amount, String status) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
    }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
