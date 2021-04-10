package com.orient.download.bean.productStructEntity;

import com.orient.download.bean.currentTaskBean.CheckTempInstBean;
import com.orient.utils.UtilFactory;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class StructDeviceCycleCheckEntity implements Serializable {
    private String deviceId;
    private String id;
    private String name;
    //检查周期
    private String checkCycle;
    private LinkedList<CheckTempInstBean> checkTempInstBeanList = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CheckTempInstBean> getCheckTempInstBeanList() {
        return checkTempInstBeanList;
    }

    public void setCheckTempInstBeanList(LinkedList<CheckTempInstBean> checkTempInstBeanList) {
        this.checkTempInstBeanList = checkTempInstBeanList;
    }

    public String getCheckCycle() {
        return checkCycle;
    }

    public void setCheckCycle(String checkCycle) {
        this.checkCycle = checkCycle;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
