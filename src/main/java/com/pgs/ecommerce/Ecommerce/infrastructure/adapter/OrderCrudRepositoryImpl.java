package com.pgs.ecommerce.Ecommerce.infrastructure.adapter;

import java.util.NoSuchElementException;

import com.pgs.ecommerce.Ecommerce.domain.model.Order;
import com.pgs.ecommerce.Ecommerce.domain.model.OrderState;
import com.pgs.ecommerce.Ecommerce.domain.port.IOrderRepository;
import com.pgs.ecommerce.Ecommerce.infrastructure.entity.OrderEntity;
import com.pgs.ecommerce.Ecommerce.infrastructure.entity.UserEntity;
import com.pgs.ecommerce.Ecommerce.infrastructure.mapper.IOrderMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderCrudRepositoryImpl implements IOrderRepository{
	
	private final IOrderMapper iOrderMapper;
	private final IOrderCrudRepository iOrderCrudRepository ;
	
	@Override
	public Order save(Order order) {
		OrderEntity orderEntity = iOrderMapper.toOrderEntity(order);
		orderEntity.getOrderProducts().forEach(
			(orderProductEntity) -> orderProductEntity.setOrderEntity(orderEntity)
		);
		return iOrderMapper.toOrder(iOrderCrudRepository.save(orderEntity)) ;
	}

	@Override
	public Order findById(Integer id) {
		return iOrderMapper.toOrder(iOrderCrudRepository.findById(id).orElseThrow(
			() -> new NoSuchElementException("Order ID " + id + " not found")
		));
	}

	@Override
	public Iterable<Order> findAll() {
		return iOrderMapper.toOrderList(iOrderCrudRepository.findAll());
	}

	@Override
	public Iterable<Order> findByUserId(Integer userId) {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(userId);
		return iOrderMapper.toOrderList(iOrderCrudRepository.findByUserEntity(userEntity));
	}

	@Override
	public void updateStateById(Integer id, String state) {

		OrderState orderState = OrderState.valueOf(state.toUpperCase());

	    if (orderState == OrderState.CANCELLED) {
	        iOrderCrudRepository.updateStateById(id, OrderState.CANCELLED);
	    } else if (orderState == OrderState.CONFIRMED) {
	        iOrderCrudRepository.updateStateById(id, OrderState.CONFIRMED);
	    }

	}

}
