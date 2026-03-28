package com.ecommerce.order.config;

import com.ecommerce.order.application.usecase.CreateOrderUseCase;
import com.ecommerce.order.domain.port.EventPublisher;
import com.ecommerce.order.domain.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepository orderRepository,
                                                  EventPublisher eventPublisher) {
        return new CreateOrderUseCase(orderRepository, eventPublisher);
    }
}
