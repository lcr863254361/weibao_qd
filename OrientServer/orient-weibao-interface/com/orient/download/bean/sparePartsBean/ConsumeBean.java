package com.orient.download.bean.sparePartsBean;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-05-31 9:29
 */
public class ConsumeBean {
    //耗材ID
    private String id;
    //耗材名称
    private String name="";
    //耗材剩余个数、上传数据的时候是消耗个数
    private int number;
    //耗材型号
    private String model="";
    //版本号，终端同步时需根据版本号判断数据是否相同，相同则不插入到数据库中
    private String version="";

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
