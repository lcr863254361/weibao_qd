package com.orient.mongorequest.model;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-06-01 14:14
 */
public class CommonAjaxResponse<T> implements Serializable {

    private String msg;
    private boolean success = false;
    private T results;

    public CommonAjaxResponse() {
    }

    public CommonAjaxResponse(String msg, boolean success, T results) {
        this.msg = msg;
        this.success = success;
        this.results = results;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
