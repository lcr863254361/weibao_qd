package com.orient.download.bean.sparePartsBean;

import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-05-31 9:06
 */
public class DeviceBean {

    //设备ID
    private String id;
    //设备名称
    private String deviceName;
    private String version="";
    private List<ConsumeBean> consumeBeanList= UtilFactory.newArrayList();
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ConsumeBean> getConsumeBeanList() {
        return consumeBeanList;
    }

    public void setConsumeBeanList(List<ConsumeBean> consumeBeanList) {
        this.consumeBeanList = consumeBeanList;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
