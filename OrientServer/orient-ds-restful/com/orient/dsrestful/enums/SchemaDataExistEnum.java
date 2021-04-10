package com.orient.dsrestful.enums;

/**
 * Created by GNY on 2018/3/26
 */
public enum SchemaDataExistEnum {

    /**
     * 1表示该schema为加锁状态;
     * 2表示schema不存在;
     * 3表示有数据;
     * 4表示无数据;
     * 5表示表不存在;
     * 6表示有异常
     */
    TYPE_LOCKED("1"),
    TYPE_SCHEMA_NOT_EXIST("2"),
    TYPE_HAS_DATA("3"),
    TYPE_NO_DATA("4"),
    TYPE_TABLE_NOT_EXIST("5"),
    TYPE_EXCEPTION("6");

    SchemaDataExistEnum(String type) {
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
