package com.orient.download.bean.sparePartsBean;


public class DeviceInstBean {
    private String id="";
    private String name="";
    private String bianhao="";
    private String state="";
    private String location="";
    private String liezhuangTime="";
    //更新设备的时间
    private String latestUpdateTime="";
//    private String nextRotateTime="";
    //设备ID
    private String deviceId="";
    private String version="";
    //型号
    private String model="";

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

    public String getBianhao() {
        return bianhao;
    }

    public void setBianhao(String bianhao) {
        this.bianhao = bianhao;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLiezhuangTime() {
        return liezhuangTime;
    }

    public void setLiezhuangTime(String liezhuangTime) {
        this.liezhuangTime = liezhuangTime;
    }

//    public String getNextRotateTime() {
//        return nextRotateTime;
//    }
//
//    public void setNextRotateTime(String nextRotateTime) {
//        this.nextRotateTime = nextRotateTime;
//    }

    public String getLatestUpdateTime() {
        return latestUpdateTime;
    }

    public void setLatestUpdateTime(String latestUpdateTime) {
        this.latestUpdateTime = latestUpdateTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
