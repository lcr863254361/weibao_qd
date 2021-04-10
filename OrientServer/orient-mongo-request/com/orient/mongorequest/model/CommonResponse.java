package com.orient.mongorequest.model;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-06-04 15:57
 */
public class CommonResponse {

    private boolean success = true;
    private String msg;
    private boolean alertMsg = true;
    private String responseCode;
    private Object result;

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

    public boolean isAlertMsg() {
        return alertMsg;
    }

    public void setAlertMsg(boolean alertMsg) {
        this.alertMsg = alertMsg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

}
