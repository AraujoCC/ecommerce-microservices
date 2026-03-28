package com.ecommerce.order.domain.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class OrderCreatedEvent {

    private final String eventId;
    private final UUID orderId;
    private final UUID customerId;
    private final BigDecimal totalAmount;
    private final String occurredAt;

    public OrderCreatedEvent(UUID orderId, UUID customerId, BigDecimal totalAmount) {
        this.eventId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.occurredAt = Instant.now().toString();
    }

    public String getEventId() { return eventId; }
    public UUID getOrderId() { return orderId; }
    public UUID getCustomerId() { return customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getOccurredAt() { return occurredAt; }
}