package com.ecommerce.order.domain.port;

public interface EventPublisher {
    void publish(Object event);
}
