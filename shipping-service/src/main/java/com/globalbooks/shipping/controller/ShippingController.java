package com.globalbooks.shipping.controller;

import com.globalbooks.shipping.model.Shipment;
import com.globalbooks.shipping.model.ShipmentStatus;
import com.globalbooks.shipping.model.ShippingMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/shipping")
@CrossOrigin(origins = "*")
public class ShippingController {

    private final AtomicLong idCounter = new AtomicLong(1);
    private final List<Shipment> shipments = new ArrayList<>();

    public ShippingController() {
        // Initialize with sample data
        Shipment sampleShipment = new Shipment();
        sampleShipment.setId(idCounter.getAndIncrement());
        sampleShipment.setOrderId("ORDER001");
        sampleShipment.setCustomerId("CUST001");
        sampleShipment.setShippingMethod(ShippingMethod.STANDARD);
        sampleShipment.setStatus(ShipmentStatus.SHIPPED);
        sampleShipment.setCustomerAddress("123 Main St, City, Country");
        sampleShipment.setTrackingNumber("TRK" + System.currentTimeMillis());
        sampleShipment.setEstimatedDelivery(LocalDateTime.now().plusDays(3));
        sampleShipment.setShipmentDate(LocalDateTime.now());
        sampleShipment.setLastModified(LocalDateTime.now());
        shipments.add(sampleShipment);
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipment(@PathVariable Long id) {
        return shipments.stream()
                .filter(shipment -> shipment.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment) {
        shipment.setId(idCounter.getAndIncrement());
        shipment.setTrackingNumber("TRK" + System.currentTimeMillis());
        shipment.setShipmentDate(LocalDateTime.now());
        shipment.setLastModified(LocalDateTime.now());
        
        if (shipment.getStatus() == null) {
            shipment.setStatus(ShipmentStatus.PENDING);
        }
        
        shipments.add(shipment);
        return ResponseEntity.ok(shipment);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Shipment> updateShipmentStatus(
            @PathVariable Long id,
            @RequestParam ShipmentStatus status) {
        return shipments.stream()
                .filter(shipment -> shipment.getId().equals(id))
                .findFirst()
                .map(shipment -> {
                    shipment.setStatus(status);
                    shipment.setLastModified(LocalDateTime.now());
                    return ResponseEntity.ok(shipment);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<Shipment> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
        return shipments.stream()
                .filter(shipment -> trackingNumber.equals(shipment.getTrackingNumber()))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Shipping Service is running!");
    }
}
