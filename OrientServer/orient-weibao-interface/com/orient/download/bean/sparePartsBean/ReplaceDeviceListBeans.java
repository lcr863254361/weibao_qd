package com.orient.download.bean.sparePartsBean;

import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-22 14:59
 */
public class ReplaceDeviceListBeans {
    private List<ReplaceDeviceBean> replaceDeviceBeanList= UtilFactory.newArrayList();
    private String recorderId="";
    private String taskId;

    public List<ReplaceDeviceBean> getReplaceDeviceBeanList() {
        return replaceDeviceBeanList;
    }

    public void setReplaceDeviceBeanList(List<ReplaceDeviceBean> replaceDeviceBeanList) {
        this.replaceDeviceBeanList = replaceDeviceBeanList;
    }

    public ReplaceDeviceListBeans(List<ReplaceDeviceBean> replaceDeviceBeanList) {
        this.replaceDeviceBeanList = replaceDeviceBeanList;
    }

    public ReplaceDeviceListBeans() {
    }

    public int getListSize(){
        return this.replaceDeviceBeanList.size();
    }

    public String getRecorderId() {
        return recorderId;
    }

    public void setRecorderId(String recorderId) {
        this.recorderId = recorderId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
