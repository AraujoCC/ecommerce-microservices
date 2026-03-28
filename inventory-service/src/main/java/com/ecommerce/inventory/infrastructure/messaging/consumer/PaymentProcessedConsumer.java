package com.ecommerce.inventory.infrastructure.messaging.consumer;

import com.ecommerce.inventory.application.usecase.ConfirmInventoryUseCase;
import com.ecommerce.inventory.application.usecase.ReleaseInventoryUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessedConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentProcessedConsumer.class);
    private final ConfirmInventoryUseCase confirmInventoryUseCase;
    private final ReleaseInventoryUseCase releaseInventoryUseCase;

    public PaymentProcessedConsumer(ConfirmInventoryUseCase confirmInventoryUseCase,
                                    ReleaseInventoryUseCase releaseInventoryUseCase) {
        this.confirmInventoryUseCase = confirmInventoryUseCase;
        this.releaseInventoryUseCase = releaseInventoryUseCase;
    }

    @KafkaListener(
        topics = "payment.processed",
        groupId = "inventory-service-payment",
        containerFactory = "paymentProcessedContainerFactory"
    )
    public void consume(PaymentProcessedMessage message) {
        log.info("Evento payment.processed recebido. orderId: {}, status: {}",
                message.getOrderId(), message.getStatus());

        if ("APPROVED".equals(message.getStatus())) {
            confirmInventoryUseCase.execute(message.getOrderId());
        } else {
            releaseInventoryUseCase.execute(message.getOrderId(), "Pagamento recusado");
        }
    }
}