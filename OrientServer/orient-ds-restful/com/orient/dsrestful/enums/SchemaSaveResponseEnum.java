package com.orient.dsrestful.enums;

/**
 * 保存schema解果返回枚举
 *
 * Created by GNY on 2018/5/2
 */
public enum SchemaSaveResponseEnum {

    /**
     * -1表示保存异常；
     * 0表示保存成功；
     * 1表示schema重名，前台需要该schema名字或者提高schema的版本号
     */
    TYPE_EXCEPTION("-1"),
    TYPE_SAVE_SUCCESS("0"),
    TYPE_SCHEMA_DUPLICATION_NAME("1");

    SchemaSaveResponseEnum(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
