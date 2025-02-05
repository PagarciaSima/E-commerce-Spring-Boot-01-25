package com.pgs.ecommerce.Ecommerce.application;

import java.util.NoSuchElementException;

import com.pgs.ecommerce.Ecommerce.domain.model.Order;
import com.pgs.ecommerce.Ecommerce.domain.port.IOrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing orders in the system.
 */
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final IOrderRepository iOrderRepository;

    /**
     * Saves a new order to the database.
     *
     * @param order The order to be saved.
     * @return The saved order with its assigned ID.
     */
    public Order save(Order order) {
        log.info("Saving new order: {}", order);
        Order savedOrder = this.iOrderRepository.save(order);
        log.info("Order saved successfully with ID: {}", savedOrder.getId());
        return savedOrder;
    }

    /**
     * Retrieves all stored orders.
     *
     * @return An iterable containing all orders.
     */
    public Iterable<Order> findAll() {
        log.info("Fetching all orders");
        Iterable<Order> orders = this.iOrderRepository.findAll();
        log.info("Retrieved orders");
        return orders;
    }

    /**
     * Retrieves all orders for a specific user.
     *
     * @param id The user ID whose orders should be fetched.
     * @return An iterable containing all orders associated with the user.
     */
    public Iterable<Order> findByUserId(Integer id) {
        log.info("Fetching orders for user ID: {}", id);
        Iterable<Order> orders = this.iOrderRepository.findByUserId(id);
        log.info("Retrieved orders for user ID: {}", id);
        return orders;
    }

    /**
     * Updates the state of a specific order.
     *
     * @param id    The ID of the order to update.
     * @param state The new state of the order.
     */
    public void updateStateId(Integer id, String state) {
        log.info("Updating state of order ID: {} to '{}'", id, state);
        this.iOrderRepository.updateStateById(id, state);
        log.info("Order ID: {} updated to state '{}'", id, state);
    }

    /**
     * Finds an order by its ID.
     *
     * @param id The ID of the order to find.
     * @return The found order.
     * @throws NoSuchElementException If the order does not exist.
     */
    public Order findById(Integer id) {
        log.info("Fetching order with ID: {}", id);
        Order order = this.iOrderRepository.findById(id);
        if (order == null) {
            log.warn("Order with ID: {} not found", id);
            throw new NoSuchElementException("Order not found with ID " + id);
        }
        log.info("Order retrieved successfully: {}", order);
        return order;
    }
}
