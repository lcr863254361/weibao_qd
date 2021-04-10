package com.orient.download.bean.sparePartsBean;

import java.util.List;

public class DeviceModel {
    private String id;
    private String name;
    private String model;
    private String deviceVersion;
    private List<DeviceInstBean> deviceCaseModels;

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

    public List<DeviceInstBean> getDeviceCaseModels() {
        return deviceCaseModels;
    }

    public void setDeviceCaseModels(List<DeviceInstBean> deviceCaseModels) {
        this.deviceCaseModels = deviceCaseModels;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }
}
