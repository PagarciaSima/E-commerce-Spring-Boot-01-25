package com.pgs.ecommerce.Ecommerce.application;

import com.pgs.ecommerce.Ecommerce.domain.model.Order;
import com.pgs.ecommerce.Ecommerce.domain.port.IOrderRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderService {

    private final IOrderRepository iOrderRepository;

    public Order save(Order order) {
        return this.iOrderRepository.save(order);
    }

    public Iterable<Order> findAll () {
        return this.iOrderRepository.findAll();
    }

    public Iterable<Order> findByUserId (Integer id) {
        return this.iOrderRepository.findByUserId(id);
    }

    public void updateStateId (Integer id, String state) {
        this.iOrderRepository.updateStateById(id, state);
    }
    
    public Order findById(Integer id) {
    	return this.iOrderRepository.findById(id);
    }

}
