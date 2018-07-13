package com.springcloud.sell.order.server.service;

import com.springcloud.sell.order.server.dto.OrderDTO;

public interface OrderService {
    /**
     * 创建订单
     */
    OrderDTO create(OrderDTO orderDTO);
}
