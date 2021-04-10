package com.orient.download.bean.productStructEntity;

import com.orient.utils.UtilFactory;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class StructSystemEntity implements Serializable {
    private String id;
    private String name;
    private String version;
    private LinkedList<StructDeviceEntity> structDeviceList= null;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<StructDeviceEntity> getStructDeviceList() {
        return structDeviceList;
    }

    public void setStructDeviceList(LinkedList<StructDeviceEntity> structDeviceList) {
        this.structDeviceList = structDeviceList;
    }

    public StructSystemEntity() {

    }
}
