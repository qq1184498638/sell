package com.springcloud.sell.product.server.service;

import com.springcloud.sell.product.server.domian.ProductCategory;

import java.util.List;

public interface CategoryService {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
