package com.ecommerce.inventory.domain.event;

import java.time.Instant;
import java.util.UUID;

public class InventoryReservedEvent {

    private final String eventId;
    private final UUID orderId;
    private final String occurredAt;

    public InventoryReservedEvent(UUID orderId) {
        this.eventId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.occurredAt = Instant.now().toString();
    }

    public String getEventId() { return eventId; }
    public UUID getOrderId() { return orderId; }
    public String getOccurredAt() { return occurredAt; }
}