package com.ecommerce.payment.infrastructure.messaging.consumer;

import com.ecommerce.payment.application.usecase.ProcessPaymentUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedConsumer.class);

    private final ProcessPaymentUseCase processPaymentUseCase;

    public OrderCreatedConsumer(ProcessPaymentUseCase processPaymentUseCase) {
        this.processPaymentUseCase = processPaymentUseCase;
    }

    @KafkaListener(
        topics = "order.created",
        groupId = "payment-service",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(OrderCreatedMessage message) {
        log.info("Evento order.created recebido para orderId: {}", message.getOrderId());

        processPaymentUseCase.execute(
                message.getOrderId(),
                message.getCustomerId(),
                message.getTotalAmount()
        );
    }
}