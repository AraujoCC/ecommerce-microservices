package com.ecommerce.payment.config;

import com.ecommerce.payment.application.usecase.ProcessPaymentUseCase;
import com.ecommerce.payment.domain.port.EventPublisher;
import com.ecommerce.payment.domain.repository.PaymentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ProcessPaymentUseCase processPaymentUseCase(PaymentRepository paymentRepository,
                                                        EventPublisher eventPublisher) {
        return new ProcessPaymentUseCase(paymentRepository, eventPublisher);
    }
}