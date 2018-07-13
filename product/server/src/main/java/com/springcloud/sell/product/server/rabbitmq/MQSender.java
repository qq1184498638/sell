package com.springcloud.sell.product.server.rabbitmq;

import com.springcloud.sell.product.server.config.MQConfig;
import com.springcloud.sell.product.server.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Object message) {
        String msg = JsonUtil.obj2String(message);
        log.info("send message:" + msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }

}
