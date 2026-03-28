package com.ecommerce.notification.domain.port;

public interface NotificationSender {
    void send(String recipient, String subject, String message);
}
