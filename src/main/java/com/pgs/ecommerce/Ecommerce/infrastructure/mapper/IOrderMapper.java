package com.pgs.ecommerce.Ecommerce.infrastructure.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.pgs.ecommerce.Ecommerce.domain.model.Order;
import com.pgs.ecommerce.Ecommerce.infrastructure.entity.OrderEntity;

@Mapper(componentModel = "spring", uses = {IOrderProductMapper.class})
public interface IOrderMapper {
	
	@Mappings (
			{
				@Mapping(source = "id", target = "id"),
				@Mapping(source = "dateCreated", target = "dateCreated"),
				@Mapping(source = "orderProducts", target = "orderProducts"),
				@Mapping(source = "orderState", target = "orderState"),
				@Mapping(source = "userEntity.id", target = "userId")

			}
	)
	
	Order toOrder (OrderEntity orderEntity);
	Iterable<Order> toOrderList (Iterable<OrderEntity> orderEntities);
	
	@InheritInverseConfiguration
	OrderEntity toOrderEntity(Order order);

}
