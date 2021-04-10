package com.orient.dsrestful.enums;

/**
 * @author GNY
 * @create 2018-03-23 10:28
 */
public enum LoginCheckResponseTypeEnum {

    TYPE_LICENCE_ERROR("-2"),
    TYPE_EXCEPTION("-1"),
    TYPE_LOGIN_FAIL("0"),
    TYPE_LOGIN_SUCCESS("1"),
    TYPE_LOCKED("2");

    LoginCheckResponseTypeEnum(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

}
