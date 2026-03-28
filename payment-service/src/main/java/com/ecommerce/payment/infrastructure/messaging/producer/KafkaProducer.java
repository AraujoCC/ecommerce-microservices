package com.ecommerce.payment.infrastructure.messaging.producer;

import com.ecommerce.payment.domain.port.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(Object event) {
        String topic = resolveTopic(event);
        log.info("Publicando evento: {} no topico: {}", event.getClass().getSimpleName(), topic);
        kafkaTemplate.send(topic, event);
    }

    private String resolveTopic(Object event) {
        return switch (event.getClass().getSimpleName()) {
            case "PaymentProcessedEvent" -> "payment.processed";
            case "PaymentFailedEvent" -> "payment.failed";
            default -> throw new IllegalArgumentException("Evento desconhecido: " + event.getClass().getSimpleName());
        };
    }
}