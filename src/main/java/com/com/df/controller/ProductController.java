package com.com.df.controller;

import com.com.df.service.ProductService;
import com.com.df.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author feng.dai
 * @Date 2022/10/18 14:20
 * @Project_Name controllerDemo
 * @Package_Name com.df.controller
 */
@RestController
@RequestMapping("/product/product_info")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/findById")
    public QueryVo findById(Integer id){
        //TODO
        return new QueryVo();
    }

    @GetMapping("/findByVo")
    public ResultVo findByVo(@Validated ProductInfoVo vo){

        ProductInfo info = new ProductInfo();
        BeanUtils.copyProperties(vo, info);
        return new ResultVo(productService.getOne(new QueryWapper(info)));
    }
}
