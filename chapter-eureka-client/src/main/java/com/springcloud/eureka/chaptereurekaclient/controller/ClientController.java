package com.springcloud.eureka.chaptereurekaclient.controller;

import com.springcloud.eureka.chaptereurekaclient.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ClientController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductService productService;

    @GetMapping("getMsg")
    public String getMst() {
        String response = restTemplate.getForObject("http://product/msg", String.class);
        log.info("response:{}", response);
        return response;
    }

    @GetMapping("feignMsg")
    public String getFeginMsg() {
        String msg = productService.getMsg();
        log.info("msg:{}", msg);
        return msg;
    }
}
