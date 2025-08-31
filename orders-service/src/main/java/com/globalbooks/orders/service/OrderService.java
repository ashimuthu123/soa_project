package com.globalbooks.orders.service;

import com.globalbooks.orders.model.Order;
import com.globalbooks.orders.model.OrderStatus;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    
    /**
     * Create a new order
     * @param order the order to create
     * @return the created order with generated ID
     */
    Order createOrder(Order order);
    
    /**
     * Get order by ID
     * @param id the order ID
     * @return Optional containing the order if found
     */
    Optional<Order> getOrderById(Long id);
    
    /**
     * Get all orders
     * @return list of all orders
     */
    List<Order> getAllOrders();
    
    /**
     * Update order status
     * @param id the order ID
     * @param status the new status
     * @return Optional containing the updated order if found
     */
    Optional<Order> updateOrderStatus(Long id, OrderStatus status);
    
    /**
     * Get orders by customer email
     * @param customerEmail the customer email
     * @return list of orders for the customer
     */
    List<Order> getOrdersByCustomerEmail(String customerEmail);
    
    /**
     * Delete order by ID
     * @param id the order ID
     * @return true if deleted, false if not found
     */
    boolean deleteOrder(Long id);
}
