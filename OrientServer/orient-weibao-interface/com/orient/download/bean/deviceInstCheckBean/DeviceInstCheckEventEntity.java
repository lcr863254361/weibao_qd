package com.orient.download.bean.deviceInstCheckBean;

import com.orient.download.bean.uploadCheckInstBean.CheckListTableBean;
import com.orient.utils.UtilFactory;

import java.io.Serializable;
import java.util.List;

public class DeviceInstCheckEventEntity implements Serializable {
    private String checkEventName;
    private String checker;
    private String checkTime;
    private String passed;
    private List<CheckListTableBean> checkListTableBeanList = UtilFactory.newArrayList();

    public void setCheckEventName(String checkEventName) {
        this.checkEventName = checkEventName;
    }

    public String getCheckEventName() {
        return checkEventName;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getPassed() {
        return passed;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }

    public List<CheckListTableBean> getCheckListTableBeanList() {
        return checkListTableBeanList;
    }

    public void setCheckListTableBeanList(List<CheckListTableBean> checkListTableBeanList) {
        this.checkListTableBeanList = checkListTableBeanList;
    }
}
