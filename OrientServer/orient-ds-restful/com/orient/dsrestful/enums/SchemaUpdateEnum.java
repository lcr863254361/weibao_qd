package com.orient.dsrestful.enums;

/**
 * Created by GNY on 2018/5/5.
 */
public enum SchemaUpdateEnum {

    /**
     * -2：字段有值，无法更新字段长度
     * -1：异常
     * 0：更新成功
     */
    TYPE_COLUMN_HAS_VALUE("-2"),
    TYPE_EXCEPTION("-1"),
    TYPE_SUCCESS("0");

    private String type;

    SchemaUpdateEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
