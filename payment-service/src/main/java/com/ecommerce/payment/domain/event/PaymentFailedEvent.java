package com.ecommerce.payment.domain.event;

import java.time.Instant;
import java.util.UUID;

public class PaymentFailedEvent {

    private final String eventId;
    private final UUID paymentId;
    private final UUID orderId;
    private final UUID customerId;
    private final String reason;
    private final String occurredAt;

    public PaymentFailedEvent(UUID paymentId, UUID orderId, UUID customerId, String reason) {
        this.eventId = UUID.randomUUID().toString();
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.reason = reason;
        this.occurredAt = Instant.now().toString();
    }

    public String getEventId() { return eventId; }
    public UUID getPaymentId() { return paymentId; }
    public UUID getOrderId() { return orderId; }
    public UUID getCustomerId() { return customerId; }
    public String getReason() { return reason; }
    public String getOccurredAt() { return occurredAt; }
}