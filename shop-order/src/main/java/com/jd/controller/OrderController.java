package com.jd.controller;

import com.alibaba.fastjson.JSON;
import com.jd.domain.Order;
import com.jd.domain.Product;
import com.jd.service.OrderService;
//import com.jd.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DiscoveryClient discoveryClient;

//    @Autowired
//    private ProductService productService;

    /**
     * 基于Feign实现服务调用
     * http://localhost:8091/order/prod/1
     * @param productId
     * @return
     */
//    @GetMapping("/order/prod/{productId}")
//    public Order order(@PathVariable("productId") Long productId) {
//        log.info(">>客户下单，这时候要调用商品微服务查询商品信息");
//
//        //通过restTemplate调用商品微服务
//        Product product = productService.findByPid(productId);
//
//        log.info(">>商品信息,查询结果:" + JSON.toJSONString(product));
//        Order order = new Order();
//        order.setUserId(1L);
//        order.setUsername("测试用户");
//        order.setProductId(product.getId());
//        order.setProductName(product.getName());
//        order.setProductPrice(product.getPrice());
//        order.setNumber(1);
//        orderService.save(order);
//        return order;
//    }

    /**
     * Ribbon的负载均衡
     *
     * @param productId
     * @return
     */
//    @GetMapping("/order/prod/{productId}")
//    public Order order(@PathVariable("productId") Long productId) {
//        log.info(">>客户下单，这时候要调用商品微服务查询商品信息");
//
//        String url = "service-product";
//
//        //通过restTemplate调用商品微服务
//        Product product = restTemplate.getForObject(
//                "http://" + url + "/product/" + productId, Product.class);
//
//        log.info(">>商品信息,查询结果:" + JSON.toJSONString(product));
//        Order order = new Order();
//        order.setUserId(1L);
//        order.setUsername("测试用户");
//        order.setProductId(product.getId());
//        order.setProductName(product.getName());
//        order.setProductPrice(product.getPrice());
//        order.setNumber(1);
//        orderService.save(order);
//        return order;
//    }


    /**
     * 自定义实现负载均衡
     * @param productId
     * @return
     */
    @GetMapping("/order/prod/{productId}")
    public Order order(@PathVariable("productId") Long productId) {
        log.info(">>客户下单，这时候要调用商品微服务查询商品信息");


        //从nacos中获取服务地址
        List<ServiceInstance> instanceList = discoveryClient.getInstances("service-product");
        int index = new Random().nextInt(instanceList.size());
        log.info("index:{}", index);
        ServiceInstance serviceInstance = instanceList.get(index);
        String url = serviceInstance.getHost() + ":" + serviceInstance.getPort();
        log.info("url:{}", url);

        //通过restTemplate调用商品微服务
        Product product = restTemplate.getForObject(
                "http://" + url + "/product/" + productId, Product.class);

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


    /**
     * 从nacos中获取服务地址
     * @param productId
     * @return
     */
//    @GetMapping("/order/prod/{productId}")
//    public Order order(@PathVariable("productId") Long productId) {
//        log.info(">>客户下单，这时候要调用商品微服务查询商品信息");
//
//
//        //从nacos中获取服务地址
//        ServiceInstance serviceInstance = discoveryClient.getInstances("service-product").get(0);
//        String url = serviceInstance.getHost() + ":" + serviceInstance.getPort();
//
//
//
//        //通过restTemplate调用商品微服务
//        Product product = restTemplate.getForObject(
//                "http://" + url + "/product/" + productId, Product.class);
//
//        log.info(">>商品信息,查询结果:" + JSON.toJSONString(product));
//        Order order = new Order();
//        order.setUserId(1L);
//        order.setUsername("测试用户");
//        order.setProductId(product.getId());
//        order.setProductName(product.getName());
//        order.setProductPrice(product.getPrice());
//        order.setNumber(1);
//        orderService.save(order);
//        return order;
//    }

    /**
     * http://localhost:8091/order/prod/1
     * @param productId
     * @return
     */
//    @GetMapping("/order/prod/{productId}")
//    public Order order(@PathVariable("productId") Long productId) {
//        log.info(">>客户下单，这时候要调用商品微服务查询商品信息");
//
//        //通过restTemplate调用商品微服务
//        Product product = restTemplate.getForObject(
//                "http://localhost:8081/product/" + productId, Product.class);
//
//        log.info(">>商品信息,查询结果:" + JSON.toJSONString(product));
//        Order order = new Order();
//        order.setUserId(1L);
//        order.setUsername("测试用户");
//        order.setProductId(product.getId());
//        order.setProductName(product.getName());
//        order.setProductPrice(product.getPrice());
//        order.setNumber(1);
//        orderService.save(order);
//        return order;
//    }


}
