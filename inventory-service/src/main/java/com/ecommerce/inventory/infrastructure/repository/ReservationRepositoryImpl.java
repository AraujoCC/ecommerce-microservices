package com.ecommerce.inventory.infrastructure.repository;

import com.ecommerce.inventory.domain.model.Reservation;
import com.ecommerce.inventory.domain.repository.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final SpringDataReservationRepository jpaRepository;

    public ReservationRepositoryImpl(SpringDataReservationRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return toDomain(jpaRepository.save(toEntity(reservation)));
    }

    @Override
    public Optional<Reservation> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Reservation> findByOrderId(UUID orderId) {
        return jpaRepository.findByOrderId(orderId).stream().map(this::toDomain).toList();
    }

    private ReservationEntity toEntity(Reservation r) {
        ReservationEntity e = new ReservationEntity();
        e.setId(r.getId());
        e.setOrderId(r.getOrderId());
        e.setProductId(r.getProductId());
        e.setQuantity(r.getQuantity());
        e.setStatus(r.getStatus());
        e.setCreatedAt(r.getCreatedAt());
        return e;
    }

    private Reservation toDomain(ReservationEntity e) {
        return Reservation.reconstitute(e.getId(), e.getOrderId(), e.getProductId(),
                e.getQuantity(), e.getStatus(), e.getCreatedAt());
    }
}