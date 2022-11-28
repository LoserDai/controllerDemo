package com.com.df.vo;

import com.com.df.enums.ResultCode;
import com.com.df.enums.StatusCode;
import lombok.Data;

/**
 * @Author feng.dai
 * @Date 2022/10/18 14:25
 * @Project_Name controllerDemo
 * @Package_Name com.com.df.resultVo
 */
@Data
public class ResultVo {

    private int code;

    private String msg;

    private Object data;

    /**
     * 手动设置返回vo
     * @param code
     * @param msg
     * @param data
     */
    public ResultVo(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 默认返回成功状态码，数据对象
     * @param data
     */
    public ResultVo(Object data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
        this.data = data;
    }

    /**
     * 返回指定状态码，数据对象
     */
    public ResultVo(StatusCode statusCode, Object data) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }

    /**
     * 只返回状态码
     */
    public ResultVo(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = null;
    }
}
