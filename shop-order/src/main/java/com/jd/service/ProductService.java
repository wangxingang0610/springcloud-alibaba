package com.jd.service;

import com.jd.domain.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 声明调用的提供者的name
 */
@FeignClient(value = "service-product")
public interface ProductService {

    /**
     *
     * 指定调用提供者的哪个方法
     * @FeignClient + @GetMapping 就是一个完整的请求路径 http://service-product/product/{pid}
     * @param productId
     * @return
     */
    @GetMapping(value = "/product/{productId}")
    Product findByPid(@PathVariable("productId") Long productId);

}
