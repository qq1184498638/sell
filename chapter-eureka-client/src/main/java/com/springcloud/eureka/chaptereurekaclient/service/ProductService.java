package com.springcloud.eureka.chaptereurekaclient.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "product")
public interface ProductService {
    @GetMapping("msg")
    String getMsg();
}
