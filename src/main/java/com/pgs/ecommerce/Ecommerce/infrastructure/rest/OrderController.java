package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.ecommerce.Ecommerce.application.OrderService;
import com.pgs.ecommerce.Ecommerce.domain.model.Order;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing orders.
 * Provides endpoints for creating, updating, and retrieving orders.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Creates a new order.
     *
     * @param order the order to be created
     * @return the created order
     */
    @PostMapping
    public ResponseEntity<Order> save(@RequestBody Order order) {
        log.info("Request to save order: {}", order);
        try {
            Order savedOrder = this.orderService.save(order);
            log.info("Order saved successfully with ID: {}", savedOrder.getId());
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            log.error("Error saving order: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates the state of an existing order.
     *
     * @param id    the ID of the order to be updated
     * @param state the new state of the order
     * @return a response indicating the outcome of the update operation
     */
    @PatchMapping("/update/state/order")
    public ResponseEntity<?> updateStateById(@RequestParam Integer id, @RequestParam String state) {
        log.info("Request to update state of order with ID: {} to state: {}", id, state);
        try {
            this.orderService.updateStateId(id, state);
            log.info("Order with ID: {} updated successfully to state: {}", id, state);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Update successful");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error updating order state for ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves all orders.
     *
     * @return a list of all orders
     */
    @GetMapping
    public ResponseEntity<Iterable<Order>> findAll() {
        log.info("Request to retrieve all orders");
        try {
            Iterable<Order> orders = this.orderService.findAll();
            log.info("Successfully retrieved orders");
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("Error retrieving orders: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to be retrieved
     * @return the order with the specified ID, or a 404 status if not found
     */
    @GetMapping("{id}")
    public ResponseEntity<Order> findByID(@PathVariable Integer id) {
        log.info("Request to retrieve order with ID: {}", id);
        try {
            Order order = this.orderService.findById(id);
            if (order != null) {
                log.info("Successfully retrieved order with ID: {}", id);
                return ResponseEntity.ok(order);
            } else {
                log.warn("Order with ID: {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            log.error("Error retrieving order with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves all orders for a specific user.
     *
     * @param id the ID of the user
     * @return a list of orders associated with the specified user
     */
    @GetMapping("/by-user/{id}")
    public ResponseEntity<Iterable<Order>> findByUserID(@PathVariable Integer id) {
        log.info("Request to retrieve orders for user with ID: {}", id);
        try {
            Iterable<Order> orders = this.orderService.findByUserId(id);
            log.info("Successfully retrieved orders for user with ID: {}", id);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("Error retrieving orders for user with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
