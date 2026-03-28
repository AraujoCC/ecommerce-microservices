package com.ecommerce.payment.application.usecase;

import com.ecommerce.payment.domain.event.PaymentFailedEvent;
import com.ecommerce.payment.domain.event.PaymentProcessedEvent;
import com.ecommerce.payment.domain.model.Payment;
import com.ecommerce.payment.domain.port.EventPublisher;
import com.ecommerce.payment.domain.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.UUID;

public class ProcessPaymentUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcessPaymentUseCase.class);

    private final PaymentRepository paymentRepository;
    private final EventPublisher eventPublisher;

    public ProcessPaymentUseCase(PaymentRepository paymentRepository,
                                  EventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }

    public Payment execute(UUID orderId, UUID customerId, BigDecimal amount) {

        // Idempotencia: se ja existe pagamento para esse pedido, retorna o existente
        var existing = paymentRepository.findByOrderId(orderId);
        if (existing.isPresent()) {
            log.info("Pagamento ja existe para orderId: {}", orderId);
            return existing.get();
        }

        Payment payment = Payment.create(orderId, customerId, amount);

        // Simulacao de processamento de pagamento
        // Em producao aqui chamariamos um gateway (Stripe, PagSeguro, etc)
        boolean paymentApproved = simulatePaymentGateway(amount);

        if (paymentApproved) {
            payment.approve();
            paymentRepository.save(payment);

            eventPublisher.publish(new PaymentProcessedEvent(
                    payment.getId(),
                    payment.getOrderId(),
                    payment.getCustomerId(),
                    payment.getAmount(),
                    payment.getStatus().name()
            ));

            log.info("Pagamento aprovado para orderId: {}", orderId);
        } else {
            payment.fail("Saldo insuficiente");
            paymentRepository.save(payment);

            eventPublisher.publish(new PaymentFailedEvent(
                    payment.getId(),
                    payment.getOrderId(),
                    payment.getCustomerId(),
                    payment.getFailureReason()
            ));

            log.info("Pagamento recusado para orderId: {}", orderId);
        }

        return payment;
    }

    // Simulacao: aprova pagamentos abaixo de R$ 10.000
    private boolean simulatePaymentGateway(BigDecimal amount) {
        return amount.compareTo(new BigDecimal("10000.00")) < 0;
    }
}