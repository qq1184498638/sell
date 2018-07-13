package com.springcloud.sell.product.server.service;

import com.springcloud.sell.product.common.domain.DeceaseStockInPut;
import com.springcloud.sell.product.common.domain.ProductInfoOutPut;
import com.springcloud.sell.product.server.domian.ProductInfo;

import java.util.List;

public interface ProductService {
    /**
     * 查询所有在架商品列表
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询商品列表
     */
    List<ProductInfoOutPut> findList(List<String> productIdList);

    /**
     * 扣库存
     */
    void decreaseStock(List<DeceaseStockInPut> decreaseStockInPutList);
}
