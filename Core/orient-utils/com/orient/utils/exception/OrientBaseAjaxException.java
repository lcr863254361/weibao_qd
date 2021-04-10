package com.orient.utils.exception;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class OrientBaseAjaxException extends RuntimeException {
    private String errorCode;

    private String errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public OrientBaseAjaxException() {

    }

    public OrientBaseAjaxException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
