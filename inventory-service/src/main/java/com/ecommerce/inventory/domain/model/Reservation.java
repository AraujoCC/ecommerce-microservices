package com.ecommerce.inventory.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Reservation {

    private final UUID id;
    private final UUID orderId;
    private final UUID productId;
    private final int quantity;
    private ReservationStatus status;
    private final Instant createdAt;

    private Reservation(UUID id, UUID orderId, UUID productId, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = ReservationStatus.ACTIVE;
        this.createdAt = Instant.now();
    }

    public static Reservation create(UUID orderId, UUID productId, int quantity) {
        return new Reservation(UUID.randomUUID(), orderId, productId, quantity);
    }

    public static Reservation reconstitute(UUID id, UUID orderId, UUID productId,
                                           int quantity, ReservationStatus status,
                                           Instant createdAt) {
        Reservation r = new Reservation(id, orderId, productId, quantity);
        r.status = status;
        return r;
    }

    public void confirm() { this.status = ReservationStatus.CONFIRMED; }
    public void release() { this.status = ReservationStatus.RELEASED; }

    public UUID getId() { return id; }
    public UUID getOrderId() { return orderId; }
    public UUID getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public ReservationStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}