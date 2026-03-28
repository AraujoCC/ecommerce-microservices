package com.ecommerce.inventory.application.usecase;

import com.ecommerce.inventory.domain.event.InventoryReleasedEvent;
import com.ecommerce.inventory.domain.model.Product;
import com.ecommerce.inventory.domain.model.Reservation;
import com.ecommerce.inventory.domain.model.ReservationStatus;
import com.ecommerce.inventory.domain.port.EventPublisher;
import com.ecommerce.inventory.domain.repository.ProductRepository;
import com.ecommerce.inventory.domain.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ReleaseInventoryUseCase {

    private static final Logger log = LoggerFactory.getLogger(ReleaseInventoryUseCase.class);

    private final ProductRepository productRepository;
    private final ReservationRepository reservationRepository;
    private final EventPublisher eventPublisher;

    public ReleaseInventoryUseCase(ProductRepository productRepository,
                                   ReservationRepository reservationRepository,
                                   EventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.reservationRepository = reservationRepository;
        this.eventPublisher = eventPublisher;
    }

    public void execute(UUID orderId, String reason) {
        var reservations = reservationRepository.findByOrderId(orderId);

        if (reservations.isEmpty()) {
            log.warn("Nenhuma reserva encontrada para orderId: {}", orderId);
            return;
        }

        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.RELEASED) continue;

            Product product = productRepository.findById(reservation.getProductId())
                    .orElseThrow(() -> new RuntimeException(
                        "Produto nao encontrado: " + reservation.getProductId()));

            product.releaseReservation(reservation.getQuantity());
            productRepository.save(product);

            reservation.release();
            reservationRepository.save(reservation);

            log.info("Reserva liberada: {} unidades do produto {}",
                    reservation.getQuantity(), reservation.getProductId());
        }

        eventPublisher.publish(new InventoryReleasedEvent(orderId, reason));
        log.info("Inventario liberado para orderId: {} - Motivo: {}", orderId, reason);
    }
}