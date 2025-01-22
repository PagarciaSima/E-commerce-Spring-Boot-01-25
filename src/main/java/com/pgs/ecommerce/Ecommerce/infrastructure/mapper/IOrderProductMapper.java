package com.pgs.ecommerce.Ecommerce.infrastructure.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.pgs.ecommerce.Ecommerce.domain.model.OrderProduct;
import com.pgs.ecommerce.Ecommerce.infrastructure.entity.OrderProductEntity;

@Mapper(componentModel = "spring")
public interface IOrderProductMapper {

	@Mappings (
			{
				@Mapping(source = "id", target = "id"),
				@Mapping(source = "quantity", target = "quantity"),
				@Mapping(source = "price", target = "price"),
				@Mapping(source = "productId", target = "productId")

			}
	)
	OrderProduct toOrderProduct(OrderProductEntity orderProductEntity);
	Iterable<OrderProduct> toOrderProductList(Iterable<OrderProductEntity> orderProductEntities);
	
	@InheritInverseConfiguration
	OrderProductEntity toOrderProductEntity (OrderProduct orderProduct);
}
