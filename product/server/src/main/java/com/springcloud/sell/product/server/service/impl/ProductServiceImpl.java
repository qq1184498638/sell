package com.springcloud.sell.product.server.service.impl;

import com.springcloud.sell.product.common.domain.DeceaseStockInPut;
import com.springcloud.sell.product.common.domain.ProductInfoOutPut;
import com.springcloud.sell.product.server.domian.ProductInfo;
import com.springcloud.sell.product.server.enums.Const;
import com.springcloud.sell.product.server.repository.ProductInfoRepository;
import com.springcloud.sell.product.server.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(Const.ProductStatusEnum.ON_SALE.getCode());
    }

    @Override
    public List<ProductInfoOutPut> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList).stream()
                .map(e -> {
                    ProductInfoOutPut outPut = new ProductInfoOutPut();
                    BeanUtils.copyProperties(e, outPut);
                    return outPut;
                }).collect(Collectors.toList());
    }

    @Override
    public void decreaseStock(List<DeceaseStockInPut> decreaseStockInPutList) {

    }
}
