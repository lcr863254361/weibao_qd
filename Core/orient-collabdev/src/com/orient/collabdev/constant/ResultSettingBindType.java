package com.orient.collabdev.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author GNY
 * @Date 2018/8/9 10:23
 * @Version 1.0
 **/
public enum ResultSettingBindType {

    DEV("1"), COMPONENT("2"), PVM("3");

    private String name;

    ResultSettingBindType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static final Map<String, ResultSettingBindType> stringToEnum = new HashMap<>();

    static {
        for (ResultSettingBindType s : values()) {
            stringToEnum.put(s.toString(), s);
        }
    }

    public static ResultSettingBindType fromString(String name) {
        return stringToEnum.get(name);
    }

}
