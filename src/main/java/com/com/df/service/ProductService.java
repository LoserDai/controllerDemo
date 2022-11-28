package com.com.df.service;

import com.com.df.enums.StatusCode;
import com.com.df.vo.QueryWapper;

/**
 * @Author feng.dai
 * @Date 2022/10/18 14:21
 * @Project_Name controllerDemo
 * @Package_Name com.df.controller.com.df.service
 */
public interface ProductService {
    StatusCode getOne(QueryWapper queryWapper);
}
