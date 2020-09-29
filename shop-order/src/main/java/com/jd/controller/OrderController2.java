package com.jd.controller;

import com.alibaba.fastjson.JSON;
import com.jd.domain.Order;
import com.jd.domain.Product;
import com.jd.service.OrderService;
import com.jd.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController2 {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;


    /**
     * http://localhost:8091/order/prod/1
     *
     * @param productId
     * @return
     */
    @GetMapping("/order/prod/{productId}")
    public Order order(@PathVariable("productId") Long productId) {
        log.info("接收到{}号商品的下单请求,接下来调用商品微服务查询此商品信息", productId);

        //通过restTemplate调用商品微服务
        Product product = productService.findByPid(productId);
        log.info("查询到{}号商品的信息,内容是:{}", productId, JSON.toJSONString(product));

        //模拟一次网络延时
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //下单(创建订单)
        Order order = new Order();
        order.setUserId(1L);
        order.setUsername("测试用户");
        order.setProductId(product.getId());
        order.setProductName(product.getName());
        order.setProductPrice(product.getPrice());
        order.setNumber(1);

        //为了不产生太多垃圾数据,暂时不做订单保存
        //orderService.createOrder(order);
//        orderService.save(order);
        return order;
    }

    /**
     * http://localhost:8091/order/message
     * @return
     */
    @RequestMapping("/order/message")
    public String message() {
        return "高并发下的问题测试";
    }

}
