package com.orient.dsrestful.enums;

/**
 * Created by GNY on 2018/3/26
 */
public enum SequenceResponseEnum {

    /**
     * -1表示出现异常;
     * 0表示重新初始化值成功
     * 1为表不存在不需要重新初始化值;
     * 2为字段不存在,不需要重新初始化值;
     * 3为字段存在，但表序列并没有创建;
     * 4为字段已有数据存在无法重新初始化值；
     */
    TYPE_EXCEPTION("-1"),
    TYPE_RESET_SUCCESS("0"),
    TYPE_TABLE_NOT_EXIST("1"),
    TYPE_COLUMN_NOT_EXIST("2"),
    TYPE_SEQ_NOT_CREATE("3"),
    TYPE_CAN_NOT_RESET("4");

    SequenceResponseEnum(String type) {
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
