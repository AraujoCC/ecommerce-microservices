package com.ecommerce.notification.infrastructure.messaging.consumer;

import com.ecommerce.notification.application.usecase.SendNotificationUseCase;
import com.ecommerce.notification.domain.event.PaymentProcessedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessedConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentProcessedConsumer.class);
    private final SendNotificationUseCase sendNotificationUseCase;
    private final ObjectMapper objectMapper;

    public PaymentProcessedConsumer(SendNotificationUseCase sendNotificationUseCase, ObjectMapper objectMapper) {
        this.sendNotificationUseCase = sendNotificationUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "payment.processed", groupId = "notification-service-group")
    public void consume(String message) {
        try {
            PaymentProcessedEvent event = objectMapper.readValue(message, PaymentProcessedEvent.class);
            log.info("Event received [payment.processed]: paymentId={}", event.getPaymentId());

            sendNotificationUseCase.execute(
                event.getOrderId(),
                "Pagamento aprovado!",
                String.format("Seu pagamento de R$ %s foi aprovado. Status: %s", event.getAmount(), event.getStatus())
            );
        } catch (Exception e) {
            log.error("Error processing [payment.processed]: {}", e.getMessage());
        }
    }
}
