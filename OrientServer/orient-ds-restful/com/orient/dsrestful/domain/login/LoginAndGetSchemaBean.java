package com.orient.dsrestful.domain.login;

/**
 * Created by GNY on 2018/3/24
 */
public class LoginAndGetSchemaBean extends LoginRequestBean {

    private boolean getAll;  //true 获取所有schema, false 获取上锁schema

    public boolean isGetAll() {
        return getAll;
    }

    public void setGetAll(boolean getAll) {
        this.getAll = getAll;
    }

}
