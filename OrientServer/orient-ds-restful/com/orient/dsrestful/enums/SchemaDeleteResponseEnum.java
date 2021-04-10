package com.orient.dsrestful.enums;

/**
 * 删除schema结果返回枚举
 *
 * Created by GNY on 2018/3/26
 */
public enum SchemaDeleteResponseEnum {

    /**
     * -1表示删除异常
     * 0表示删除成功
     * 1表示schema不存在
     */
    TYPE_EXCEPTION("-1"),
    TYPE_DELETE_SUCCESS("0"),
    TYPE_SCHEMA_NOT_EXIST("1");

    SchemaDeleteResponseEnum(String type) {
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
