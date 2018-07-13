package com.springcloud.sell.product.server.repository;

import com.springcloud.sell.product.server.domian.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>  {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
