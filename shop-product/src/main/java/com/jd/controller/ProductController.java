package com.jd.controller;

import com.alibaba.fastjson.JSON;
import com.jd.domain.Product;
import com.jd.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/product/{id}")
    public Product product(@PathVariable("id") Long id){
        Product product = productService.findById(id);
        log.info("商品信息:{}", JSON.toJSONString(product));
        return product;
    }
}
