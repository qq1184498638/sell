package com.springcloud.sell.product.client;

import com.springcloud.sell.product.common.domain.DeceaseStockInPut;
import com.springcloud.sell.product.common.domain.ProductInfoOutPut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product")
public interface ProductClient {
    @PostMapping("/product/listForOrder")
    public List<ProductInfoOutPut> listForOrder(@RequestBody List<String> prodcutIdList);

    @PostMapping("/product/decreaseStock")
    void decreaseStock(@RequestBody List<DeceaseStockInPut> decreaseStockInPutList);
}
