package com.springcloud.sell.product.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeceaseStockInPut {
    private String productId;

    private Integer productQuantity;

}
