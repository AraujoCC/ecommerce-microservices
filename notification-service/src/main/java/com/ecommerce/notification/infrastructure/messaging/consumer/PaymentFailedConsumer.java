package com.ecommerce.notification.infrastructure.messaging.consumer;

import com.ecommerce.notification.application.usecase.SendNotificationUseCase;
import com.ecommerce.notification.domain.event.PaymentFailedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentFailedConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentFailedConsumer.class);
    private final SendNotificationUseCase sendNotificationUseCase;
    private final ObjectMapper objectMapper;

    public PaymentFailedConsumer(SendNotificationUseCase sendNotificationUseCase, ObjectMapper objectMapper) {
        this.sendNotificationUseCase = sendNotificationUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "payment.failed", groupId = "notification-service-group")
    public void consume(String message) {
        try {
            PaymentFailedEvent event = objectMapper.readValue(message, PaymentFailedEvent.class);
            log.info("Event received [payment.failed]: paymentId={}", event.getPaymentId());

            sendNotificationUseCase.execute(
                event.getOrderId(),
                "Falha no pagamento",
                String.format("Seu pagamento falhou. Motivo: %s", event.getReason())
            );
        } catch (Exception e) {
            log.error("Error processing [payment.failed]: {}", e.getMessage());
        }
    }
}
