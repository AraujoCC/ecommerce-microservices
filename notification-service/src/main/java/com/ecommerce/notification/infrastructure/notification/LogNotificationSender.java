package com.ecommerce.notification.infrastructure.notification;

import com.ecommerce.notification.domain.port.NotificationSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogNotificationSender implements NotificationSender {

    private static final Logger log = LoggerFactory.getLogger(LogNotificationSender.class);

    @Override
    public void send(String recipient, String subject, String message) {
        log.info("==========================================");
        log.info("[NOTIFICATION SENT]");
        log.info("To      : {}", recipient);
        log.info("Subject : {}", subject);
        log.info("Message : {}", message);
        log.info("==========================================");
    }
}
