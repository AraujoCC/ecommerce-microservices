package com.ecommerce.notification.infrastructure.messaging.consumer;

import com.ecommerce.notification.application.usecase.SendNotificationUseCase;
import com.ecommerce.notification.domain.event.OrderCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedConsumer.class);
    private final SendNotificationUseCase sendNotificationUseCase;
    private final ObjectMapper objectMapper;

    public OrderCreatedConsumer(SendNotificationUseCase sendNotificationUseCase, ObjectMapper objectMapper) {
        this.sendNotificationUseCase = sendNotificationUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "order.created", groupId = "notification-service-group")
    public void consume(String message) {
        try {
            OrderCreatedEvent event = objectMapper.readValue(message, OrderCreatedEvent.class);
            log.info("Event received [order.created]: orderId={}", event.getOrderId());

            sendNotificationUseCase.execute(
                event.getCustomerId(),
                "Pedido criado com sucesso!",
                String.format("Seu pedido %s foi criado. Total: R$ %s", event.getOrderId(), event.getTotalAmount())
            );
        } catch (Exception e) {
            log.error("Error processing [order.created]: {}", e.getMessage());
        }
    }
}
