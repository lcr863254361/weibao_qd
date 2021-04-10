package com.orient.dsrestful.domain.login;

import java.io.Serializable;

/**
 * Created by GNY on 2018/3/24
 */
public class LoginRequestBean implements Serializable {

    private String username;
    private String password;
    private String ip;
    private String clientType;

    public LoginRequestBean() {
    }

    public LoginRequestBean(String username, String password, String ip, String clientType) {
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.clientType = clientType;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

}
