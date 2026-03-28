package com.ecommerce.inventory.application.usecase;

import com.ecommerce.inventory.domain.model.Product;
import com.ecommerce.inventory.domain.model.Reservation;
import com.ecommerce.inventory.domain.model.ReservationStatus;
import com.ecommerce.inventory.domain.repository.ProductRepository;
import com.ecommerce.inventory.domain.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ConfirmInventoryUseCase {

    private static final Logger log = LoggerFactory.getLogger(ConfirmInventoryUseCase.class);

    private final ProductRepository productRepository;
    private final ReservationRepository reservationRepository;

    public ConfirmInventoryUseCase(ProductRepository productRepository,
                                   ReservationRepository reservationRepository) {
        this.productRepository = productRepository;
        this.reservationRepository = reservationRepository;
    }

    public void execute(UUID orderId) {
        var reservations = reservationRepository.findByOrderId(orderId);

        if (reservations.isEmpty()) {
            log.warn("Nenhuma reserva encontrada para confirmar. orderId: {}", orderId);
            return;
        }

        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) continue;

            Product product = productRepository.findById(reservation.getProductId())
                    .orElseThrow(() -> new RuntimeException(
                        "Produto nao encontrado: " + reservation.getProductId()));

            product.confirmReservation(reservation.getQuantity());
            productRepository.save(product);

            reservation.confirm();
            reservationRepository.save(reservation);

            log.info("Reserva confirmada: {} unidades do produto {}",
                    reservation.getQuantity(), reservation.getProductId());
        }

        log.info("Inventario confirmado para orderId: {}", orderId);
    }
}