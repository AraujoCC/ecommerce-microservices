package com.ecommerce.inventory.domain.event;

import java.time.Instant;
import java.util.UUID;

public class InventoryReleasedEvent {

    private final String eventId;
    private final UUID orderId;
    private final String reason;
    private final String occurredAt;

    public InventoryReleasedEvent(UUID orderId, String reason) {
        this.eventId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.reason = reason;
        this.occurredAt = Instant.now().toString();
    }

    public String getEventId() { return eventId; }
    public UUID getOrderId() { return orderId; }
    public String getReason() { return reason; }
    public String getOccurredAt() { return occurredAt; }
}