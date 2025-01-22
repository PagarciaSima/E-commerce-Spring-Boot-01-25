package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.ecommerce.Ecommerce.application.OrderService;
import com.pgs.ecommerce.Ecommerce.domain.model.Order;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/orders")
@Slf4j
@AllArgsConstructor
// http://localhost:8085/api/v1/orders
public class OrderController {

	private final OrderService orderService;
	
	@PostMapping
	public ResponseEntity<Order> save(@RequestBody Order order) {
		return ResponseEntity.ok(this.orderService.save(order));
	}
	
	@PostMapping("/update/state/order")
	public ResponseEntity<String> updateStateById(@RequestParam Integer id, @RequestParam String state) {
		this.orderService.updateStateId(id, state);
		return new ResponseEntity<String>("Update successful", HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Order>> findAll() {
		return ResponseEntity.ok(this.orderService.findAll())
	}
}
