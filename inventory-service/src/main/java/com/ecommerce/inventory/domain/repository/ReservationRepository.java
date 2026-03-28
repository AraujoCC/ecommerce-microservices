package com.ecommerce.inventory.domain.repository;

import com.ecommerce.inventory.domain.model.Reservation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(UUID id);
    List<Reservation> findByOrderId(UUID orderId);
}