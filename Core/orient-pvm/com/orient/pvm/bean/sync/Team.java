package com.orient.pvm.bean.sync;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbin on 16/8/1.
 * Purpose:
 * Detail:
 */
public class Team {

    String uploadUserId;  //上传人

    List<UserId> userIds = new ArrayList<>();

    public List<UserId> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<UserId> userIds) {
        this.userIds = userIds;
    }

    public String getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(String uploadUserId) {
        this.uploadUserId = uploadUserId;
    }
}
