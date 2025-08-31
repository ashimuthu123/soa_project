package com.globalbooks.payments.controller;

import com.globalbooks.payments.model.Payment;
import com.globalbooks.payments.model.PaymentMethod;
import com.globalbooks.payments.model.PaymentStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "*")
public class PaymentsController {

    private final AtomicLong idCounter = new AtomicLong(1);
    private final List<Payment> payments = new ArrayList<>();

    public PaymentsController() {
        // Initialize with sample data
        Payment samplePayment = new Payment();
        samplePayment.setId(idCounter.getAndIncrement());
        samplePayment.setOrderId("ORDER001");
        samplePayment.setAmount(new BigDecimal("29.99"));
        samplePayment.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        samplePayment.setStatus(PaymentStatus.COMPLETED);
        samplePayment.setCardNumber("****-****-****-1234");
        samplePayment.setTransactionId("TXN" + System.currentTimeMillis());
        samplePayment.setPaymentDate(LocalDateTime.now());
        samplePayment.setLastModified(LocalDateTime.now());
        payments.add(samplePayment);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        return payments.stream()
                .filter(payment -> payment.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        payment.setId(idCounter.getAndIncrement());
        payment.setTransactionId("TXN" + System.currentTimeMillis());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setLastModified(LocalDateTime.now());
        
        if (payment.getStatus() == null) {
            payment.setStatus(PaymentStatus.PENDING);
        }
        
        payments.add(payment);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam PaymentStatus status) {
        return payments.stream()
                .filter(payment -> payment.getId().equals(id))
                .findFirst()
                .map(payment -> {
                    payment.setStatus(status);
                    payment.setLastModified(LocalDateTime.now());
                    return ResponseEntity.ok(payment);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Payments Service is running!");
    }
}
