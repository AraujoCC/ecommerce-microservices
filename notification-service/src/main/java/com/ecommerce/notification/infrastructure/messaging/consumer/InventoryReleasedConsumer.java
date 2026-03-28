package com.ecommerce.notification.infrastructure.messaging.consumer;

import com.ecommerce.notification.application.usecase.SendNotificationUseCase;
import com.ecommerce.notification.domain.event.InventoryReleasedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryReleasedConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventoryReleasedConsumer.class);
    private final SendNotificationUseCase sendNotificationUseCase;
    private final ObjectMapper objectMapper;

    public InventoryReleasedConsumer(SendNotificationUseCase sendNotificationUseCase, ObjectMapper objectMapper) {
        this.sendNotificationUseCase = sendNotificationUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "inventory.released", groupId = "notification-service-group")
    public void consume(String message) {
        try {
            InventoryReleasedEvent event = objectMapper.readValue(message, InventoryReleasedEvent.class);
            log.info("Event received [inventory.released]: orderId={}", event.getOrderId());

            sendNotificationUseCase.execute(
                event.getOrderId(),
                "Estoque liberado",
                String.format("Estoque do produto %s foi liberado. Quantidade: %d", event.getProductId(), event.getQuantity())
            );
        } catch (Exception e) {
            log.error("Error processing [inventory.released]: {}", e.getMessage());
        }
    }
}
