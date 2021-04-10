package com.orient.weibao.bean;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-18 19:56
 */
public class DeviceInstBean {

    private String id;
    private String deviceName;
    private String number;
    private String state;
    private String liezhuangTime;
    private String position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLiezhuangTime() {
        return liezhuangTime;
    }

    public void setLiezhuangTime(String liezhuangTime) {
        this.liezhuangTime = liezhuangTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
