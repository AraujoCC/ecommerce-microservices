package com.ecommerce.order.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID id;
    private final UUID customerId;
    private final List<OrderItem> items;
    private OrderStatus status;
    private final BigDecimal totalAmount;
    private Instant createdAt;

    private Order(UUID id, UUID customerId, List<OrderItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.items = Collections.unmodifiableList(items);
        this.status = OrderStatus.PENDING;
        this.totalAmount = calculateTotal(items);
        this.createdAt = Instant.now();
    }

    public static Order create(UUID customerId, List<OrderItem> items) {
        if (customerId == null)
            throw new IllegalArgumentException("CustomerId e obrigatorio");
        if (items == null || items.isEmpty())
            throw new IllegalArgumentException("Pedido deve ter ao menos um item");
        return new Order(UUID.randomUUID(), customerId, items);
    }

    public static Order reconstitute(UUID id, UUID customerId, List<OrderItem> items,
                                     OrderStatus status, BigDecimal totalAmount,
                                     Instant createdAt) {
        Order order = new Order(id, customerId, items);
        order.status = status;
        order.createdAt = createdAt;
        return order;
    }

    public void confirm() {
        if (this.status != OrderStatus.PENDING)
            throw new IllegalStateException("Apenas pedidos PENDING podem ser confirmados");
        this.status = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status == OrderStatus.DELIVERED)
            throw new IllegalStateException("Pedidos entregues nao podem ser cancelados");
        this.status = OrderStatus.CANCELLED;
    }

    private BigDecimal calculateTotal(List<OrderItem> items) {
        return items.stream()
                .map(OrderItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public UUID getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public List<OrderItem> getItems() { return items; }
    public OrderStatus getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public Instant getCreatedAt() { return createdAt; }
}
