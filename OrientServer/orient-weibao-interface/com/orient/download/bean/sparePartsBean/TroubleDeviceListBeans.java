package com.orient.download.bean.sparePartsBean;

import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-22 16:25
 */
public class TroubleDeviceListBeans {

    private List<TroubleDeviceBean> troubleDeviceBeanList= UtilFactory.newArrayList();

    private String taskId="";
    private String recorderId="";

    public List<TroubleDeviceBean> getTroubleDeviceBeanList() {
        return troubleDeviceBeanList;
    }

    public void setTroubleDeviceBeanList(List<TroubleDeviceBean> troubleDeviceBeanList) {
        this.troubleDeviceBeanList = troubleDeviceBeanList;
    }

    public TroubleDeviceListBeans() {
    }

    public TroubleDeviceListBeans(List<TroubleDeviceBean> troubleDeviceBeanList) {
        this.troubleDeviceBeanList = troubleDeviceBeanList;
    }

    public int getListSize(){
        return this.troubleDeviceBeanList.size();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRecorderId() {
        return recorderId;
    }

    public void setRecorderId(String recorderId) {
        this.recorderId = recorderId;
    }
}
