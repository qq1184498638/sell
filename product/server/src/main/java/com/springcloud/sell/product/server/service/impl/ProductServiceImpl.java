package com.springcloud.sell.product.server.service.impl;

import com.google.common.collect.Lists;
import com.springcloud.sell.product.common.domain.DeceaseStockInPut;
import com.springcloud.sell.product.common.domain.ProductInfoOutPut;
import com.springcloud.sell.product.server.domian.ProductInfo;
import com.springcloud.sell.product.server.enums.Const;
import com.springcloud.sell.product.server.rabbitmq.MQSender;
import com.springcloud.sell.product.server.repository.ProductInfoRepository;
import com.springcloud.sell.product.server.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private MQSender mqSender;

    @Autowired
    private ModelMapper modelMapper;

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
        List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockInPutList);
        List<ProductInfoOutPut> productInfoOutPutList = productInfoList.stream().map(e -> {
            return modelMapper.map(e, ProductInfoOutPut.class);
        }).collect(Collectors.toList());
        mqSender.sendMessage(productInfoOutPutList);
    }

    @Transactional
    private List<ProductInfo> decreaseStockProcess(List<DeceaseStockInPut> decreaseStockInPutList) {
        List<ProductInfo> productInfoList = Lists.newArrayList();
        decreaseStockInPutList.forEach(decreaseStockInPut -> {
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(decreaseStockInPut.getProductId());
            //判断商品是否存在
            if (!productInfoOptional.isPresent()) {
                throw new RuntimeException(Const.ResultEnum.PRODUCT_NOT_EXIST.getMessage());
            }
            ProductInfo productInfo = productInfoOptional.get();
            //库存是否够
            int result = productInfo.getProductStock() - decreaseStockInPut.getProductQuantity();
            if (result < 0) {
                throw new RuntimeException(Const.ResultEnum.PRODUCT_STOCK_ERROR.getMessage());
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
            productInfoList.add(productInfo);
        });
        return productInfoList;
    }
}
