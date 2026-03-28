package com.ecommerce.inventory.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SpringDataReservationRepository extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findByOrderId(UUID orderId);
}