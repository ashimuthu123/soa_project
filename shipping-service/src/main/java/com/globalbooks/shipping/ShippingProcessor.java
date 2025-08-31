package com.globalbooks.shipping;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class ShippingProcessor {
    // Simulate data store
    private final Map<String, String> shippingStore = new HashMap<>();

    @RabbitListener(queues = "shipping-queue")
    public void processShipping(String orderId) {
        // Simulate shipping coordination
        System.out.println("Processing shipping for order: " + orderId);
        shippingStore.put(orderId, "SHIPPED");
        // In real scenario, integrate with shipping provider
    }
}