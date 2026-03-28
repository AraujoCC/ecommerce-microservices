package com.ecommerce.notification.config;

import com.ecommerce.notification.application.usecase.SendNotificationUseCase;
import com.ecommerce.notification.domain.port.NotificationSender;
import com.ecommerce.notification.infrastructure.notification.EmailNotificationSender;
import com.ecommerce.notification.infrastructure.notification.LogNotificationSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public NotificationSender notificationSender() {
        return new EmailNotificationSender();
    }

    @Bean
    public SendNotificationUseCase sendNotificationUseCase(NotificationSender notificationSender) {
        return new SendNotificationUseCase(notificationSender);
    }
}
