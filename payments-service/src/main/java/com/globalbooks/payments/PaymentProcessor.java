package com.globalbooks.payments;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentProcessor {
    // Simulate data store (in-memory)
    private final Map<String, String> paymentStore = new HashMap<>();

    @RabbitListener(queues = "payments-queue")
    public void processPayment(String orderId) {
        // Simulate payment processing
        System.out.println("Processing payment for order: " + orderId);
        paymentStore.put(orderId, "PAID");
        // In real scenario, integrate with payment gateway
    }
}