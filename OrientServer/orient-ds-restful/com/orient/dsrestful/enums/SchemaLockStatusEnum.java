package com.orient.dsrestful.enums;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-23 15:10
 */
public enum SchemaLockStatusEnum {

    /**
     * 0表示schema没有上锁
     * 1表示schema已经上锁
     */
    TYPE_UNLOCKED(0),
    TYPE_LOCKED(1);

    private int type;

    SchemaLockStatusEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int value() {
        return this.type;
    }

    public static SchemaLockStatusEnum valueOf(int type) {
        switch (type) {
            case 0:
                return TYPE_UNLOCKED;
            case 1:
                return TYPE_LOCKED;
            default:
                return TYPE_UNLOCKED;
        }
    }

}
