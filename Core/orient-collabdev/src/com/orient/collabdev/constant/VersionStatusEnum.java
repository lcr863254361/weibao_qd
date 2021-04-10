package com.orient.collabdev.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/27 0027.
 * 版本状态
 */
public enum VersionStatusEnum {
    LATEST("0"), HISTORY("1");
    private String name;

    VersionStatusEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static final Map<String, VersionStatusEnum> stringToEnum = new HashMap<>();

    static {
        for (VersionStatusEnum s : values()) {
            stringToEnum.put(s.toString(), s);
        }
    }

    public static VersionStatusEnum fromString(String name) {
        return stringToEnum.get(name);
    }
}
