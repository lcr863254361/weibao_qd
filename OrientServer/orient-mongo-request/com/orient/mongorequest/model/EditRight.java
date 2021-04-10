package com.orient.mongorequest.model;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-06-07 9:09
 */
public class EditRight extends CommonResponse {

    private String userId;
    private long lastModifyTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

}
