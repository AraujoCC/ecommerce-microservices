package com.ecommerce.inventory.domain.model;

import com.ecommerce.inventory.domain.exception.InsufficientStockException;

import java.util.UUID;

public class Product {

    private final UUID id;
    private final String name;
    private final String sku;
    private int availableQuantity;
    private int reservedQuantity;

    private Product(UUID id, String name, String sku, int availableQuantity) {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.availableQuantity = availableQuantity;
        this.reservedQuantity = 0;
    }

    public static Product create(String name, String sku, int initialQuantity) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name e obrigatorio");
        if (sku == null || sku.isBlank()) throw new IllegalArgumentException("SKU e obrigatorio");
        if (initialQuantity < 0) throw new IllegalArgumentException("Quantidade nao pode ser negativa");
        return new Product(UUID.randomUUID(), name, sku, initialQuantity);
    }

    public static Product reconstitute(UUID id, String name, String sku,
                                       int availableQuantity, int reservedQuantity) {
        Product product = new Product(id, name, sku, availableQuantity);
        product.reservedQuantity = reservedQuantity;
        return product;
    }

    public void reserve(int quantity) {
        if (this.availableQuantity < quantity)
            throw new InsufficientStockException(id.toString(), quantity, availableQuantity);
        this.availableQuantity -= quantity;
        this.reservedQuantity += quantity;
    }

    public void confirmReservation(int quantity) {
        if (this.reservedQuantity < quantity)
            throw new IllegalStateException("Reserva insuficiente para confirmar");
        this.reservedQuantity -= quantity;
    }

    public void releaseReservation(int quantity) {
        if (this.reservedQuantity < quantity)
            throw new IllegalStateException("Reserva insuficiente para liberar");
        this.reservedQuantity -= quantity;
        this.availableQuantity += quantity;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getSku() { return sku; }
    public int getAvailableQuantity() { return availableQuantity; }
    public int getReservedQuantity() { return reservedQuantity; }
}