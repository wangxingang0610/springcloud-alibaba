package com.jd.controller;

import com.alibaba.fastjson.JSON;
import com.jd.domain.Order;
import com.jd.domain.Product;
import com.jd.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderService orderService;


    @GetMapping("/order/prod/{productId}")
    public Order order(@PathVariable("productId") Long productId) {
        log.info(">>客户下单，这时候要调用商品微服务查询商品信息");

        //通过restTemplate调用商品微服务
        Product product = restTemplate.getForObject(
                "http://localhost:8081/product/" + productId, Product.class);

        log.info(">>商品信息,查询结果:" + JSON.toJSONString(product));
        Order order = new Order();
        order.setUserId(1L);
        order.setUsername("测试用户");
        order.setProductId(product.getId());
        order.setProductName(product.getName());
        order.setProductPrice(product.getPrice());
        order.setNumber(1);
        orderService.save(order);
        return order;
    }


}
