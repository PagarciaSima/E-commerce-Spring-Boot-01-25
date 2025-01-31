package com.pgs.ecommerce.Ecommerce.application;

import java.util.NoSuchElementException;

import com.pgs.ecommerce.Ecommerce.domain.model.Order;
import com.pgs.ecommerce.Ecommerce.domain.port.IOrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class OrderService {

    private final IOrderRepository iOrderRepository;

    public Order save(Order order) {
        log.info("Saving new order: {}", order);
        Order savedOrder = this.iOrderRepository.save(order);
        log.info("Order saved successfully with ID: {}", savedOrder.getId());
        return savedOrder;
    }

    public Iterable<Order> findAll() {
        log.info("Fetching all orders");
        Iterable<Order> orders = this.iOrderRepository.findAll();
        log.info("Retrieved orders");
        return orders;
    }

    public Iterable<Order> findByUserId(Integer id) {
        log.info("Fetching orders for user ID: {}", id);
        Iterable<Order> orders = this.iOrderRepository.findByUserId(id);
        log.info("Retrieved orders for user ID: {}", id);
        return orders;
    }

    public void updateStateId(Integer id, String state) {
        log.info("Updating state of order ID: {} to '{}'", id, state);
        this.iOrderRepository.updateStateById(id, state);
        log.info("Order ID: {} updated to state '{}'", id, state);
    }

    public Order findById(Integer id) {
        log.info("Fetching order with ID: {}", id);
        Order order = this.iOrderRepository.findById(id);
        if (order == null) {
            log.warn("Order with ID: {} not found", id);
            throw new NoSuchElementException("Order not found ID " + id);
        }
        log.info("Order retrieved successfully: {}", order);
        return order;
    }
}
