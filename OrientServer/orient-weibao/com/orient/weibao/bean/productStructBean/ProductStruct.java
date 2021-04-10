package com.orient.weibao.bean.productStructBean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ProductStruct implements Serializable {
    private String id;
    private String name;
    private String type;
    private Set<ProductStruct> structDeviceList= new HashSet<>();
    private Set<ProductStruct> partsList= new HashSet<>();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<ProductStruct> getStructDeviceList() {
        return structDeviceList;
    }

    public void setStructDeviceList(Set<ProductStruct> structDeviceList) {
        this.structDeviceList = structDeviceList;
    }

    public Set<ProductStruct> getPartsList() {
        return partsList;
    }

    public void setPartsList(Set<ProductStruct> partsList) {
        this.partsList = partsList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductStruct() {
    }


}
