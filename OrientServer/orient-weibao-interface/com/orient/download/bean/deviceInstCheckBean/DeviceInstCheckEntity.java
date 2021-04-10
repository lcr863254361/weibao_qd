package com.orient.download.bean.deviceInstCheckBean;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class DeviceInstCheckEntity implements Serializable {
    private String systemId;
    private String deviceInstId;
    private LinkedList<DeviceInstCheckEventEntity> deviceInstCheckEventEntities=new LinkedList<>();

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setDeviceInstId(String deviceInstId) {
        this.deviceInstId = deviceInstId;
    }

    public String getDeviceInstId() {
        return deviceInstId;
    }

    public void setDeviceInstCheckEventEntities(LinkedList<DeviceInstCheckEventEntity> deviceInstCheckEventEntities) {
        this.deviceInstCheckEventEntities = deviceInstCheckEventEntities;
    }

    public LinkedList<DeviceInstCheckEventEntity> getDeviceInstCheckEventEntities() {
        return deviceInstCheckEventEntities;
    }
}
