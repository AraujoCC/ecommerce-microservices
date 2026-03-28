package com.ecommerce.inventory.config;

import com.ecommerce.inventory.application.usecase.ConfirmInventoryUseCase;
import com.ecommerce.inventory.application.usecase.ReleaseInventoryUseCase;
import com.ecommerce.inventory.application.usecase.ReserveInventoryUseCase;
import com.ecommerce.inventory.domain.port.EventPublisher;
import com.ecommerce.inventory.domain.repository.ProductRepository;
import com.ecommerce.inventory.domain.repository.ReservationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ReserveInventoryUseCase reserveInventoryUseCase(ProductRepository productRepository,
                                                           ReservationRepository reservationRepository,
                                                           EventPublisher eventPublisher) {
        return new ReserveInventoryUseCase(productRepository, reservationRepository, eventPublisher);
    }

    @Bean
    public ConfirmInventoryUseCase confirmInventoryUseCase(ProductRepository productRepository,
                                                           ReservationRepository reservationRepository) {
        return new ConfirmInventoryUseCase(productRepository, reservationRepository);
    }

    @Bean
    public ReleaseInventoryUseCase releaseInventoryUseCase(ProductRepository productRepository,
                                                           ReservationRepository reservationRepository,
                                                           EventPublisher eventPublisher) {
        return new ReleaseInventoryUseCase(productRepository, reservationRepository, eventPublisher);
    }
}