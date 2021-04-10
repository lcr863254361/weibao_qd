package com.orient.download.bean.uploadWaterRunning;

import java.util.List;

/**
 * Author WangJie
 * Created on 2018/10/15.
 */
public class UploadDiveReq {
    private List<DiveModel> diveList;

    public UploadDiveReq() {
    }

    public UploadDiveReq(List<DiveModel> diveList) {
        this.diveList = diveList;
    }

    public List<DiveModel> getDiveList() {
        return diveList;
    }

    public void setDiveList(List<DiveModel> diveList) {
        this.diveList = diveList;
    }

    public int getListsize(){
        return this.diveList.size();
    }
}
