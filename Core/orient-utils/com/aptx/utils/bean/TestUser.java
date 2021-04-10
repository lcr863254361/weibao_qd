package com.aptx.utils.bean;

import com.aptx.utils.GsonUtil;

import java.io.Serializable;
import java.util.Date;

public class TestUser implements Serializable {
    private Long id;
    private String username;
    private Date birthday;

    public TestUser() {

    }

    public TestUser(Long id, String username, Date birthday) {
        this.id = id;
        this.username = username;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return GsonUtil.toJson(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
