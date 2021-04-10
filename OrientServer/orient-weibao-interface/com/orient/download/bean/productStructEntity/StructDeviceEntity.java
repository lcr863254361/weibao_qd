package com.orient.download.bean.productStructEntity;

import com.orient.utils.UtilFactory;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class StructDeviceEntity implements Serializable {
    private String id;
    private String name;
    private LinkedList<StructDeviceInstEntity> structDeviceInstEntityList= null;
    private LinkedList<StructDeviceCycleCheckEntity> structDeviceCycleCheckEntityList=null;
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

    public List<StructDeviceInstEntity> getStructDeviceInstEntityList() {
        return structDeviceInstEntityList;
    }

    public void setStructDeviceCycleCheckEntityList(LinkedList<StructDeviceCycleCheckEntity> structDeviceCycleCheckEntityList) {
        this.structDeviceCycleCheckEntityList = structDeviceCycleCheckEntityList;
    }

    public List<StructDeviceCycleCheckEntity> getStructDeviceCycleCheckEntityList() {
        return structDeviceCycleCheckEntityList;
    }

    public void setStructDeviceInstEntityList(LinkedList<StructDeviceInstEntity> structDeviceInstEntityList) {
        this.structDeviceInstEntityList = structDeviceInstEntityList;
    }

    public StructDeviceEntity() {

    }
}
