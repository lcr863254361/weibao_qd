package com.orient.utils.restful;

/**
 * restful response result
 *
 * @author Seraph
 *         2016-12-21 下午3:35
 */
public class RestfulResponse<T> {

    public RestfulResponse(boolean success, int statusCode){
        this.success = success;
        this.statusCode = statusCode;
    }

    public RestfulResponse(String errorMsg){
        this.errorMsg = errorMsg;
    }

    private boolean success;
    private int statusCode;
    private T result;
    private String errorMsg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
