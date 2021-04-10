package com.orient.dsrestful.domain.share;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GNY on 2018/3/27
 */
public class ShareTableResult implements Serializable {

    private List<ShareTable> systemTableList;
    private List<ShareSchema> shareTableList;

    public List<ShareTable> getSystemTableList() {
        return systemTableList;
    }

    public void setSystemTableList(List<ShareTable> systemTableList) {
        this.systemTableList = systemTableList;
    }

    public List<ShareSchema> getShareTableList() {
        return shareTableList;
    }

    public void setShareTableList(List<ShareSchema> shareTableList) {
        this.shareTableList = shareTableList;
    }

}
