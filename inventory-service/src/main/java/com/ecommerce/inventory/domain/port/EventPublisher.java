package com.ecommerce.inventory.domain.port;

public interface EventPublisher {
    void publish(Object event);
}