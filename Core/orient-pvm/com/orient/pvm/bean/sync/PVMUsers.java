package com.orient.pvm.bean.sync;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbin on 16/8/2.
 * Purpose:
 * Detail:
 */
public class PVMUsers {

    private List<PVMUser> users =new ArrayList<>();

    public List<PVMUser> getUsers() {
        return users;
    }

    public void setUsers(List<PVMUser> users) {
        this.users = users;
    }
}
