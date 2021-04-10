package com.orient.dsrestful.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端类型枚举
 * Created by GNY on 2018/3/23
 */
public enum ClientTypeEnum {

    /**
     * rcp1:ds
     * rcp2:tbom
     * rcp3:etl
     * rcp4:workflow
     */
    TYPE_DESIGN_STUDIO("rcp1"),
    TYPE_TBOM_STUDIO("rcp2"),
    TYPE_ETL_STUDIO("rcp3"),
    TYPE_WORK_FLOW_STUDIO("rcp4");

    private String type;

    ClientTypeEnum(String type) {
        this.type = type;
    }

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

    private static final Map<String, ClientTypeEnum> stringToEnum = new HashMap<>();

    static {
        for (ClientTypeEnum type : values()) {
            stringToEnum.put(type.type, type);
        }
    }

    public static ClientTypeEnum fromString(String symbol) {
        return stringToEnum.get(symbol);
    }

}
