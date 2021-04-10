package com.orient.download.bean.sparePartsBean;

import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-21 18:30
 */
public class DeviceInstListBeans {
    List<DeviceInstBean> deviceModelList= UtilFactory.newArrayList();

    public List<DeviceInstBean> getDeviceModelList() {
        return deviceModelList;
    }

    public void setDeviceModelList(List<DeviceInstBean> deviceModelList) {
        this.deviceModelList = deviceModelList;
    }

    public int getListSize(){
       return this.deviceModelList.size();
    }
}
