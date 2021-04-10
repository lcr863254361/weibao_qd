package com.orient.dsrestful.enums;

/**
 * Created by GNY on 2018/3/27
 */
public enum JudgeCanDeleteResponseEnum {

    /**
     * 0表示可以删除
     * 1表示已删除
     * 2表示已加锁不能删除
     */
    TYPE_CAN_DELETE("0"),
    TYPE_HAS_DELETE("1"),
    TYPE_LOCKED_CAN_NOT_DELETE("2");

    JudgeCanDeleteResponseEnum(String type) {
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
