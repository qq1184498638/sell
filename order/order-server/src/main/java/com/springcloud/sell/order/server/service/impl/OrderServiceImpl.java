package com.springcloud.sell.order.server.service.impl;

import com.springcloud.sell.order.server.domain.OrderDetail;
import com.springcloud.sell.order.server.domain.OrderMaster;
import com.springcloud.sell.order.server.dto.OrderDTO;
import com.springcloud.sell.order.server.enums.Const;
import com.springcloud.sell.order.server.repository.OrderDetailRepository;
import com.springcloud.sell.order.server.repository.OrderMasterRepository;
import com.springcloud.sell.order.server.service.OrderService;
import com.springcloud.sell.order.server.utils.KeyUtil;
import com.springcloud.sell.product.client.ProductClient;
import com.springcloud.sell.product.common.domain.DeceaseStockInPut;
import com.springcloud.sell.product.common.domain.ProductInfoOutPut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private ProductClient productClient;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        //查询商品信息（调用商品服务)
        List<String> productIdList = orderDTO.getOrderDetailList().stream().map(OrderDetail::getProductId).collect(Collectors.toList());
        List<ProductInfoOutPut> productInfoList = productClient.listForOrder(productIdList);

        //计算总价
        BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProductInfoOutPut productInfo : productInfoList) {
                if (productInfo.getProductId().equals(orderDetail.getProductId())) {
                    //单价* 数量
                    orderAmout = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmout);
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    orderDetailRepository.save(orderDetail);
                }
            }
        }

        //扣库存
        List<DeceaseStockInPut> decreaseStockInPutList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DeceaseStockInPut(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());


        productClient.decreaseStock(decreaseStockInPutList);

        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmout);
        orderMaster.setOrderStatus(Const.OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(Const.PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
