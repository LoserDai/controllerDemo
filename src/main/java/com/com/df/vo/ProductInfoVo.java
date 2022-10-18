package com.com.df.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author feng.dai
 * @Date 2022/10/18 14:53
 * @Project_Name controllerDemo
 * @Package_Name com.com.df.vo
 */
@Data
public class ProductInfoVo {

    @NotNull(message = "商品名称不允许为空")
    private String productName;

    @Min(value = 0, message = "商品价格不允许为负数")
    private BigDecimal productPrice;

    private Integer productStatus;

}
