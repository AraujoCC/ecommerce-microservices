package com.ecommerce.payment.domain.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class PaymentProcessedEvent {

    private final String eventId;
    private final UUID paymentId;
    private final UUID orderId;
    private final UUID customerId;
    private final BigDecimal amount;
    private final String status;
    private final String occurredAt;

    public PaymentProcessedEvent(UUID paymentId, UUID orderId, UUID customerId,
                                  BigDecimal amount, String status) {
        this.eventId = UUID.randomUUID().toString();
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.status = status;
        this.occurredAt = Instant.now().toString();
    }

    public String getEventId() { return eventId; }
    public UUID getPaymentId() { return paymentId; }
    public UUID getOrderId() { return orderId; }
    public UUID getCustomerId() { return customerId; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus() { return status; }
    public String getOccurredAt() { return occurredAt; }
}