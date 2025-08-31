package com.globalbooks.orders.repository;

import com.globalbooks.orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * Find orders by customer email
     * @param customerEmail the customer email
     * @return list of orders for the customer
     */
    List<Order> findByCustomerEmail(String customerEmail);
    
    /**
     * Find orders by status
     * @param status the order status
     * @return list of orders with the specified status
     */
    List<Order> findByStatus(com.globalbooks.orders.model.OrderStatus status);
    
    /**
     * Find orders by customer name (case-insensitive)
     * @param customerName the customer name
     * @return list of orders for the customer
     */
    List<Order> findByCustomerNameContainingIgnoreCase(String customerName);
}
