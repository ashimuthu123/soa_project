package com.globalbooks.orders.controller;

import com.globalbooks.orders.model.Order;
import com.globalbooks.orders.model.OrderItem;
import com.globalbooks.orders.model.OrderStatus;
import com.globalbooks.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // POST /orders - Create a new order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            Order order = new Order();
            order.setCustomerName(request.getCustomerName());
            order.setCustomerEmail(request.getCustomerEmail());
            order.setCustomerAddress(request.getCustomerAddress());
            order.setStatus(OrderStatus.PENDING);
            
            // Convert request items to order items
            List<OrderItem> orderItems = request.getOrderItems().stream()
                .map(this::convertToOrderItem)
                .toList();
            
            order.setOrderItems(orderItems);
            
            // Calculate total amount
            BigDecimal totalAmount = orderItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            order.setTotalAmount(totalAmount);
            
            Order savedOrder = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // GET /orders/{id} - Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // GET /orders - Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // PUT /orders/{id}/status - Update order status
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id, 
            @RequestBody UpdateStatusRequest request) {
        try {
            OrderStatus newStatus = OrderStatus.valueOf(request.getStatus().toUpperCase());
            Optional<Order> updatedOrder = orderService.updateOrderStatus(id, newStatus);
            return updatedOrder.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private OrderItem convertToOrderItem(CreateOrderItemRequest itemRequest) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBookId(itemRequest.getBookId());
        orderItem.setBookTitle(itemRequest.getBookTitle());
        orderItem.setBookIsbn(itemRequest.getBookIsbn());
        orderItem.setQuantity(itemRequest.getQuantity());
        orderItem.setUnitPrice(itemRequest.getUnitPrice());
        orderItem.calculateSubtotal();
        return orderItem;
    }

    // Inner classes for request/response
    public static class CreateOrderRequest {
        private String customerName;
        private String customerEmail;
        private String customerAddress;
        private List<CreateOrderItemRequest> orderItems;

        // Getters and Setters
        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }
        
        public String getCustomerEmail() { return customerEmail; }
        public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
        
        public String getCustomerAddress() { return customerAddress; }
        public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }
        
        public List<CreateOrderItemRequest> getOrderItems() { return orderItems; }
        public void setOrderItems(List<CreateOrderItemRequest> orderItems) { this.orderItems = orderItems; }
    }

    public static class CreateOrderItemRequest {
        private String bookId;
        private String bookTitle;
        private String bookIsbn;
        private Integer quantity;
        private BigDecimal unitPrice;

        // Getters and Setters
        public String getBookId() { return bookId; }
        public void setBookId(String bookId) { this.bookId = bookId; }
        
        public String getBookTitle() { return bookTitle; }
        public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
        
        public String getBookIsbn() { return bookIsbn; }
        public void setBookIsbn(String bookIsbn) { this.bookIsbn = bookIsbn; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        
        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    }

    public static class UpdateStatusRequest {
        private String status;

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}
