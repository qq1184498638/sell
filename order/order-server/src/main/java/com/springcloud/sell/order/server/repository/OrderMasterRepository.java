package com.springcloud.sell.order.server.repository;

import com.springcloud.sell.order.server.domain.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository  extends JpaRepository<OrderMaster,String>{
}
