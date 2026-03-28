package com.ecommerce.payment.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Payment {

    private final UUID id;
    private final UUID orderId;
    private final UUID customerId;
    private final BigDecimal amount;
    private PaymentStatus status;
    private String failureReason;
    private final Instant createdAt;
    private Instant processedAt;

    private Payment(UUID id, UUID orderId, UUID customerId, BigDecimal amount) {
        this.id = id;
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.createdAt = Instant.now();
    }

    public static Payment create(UUID orderId, UUID customerId, BigDecimal amount) {
        if (orderId == null) throw new IllegalArgumentException("OrderId e obrigatorio");
        if (customerId == null) throw new IllegalArgumentException("CustomerId e obrigatorio");
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Amount deve ser maior que zero");
        return new Payment(UUID.randomUUID(), orderId, customerId, amount);
    }

    public static Payment reconstitute(UUID id, UUID orderId, UUID customerId,
                                       BigDecimal amount, PaymentStatus status,
                                       String failureReason, Instant createdAt,
                                       Instant processedAt) {
        Payment payment = new Payment(id, orderId, customerId, amount);
        payment.status = status;
        payment.failureReason = failureReason;
        payment.createdAt = createdAt;
        payment.processedAt = processedAt;
        return payment;
    }

    public void approve() {
        if (this.status != PaymentStatus.PENDING)
            throw new IllegalStateException("Apenas pagamentos PENDING podem ser aprovados");
        this.status = PaymentStatus.APPROVED;
        this.processedAt = Instant.now();
    }

    public void fail(String reason) {
        if (this.status != PaymentStatus.PENDING)
            throw new IllegalStateException("Apenas pagamentos PENDING podem falhar");
        this.status = PaymentStatus.FAILED;
        this.failureReason = reason;
        this.processedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getOrderId() { return orderId; }
    public UUID getCustomerId() { return customerId; }
    public BigDecimal getAmount() { return amount; }
    public PaymentStatus getStatus() { return status; }
    public String getFailureReason() { return failureReason; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getProcessedAt() { return processedAt; }
}