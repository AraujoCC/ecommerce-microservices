package com.ecommerce.order.interfaces.controller.dto;

import com.ecommerce.order.domain.model.Order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class OrderResponse {

    private final UUID id;
    private final UUID customerId;
    private final String status;
    private final BigDecimal totalAmount;
    private final Instant createdAt;

    private OrderResponse(UUID id, UUID customerId, String status,
                          BigDecimal totalAmount, Instant createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getCreatedAt()
        );
    }

    public UUID getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public String getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public Instant getCreatedAt() { return createdAt; }
}
