package com.ecommerce.notification.infrastructure.notification;

import com.ecommerce.notification.domain.port.NotificationSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailNotificationSender implements NotificationSender {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationSender.class);

    @Override
    public void send(String recipient, String subject, String message) {
        // TODO: Integrate with SMTP / SendGrid / SES
        log.info("[EMAIL SIMULATION] To: {} | Subject: {} | Body: {}", recipient, subject, message);
    }
}
