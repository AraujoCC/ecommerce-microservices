package com.ecommerce.inventory.domain.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String productId, int requested, int available) {
        super(String.format(
            "Estoque insuficiente para produto %s. Solicitado: %d, Disponivel: %d",
            productId, requested, available
        ));
    }
}