package com.orient.web.springmvcsupport.exception;

/**
 * DS操作的自定义异常
 * Created by GNY on 2018/5/3
 */
public class DSException extends RuntimeException {

    private String status;        //异常状态码
    private String errorMessage;  // 错误信息

    public DSException() {
    }

    public DSException(String status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }



}
