package com.orient.dsrestful.domain.lock;

import com.orient.web.base.dsbean.CommonDataBean;

/**
 * Created by GNY on 2018/3/22
 */
public class LockStatus extends CommonDataBean {

    private String username;    //用户名
    private String ip;          //ip

    public LockStatus() {
    }

    public LockStatus(String username, String ip) {
        this.username = username;
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
