package com.ecommerce.payment.infrastructure.repository;

import com.ecommerce.payment.domain.model.Payment;
import com.ecommerce.payment.domain.repository.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final SpringDataPaymentRepository jpaRepository;

    public PaymentRepositoryImpl(SpringDataPaymentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = toEntity(payment);
        PaymentEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return jpaRepository.findByOrderId(orderId).map(this::toDomain);
    }

    private PaymentEntity toEntity(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        entity.setId(payment.getId());
        entity.setOrderId(payment.getOrderId());
        entity.setCustomerId(payment.getCustomerId());
        entity.setAmount(payment.getAmount());
        entity.setStatus(payment.getStatus());
        entity.setFailureReason(payment.getFailureReason());
        entity.setCreatedAt(payment.getCreatedAt());
        entity.setProcessedAt(payment.getProcessedAt());
        return entity;
    }

    private Payment toDomain(PaymentEntity entity) {
        return Payment.reconstitute(
                entity.getId(),
                entity.getOrderId(),
                entity.getCustomerId(),
                entity.getAmount(),
                entity.getStatus(),
                entity.getFailureReason(),
                entity.getCreatedAt(),
                entity.getProcessedAt()
        );
    }
}