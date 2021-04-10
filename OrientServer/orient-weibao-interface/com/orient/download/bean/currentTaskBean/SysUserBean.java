package com.orient.download.bean.currentTaskBean;

import java.io.Serializable;

/**
 * Created by User on 2018/9/26.
 */
public class SysUserBean implements Serializable{
    private int id;
    //填表人名及签署人名称
    private String personnelName="";
    private String loginPassword="";
    //登录用户名
    private String username="";
    //2021.2.2增加人员分类，只有人员分类是1的类型(潜航员)，才去同步分系统及设备接口
    private String personClassify="";

    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonClassify() {
        return personClassify;
    }

    public void setPersonClassify(String personClassify) {
        this.personClassify = personClassify;
    }
}
