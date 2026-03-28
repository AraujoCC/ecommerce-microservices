package com.ecommerce.inventory.infrastructure.repository;

import com.ecommerce.inventory.domain.model.Product;
import com.ecommerce.inventory.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final SpringDataProductRepository jpaRepository;

    public ProductRepositoryImpl(SpringDataProductRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Product save(Product product) {
        return toDomain(jpaRepository.save(toEntity(product)));
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return jpaRepository.findBySku(sku).map(this::toDomain);
    }

    private ProductEntity toEntity(Product p) {
        ProductEntity e = new ProductEntity();
        e.setId(p.getId());
        e.setName(p.getName());
        e.setSku(p.getSku());
        e.setAvailableQuantity(p.getAvailableQuantity());
        e.setReservedQuantity(p.getReservedQuantity());
        return e;
    }

    private Product toDomain(ProductEntity e) {
        return Product.reconstitute(e.getId(), e.getName(), e.getSku(),
                e.getAvailableQuantity(), e.getReservedQuantity());
    }
}