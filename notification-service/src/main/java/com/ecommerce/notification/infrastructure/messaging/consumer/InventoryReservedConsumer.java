package com.ecommerce.notification.infrastructure.messaging.consumer;

import com.ecommerce.notification.application.usecase.SendNotificationUseCase;
import com.ecommerce.notification.domain.event.InventoryReservedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryReservedConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventoryReservedConsumer.class);
    private final SendNotificationUseCase sendNotificationUseCase;
    private final ObjectMapper objectMapper;

    public InventoryReservedConsumer(SendNotificationUseCase sendNotificationUseCase, ObjectMapper objectMapper) {
        this.sendNotificationUseCase = sendNotificationUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "inventory.reserved", groupId = "notification-service-group")
    public void consume(String message) {
        try {
            InventoryReservedEvent event = objectMapper.readValue(message, InventoryReservedEvent.class);
            log.info("Event received [inventory.reserved]: orderId={}", event.getOrderId());

            sendNotificationUseCase.execute(
                event.getOrderId(),
                "Estoque reservado!",
                String.format("Produto %s reservado. Quantidade: %d", event.getProductId(), event.getQuantity())
            );
        } catch (Exception e) {
            log.error("Error processing [inventory.reserved]: {}", e.getMessage());
        }
    }
}
