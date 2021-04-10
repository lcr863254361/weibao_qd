package com.orient.pvm.bean.sync;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbin on 16/8/1.
 * Purpose:
 * Detail:
 */
public class PVMTables {
    private String version = "";

    private List<CheckModel> checkModelList = new ArrayList<>() ;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<CheckModel> getCheckModelList() {
        return checkModelList;
    }

    public void setCheckModelList(List<CheckModel> checkModelList) {
        this.checkModelList = checkModelList;
    }
}
