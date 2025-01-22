package com.pgs.ecommerce.Ecommerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {

    private Integer id;
    private BigDecimal quantity;
    private BigDecimal price;
    private Integer ProductId;

    public BigDecimal getTotalItem () {
        return this.price.multiply(quantity);
    }

}
