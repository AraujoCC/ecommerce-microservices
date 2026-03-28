package com.ecommerce.inventory.domain.repository;

import com.ecommerce.inventory.domain.model.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(UUID id);
    Optional<Product> findBySku(String sku);
}