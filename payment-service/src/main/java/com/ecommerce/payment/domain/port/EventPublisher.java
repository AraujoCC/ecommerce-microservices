package com.ecommerce.payment.domain.port;

public interface EventPublisher {
    void publish(Object event);
}