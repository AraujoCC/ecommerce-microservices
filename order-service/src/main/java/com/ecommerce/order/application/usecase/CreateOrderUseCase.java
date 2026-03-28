package com.ecommerce.order.application.usecase;

import com.ecommerce.order.domain.event.OrderCreatedEvent;
import com.ecommerce.order.domain.model.Order;
import com.ecommerce.order.domain.model.OrderItem;
import com.ecommerce.order.domain.port.EventPublisher;
import com.ecommerce.order.domain.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

public class CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;

    public CreateOrderUseCase(OrderRepository orderRepository, EventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    public Order execute(UUID customerId, List<OrderItem> items) {
        Order order = Order.create(customerId, items);
        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getCustomerId(),
                savedOrder.getTotalAmount()
        );

        eventPublisher.publish(event);

        return savedOrder;
    }
}
