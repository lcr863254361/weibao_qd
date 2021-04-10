package com.orient.collabdev.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-07 10:47 AM
 */
public enum ApprovalTypeEnum {

    NODEAPPROVAL("nodeApproval"), DEVDATAAPPROVAL("devDataApproval");

    private String name;

    ApprovalTypeEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static final Map<String, ApprovalTypeEnum> stringToEnum = new HashMap<>();

    static {
        for (ApprovalTypeEnum s : values()) {
            stringToEnum.put(s.toString(), s);
        }
    }

    public static ApprovalTypeEnum fromString(String name) {
        return stringToEnum.get(name);
    }
}
