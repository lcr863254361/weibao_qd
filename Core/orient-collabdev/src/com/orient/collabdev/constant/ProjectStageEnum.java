package com.orient.collabdev.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/27 0027.
 * 项目时态
 */
public enum ProjectStageEnum {
    DESIGNING("DESIGNING"), PROCESSING("PROCESSING");
    private String name;

    ProjectStageEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static final Map<String, ProjectStageEnum> stringToEnum = new HashMap<>();

    static {
        for (ProjectStageEnum s : values()) {
            stringToEnum.put(s.toString(), s);
        }
    }

    public static ProjectStageEnum fromString(String name) {
        return stringToEnum.get(name);
    }
}
