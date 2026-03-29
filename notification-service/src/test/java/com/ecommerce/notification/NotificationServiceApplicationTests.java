package com.ecommerce.notification;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, topics = {
    "order.created",
    "payment.processed",
    "payment.failed",
    "inventory.reserved",
    "inventory.released"
})
class NotificationServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
