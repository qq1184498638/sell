package com.springcloud.sell.product.server.service.impl;

import com.springcloud.sell.product.server.domian.ProductCategory;
import com.springcloud.sell.product.server.repository.ProductCategoryRepository;
import com.springcloud.sell.product.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }
}
