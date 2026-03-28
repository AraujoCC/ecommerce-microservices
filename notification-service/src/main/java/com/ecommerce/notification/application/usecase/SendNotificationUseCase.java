package com.ecommerce.notification.application.usecase;

import com.ecommerce.notification.domain.port.NotificationSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendNotificationUseCase {

    private static final Logger log = LoggerFactory.getLogger(SendNotificationUseCase.class);
    private final NotificationSender notificationSender;

    public SendNotificationUseCase(NotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    public void execute(String recipient, String subject, String message) {
        log.info("Processing notification to [{}] | Subject: {}", recipient, subject);
        notificationSender.send(recipient, subject, message);
    }
}
