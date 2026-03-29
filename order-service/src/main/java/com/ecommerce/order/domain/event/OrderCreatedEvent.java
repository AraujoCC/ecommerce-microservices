package com.ecommerce.order.domain.event;

import com.ecommerce.order.domain.model.OrderItem;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class OrderCreatedEvent {

    private final String eventId;
    private final UUID orderId;
    private final UUID customerId;
    private final BigDecimal totalAmount;
    private final List<OrderItemData> items;
    private final String occurredAt;

    public OrderCreatedEvent(UUID orderId, UUID customerId,
                             BigDecimal totalAmount, List<OrderItem> items) {
        this.eventId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.items = items.stream()
                .map(i -> new OrderItemData(i.getProductId(), i.getQuantity(), i.getUnitPrice()))
                .toList();
        this.occurredAt = Instant.now().toString();
    }

    public String getEventId() { return eventId; }
    public UUID getOrderId() { return orderId; }
    public UUID getCustomerId() { return customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public List<OrderItemData> getItems() { return items; }
    public String getOccurredAt() { return occurredAt; }

    public record OrderItemData(UUID productId, int quantity, BigDecimal unitPrice) {}
}