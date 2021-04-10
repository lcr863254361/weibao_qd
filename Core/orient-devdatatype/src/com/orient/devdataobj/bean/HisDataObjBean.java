package com.orient.devdataobj.bean;

/**
 * Created by mengbin on 16/7/15.
 * Purpose:
 * Detail:
 */
public class HisDataObjBean extends DataObjectBean {

    private Integer dataobjectid;
    private int isdeleted;

    public Integer getDataobjectid() {
        return dataobjectid;
    }

    public void setDataobjectid(Integer dataobjectid) {
        this.dataobjectid = dataobjectid;
    }

    public int getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(int isdeleted) {
        this.isdeleted = isdeleted;
    }
}
