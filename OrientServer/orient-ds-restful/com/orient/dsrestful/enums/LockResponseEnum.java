package com.orient.dsrestful.enums;

/**
 * 服务端上锁状态返回值枚举
 *
 * @author GNY
 * @create 2018-03-22 10:17
 */
public enum LockResponseEnum {

    /**
     * 1:上锁成功
     * 2:已经被其他用户上锁
     * 3:解锁成功
     * 4:当前schema非该用户上锁,无法解锁
     * 5:schema不存在
     * 6:服务端异常
     * 7:已经被其他人解锁，无需解锁
     */
    TYPE_LOCK_SUCCESS("1"),
    TYPE_HAS_LOCKED("2"),
    TYPE_UNLOCK_SUCCESS("3"),
    TYPE_CAN_NOT_UNLOCK("4"),
    TYPE_SCHEMA_NOT_EXIST("5"),
    TYPE_EXCEPTION("6"),
    TYPE_HAS_UNLOCKED("7");

    private String type;

    LockResponseEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String value() {
        return this.type;
    }

    public static LockResponseEnum valueOf(int type) {
        switch (type) {
            case 1:
                return TYPE_LOCK_SUCCESS;
            case 2:
                return TYPE_HAS_LOCKED;
            case 3:
                return TYPE_UNLOCK_SUCCESS;
            case 4:
                return TYPE_CAN_NOT_UNLOCK;
            case 5:
                return TYPE_SCHEMA_NOT_EXIST;
            case 6:
                return TYPE_EXCEPTION;
            default:
                return null;
        }
    }

}
