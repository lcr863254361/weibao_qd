package com.orient.web.base.dsbean;

import java.io.Serializable;

/**
 * Created by GNY on 2018/3/23
 */
public class CommonDataBean implements Serializable {

    private String status;

    public CommonDataBean() {
    }

    public CommonDataBean(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
