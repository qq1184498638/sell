package com.springcloud.sell.product.server.rabbitmq;

import com.springcloud.sell.product.common.domain.ProductInfoOutPut;
import com.springcloud.sell.product.server.config.MQConfig;
import com.springcloud.sell.product.server.service.ProductService;
import com.springcloud.sell.product.server.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MQReceiver {
    @Autowired
    private ProductService productService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receiveInfo(String message) {
        log.info("receive message:" + message);
        List<ProductInfoOutPut> productInfoOutPutList = JsonUtil.string2Obj(message, List.class, ProductInfoOutPut.class);
        productInfoOutPutList.forEach(e ->{
            log.info("logs ------------------------{}", e.toString());
        });
    }

}
