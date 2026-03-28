package com.ecommerce.order.interfaces.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class CreateOrderRequest {

    @NotNull(message = "CustomerId e obrigatorio")
    private UUID customerId;

    @NotEmpty(message = "Pedido deve ter ao menos um item")
    @Valid
    private List<OrderItemRequest> items;

    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}
