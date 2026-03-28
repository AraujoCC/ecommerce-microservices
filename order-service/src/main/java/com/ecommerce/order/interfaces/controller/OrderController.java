package com.ecommerce.order.interfaces.controller;

import com.ecommerce.order.application.usecase.CreateOrderUseCase;
import com.ecommerce.order.domain.model.Order;
import com.ecommerce.order.domain.model.OrderItem;
import com.ecommerce.order.interfaces.controller.dto.CreateOrderRequest;
import com.ecommerce.order.interfaces.controller.dto.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {
        List<OrderItem> items = request.getItems().stream()
                .map(i -> new OrderItem(
                        i.getProductId(),
                        i.getProductName(),
                        i.getQuantity(),
                        i.getUnitPrice()
                ))
                .toList();

        Order order = createOrderUseCase.execute(request.getCustomerId(), items);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(OrderResponse.from(order));
    }
}
