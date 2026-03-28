package com.ecommerce.order.infrastructure.messaging;

import com.ecommerce.order.domain.port.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private static final String TOPIC_ORDER_CREATED = "order.created";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(Object event) {
        log.info("Publicando evento: {}", event.getClass().getSimpleName());
        kafkaTemplate.send(TOPIC_ORDER_CREATED, event);
        log.info("Evento publicado com sucesso no topico: {}", TOPIC_ORDER_CREATED);
    }
}
