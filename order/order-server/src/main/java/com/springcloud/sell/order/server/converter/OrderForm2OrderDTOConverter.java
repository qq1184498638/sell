package com.springcloud.sell.order.server.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.springcloud.sell.order.server.domain.OrderDetail;
import com.springcloud.sell.order.server.dto.OrderDTO;
import com.springcloud.sell.order.server.enums.Const;
import com.springcloud.sell.order.server.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 廖师兄
 * 2017-12-10 17:38
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
            log.error("【json转换】错误, string={}", orderForm.getItems());
            throw new RuntimeException(Const.ResultEnum.PARAM_ERROR.getMessage());
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
