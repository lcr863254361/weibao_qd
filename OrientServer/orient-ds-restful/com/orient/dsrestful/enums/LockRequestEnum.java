package com.orient.dsrestful.enums;

/**
 * 客户端上锁请求状态枚举
 *
 * @author GNY
 * @create 2018-03-22 10:34
 */
public enum LockRequestEnum {

    /**
     * 0表示客户端请求解锁
     * 1表示客户端请求上锁
     */
    TYPE_TO_UNLOCK(0),
    TYPE_TO_LOCK(1);

    private int type;

    LockRequestEnum(int type) {
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

    public static LockRequestEnum valueOf(int type) {
        switch (type) {
            case 0:
                return TYPE_TO_UNLOCK;
            case 1:
                return TYPE_TO_LOCK;
            default:
                return TYPE_TO_UNLOCK;
        }
    }

}
