package com.springcloud.sell.order.server.repository;

import com.springcloud.sell.order.server.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
}
