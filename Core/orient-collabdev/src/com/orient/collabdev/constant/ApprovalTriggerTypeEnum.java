package com.orient.collabdev.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-07 10:47 AM
 */
public enum ApprovalTriggerTypeEnum {

    SUBMIT("submit") {

    }, MODIFTY("modify") {

    }, DELETE("delete") {

    };

    private String name;

    ApprovalTriggerTypeEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static final Map<String, ApprovalTriggerTypeEnum> stringToEnum = new HashMap<>();

    static {
        for (ApprovalTriggerTypeEnum s : values()) {
            stringToEnum.put(s.toString(), s);
        }
    }

    public static ApprovalTriggerTypeEnum fromString(String name) {
        return stringToEnum.get(name);
    }
}
