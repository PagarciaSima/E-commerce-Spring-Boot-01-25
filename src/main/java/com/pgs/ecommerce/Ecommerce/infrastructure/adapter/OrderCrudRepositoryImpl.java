package com.pgs.ecommerce.Ecommerce.infrastructure.adapter;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import com.pgs.ecommerce.Ecommerce.domain.model.Order;
import com.pgs.ecommerce.Ecommerce.domain.model.OrderState;
import com.pgs.ecommerce.Ecommerce.domain.port.IOrderRepository;
import com.pgs.ecommerce.Ecommerce.infrastructure.entity.OrderEntity;
import com.pgs.ecommerce.Ecommerce.infrastructure.entity.UserEntity;
import com.pgs.ecommerce.Ecommerce.infrastructure.mapper.IOrderMapper;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class OrderCrudRepositoryImpl implements IOrderRepository{
	
	private final IOrderMapper iOrderMapper;
	private final IOrderCrudRepository iOrderCrudRepository ;
	
	 /**
     * Saves the given order to the data source.
     *
     * @param order the order to be saved
     * @return the saved order
     */
    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = iOrderMapper.toOrderEntity(order);
        orderEntity.getOrderProducts().forEach(
            orderProductEntity -> orderProductEntity.setOrderEntity(orderEntity)
        );
        return iOrderMapper.toOrder(iOrderCrudRepository.save(orderEntity));
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to retrieve
     * @return the retrieved order
     * @throws NoSuchElementException if no order with the given ID is found
     */
    @Override
    public Order findById(Integer id) {
        return iOrderMapper.toOrder(iOrderCrudRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Order ID " + id + " not found")
        ));
    }

    /**
     * Retrieves all orders from the data source.
     *
     * @return an iterable collection of all orders
     */
    @Override
    public Iterable<Order> findAll() {
        return iOrderMapper.toOrderList(iOrderCrudRepository.findAll());
    }

    /**
     * Retrieves all orders associated with a specific user ID.
     *
     * @param userId the ID of the user whose orders are to be retrieved
     * @return an iterable collection of the user's orders
     */
    @Override
    public Iterable<Order> findByUserId(Integer userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return iOrderMapper.toOrderList(iOrderCrudRepository.findByUserEntity(userEntity));
    }

    /**
     * Updates the state of an order by its ID.
     *
     * @param id    the ID of the order to update
     * @param state the new state to set for the order
     */
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
