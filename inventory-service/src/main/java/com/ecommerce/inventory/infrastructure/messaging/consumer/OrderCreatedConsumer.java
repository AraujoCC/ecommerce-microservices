package com.ecommerce.inventory.infrastructure.messaging.consumer;

import com.ecommerce.inventory.application.usecase.ReserveInventoryUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderCreatedConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedConsumer.class);
    private final ReserveInventoryUseCase reserveInventoryUseCase;

    public OrderCreatedConsumer(ReserveInventoryUseCase reserveInventoryUseCase) {
        this.reserveInventoryUseCase = reserveInventoryUseCase;
    }

    @KafkaListener(
        topics = "order.created",
        groupId = "inventory-service-order",
        containerFactory = "orderCreatedContainerFactory"
    )
    public void consume(OrderCreatedMessage message) {
        log.info("Evento order.created recebido para orderId: {}", message.getOrderId());

        List<ReserveInventoryUseCase.OrderItemData> items = message.getItems().stream()
                .map(i -> new ReserveInventoryUseCase.OrderItemData(
                        i.getProductId(),
                        i.getQuantity()
                ))
                .toList();

        reserveInventoryUseCase.execute(message.getOrderId(), items);
    }
}