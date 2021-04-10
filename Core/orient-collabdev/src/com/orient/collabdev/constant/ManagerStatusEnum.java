package com.orient.collabdev.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/27 0027.
 * 管理状态
 */
public enum ManagerStatusEnum {
    UNSTART("0"), PROCESSING("1"), DONE("2");
    private String name;

    ManagerStatusEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static final Map<String, ManagerStatusEnum> stringToEnum = new HashMap<>();

    static {
        for (ManagerStatusEnum s : values()) {
            stringToEnum.put(s.toString(), s);
        }
    }

    public static ManagerStatusEnum fromString(String name) {
        return stringToEnum.get(name);
    }
}
