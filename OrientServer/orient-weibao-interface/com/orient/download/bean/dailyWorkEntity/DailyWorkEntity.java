package com.orient.download.bean.dailyWorkEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DailyWorkEntity implements Serializable {
    private String dailyWorkId;
    private String workDate;
    private String workContent;
    private String workType;
    //参与人员存李航洲-id,
    private String personnel;
    private String belongSystem;
    private String belongDeviceIns;
    private String belongDivingTask;
    //终端UUID编码
    private String padUUID;

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getDailyWorkId() {
        return dailyWorkId;
    }

    public void setDailyWorkId(String dailyWorkId) {
        this.dailyWorkId = dailyWorkId;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }

    public String getBelongSystem() {
        return belongSystem;
    }

    public void setBelongSystem(String belongSystem) {
        this.belongSystem = belongSystem;
    }

    public String getBelongDeviceIns() {
        return belongDeviceIns;
    }

    public void setBelongDeviceIns(String belongDeviceIns) {
        this.belongDeviceIns = belongDeviceIns;
    }

    public String getBelongDivingTask() {
        return belongDivingTask;
    }

    public void setBelongDivingTask(String belongDivingTask) {
        this.belongDivingTask = belongDivingTask;
    }

    public String getPadUUID() {
        return padUUID;
    }

    public void setPadUUID(String padUUID) {
        this.padUUID = padUUID;
    }
}
