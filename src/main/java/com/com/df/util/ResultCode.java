package com.com.df.util;


import lombok.Getter;

/**
 * @Author feng.dai
 * @Date 2022/10/18 14:24
 * @Project_Name controllerDemo
 * @Package_Name com.com.df.util
 */
@Getter
public enum ResultCode implements StatusCode{
    SUCCESS(1000, "请求成功！"),
    FAILED(1001, "请求失败！"),
    VALIDATE_ERROR(1002, "参数校验失败！"),
    RESPONSE_PACK_ERROR(1003,"response返回包装失败！");

    private int code;
    private String msg;

    ResultCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
