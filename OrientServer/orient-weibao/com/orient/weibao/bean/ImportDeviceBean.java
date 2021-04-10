package com.orient.weibao.bean;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-06-15 15:51
 */
public class ImportDeviceBean {
    private String name="";
    private String model="";
    private String note="";
    private String position="";
    private Date wareHouseDate;
    private int number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getWareHouseDate() {
        return wareHouseDate;
    }

    public void setWareHouseDate(Date wareHouseDate) {
        this.wareHouseDate = wareHouseDate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
