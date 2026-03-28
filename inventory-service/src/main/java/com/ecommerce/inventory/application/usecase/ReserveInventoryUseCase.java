package com.ecommerce.inventory.application.usecase;

import com.ecommerce.inventory.domain.event.InventoryReservedEvent;
import com.ecommerce.inventory.domain.model.Product;
import com.ecommerce.inventory.domain.model.Reservation;
import com.ecommerce.inventory.domain.port.EventPublisher;
import com.ecommerce.inventory.domain.repository.ProductRepository;
import com.ecommerce.inventory.domain.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class ReserveInventoryUseCase {

    private static final Logger log = LoggerFactory.getLogger(ReserveInventoryUseCase.class);

    private final ProductRepository productRepository;
    private final ReservationRepository reservationRepository;
    private final EventPublisher eventPublisher;

    public ReserveInventoryUseCase(ProductRepository productRepository,
                                   ReservationRepository reservationRepository,
                                   EventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.reservationRepository = reservationRepository;
        this.eventPublisher = eventPublisher;
    }

    public void execute(UUID orderId, List<OrderItemData> items) {
        // Idempotencia: verifica se ja existe reserva para esse pedido
        var existing = reservationRepository.findByOrderId(orderId);
        if (!existing.isEmpty()) {
            log.info("Reserva ja existe para orderId: {}", orderId);
            return;
        }

        for (OrderItemData item : items) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new RuntimeException(
                        "Produto nao encontrado: " + item.productId()));

            product.reserve(item.quantity());
            productRepository.save(product);

            Reservation reservation = Reservation.create(orderId, item.productId(), item.quantity());
            reservationRepository.save(reservation);

            log.info("Estoque reservado: {} unidades do produto {}",
                    item.quantity(), item.productId());
        }

        eventPublisher.publish(new InventoryReservedEvent(orderId));
        log.info("Inventario reservado para orderId: {}", orderId);
    }

    public record OrderItemData(UUID productId, int quantity) {}
}