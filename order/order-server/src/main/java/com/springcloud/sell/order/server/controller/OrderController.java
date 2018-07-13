package com.springcloud.sell.order.server.controller;

import com.google.common.collect.Maps;
import com.springcloud.sell.order.server.converter.OrderForm2OrderDTOConverter;
import com.springcloud.sell.order.server.dto.OrderDTO;
import com.springcloud.sell.order.server.enums.Const;
import com.springcloud.sell.order.server.form.OrderForm;
import com.springcloud.sell.order.server.service.OrderService;
import com.springcloud.sell.order.server.utils.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 1. 参数检验
     * 2. 查询商品信息(调用商品服务)
     * 3. 计算总价
     * 4. 扣库存(调用商品服务)
     * 5. 订单入库
     */
    @PostMapping("/create")
    public ServerResponse<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) throws RuntimeException {
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new RuntimeException(Const.ResultEnum.PARAM_ERROR.getMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车信息为空");
            throw new RuntimeException(Const.ResultEnum.CART_EMPTY.getMessage());
        }

        OrderDTO result = orderService.create(orderDTO);
        Map<String, String> map = Maps.newHashMap();
        map.put("orderId", result.getOrderId());
        return ServerResponse.createBySuccess(map);
    }

    @GetMapping("/msg")
    public String getMsg() {
        return "msg";
    }

}
