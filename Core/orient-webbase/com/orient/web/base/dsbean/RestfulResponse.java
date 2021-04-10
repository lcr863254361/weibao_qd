package com.orient.web.base.dsbean;

import java.io.Serializable;

/**
 * DS交互接口统一返回格式
 * Created by GNY on 2018/3/22
 */
public class RestfulResponse<T> implements Serializable {

    private boolean success = true; //是否成功
    private String msg = "";        //失败时返回前台的消息
    private T result;               //具体数据

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
