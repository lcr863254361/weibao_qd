package com.orient.weibao.dto;

import com.orient.weibao.mbg.model.CarryTool;

public class CarryToolWithParams extends CarryTool {
    private String deviceName;
    private String cWidth3209;
    private String cLength3209;
    private String taskName;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getcWidth3209() {
        return cWidth3209;
    }

    public void setcWidth3209(String cWidth3209) {
        this.cWidth3209 = cWidth3209;
    }

    public String getcLength3209() {
        return cLength3209;
    }

    public void setcLength3209(String cLength3209) {
        this.cLength3209 = cLength3209;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
