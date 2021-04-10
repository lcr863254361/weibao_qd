package com.orient.modeldata.validateHandler.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class ValidateError implements Serializable{

    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
