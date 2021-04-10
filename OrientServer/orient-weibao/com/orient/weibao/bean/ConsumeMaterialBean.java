package com.orient.weibao.bean;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-05-18 14:28
 */
public class ConsumeMaterialBean {
    private String name="";
    private String model="";//型号
    private String number="";//数量
    private String position="";//存放位置
    private String boxNumber=""; //箱号
    private Date wareHousingDate; //入库时间
    private String note="";   //备注
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public Date getWareHousingDate() {
        return wareHousingDate;
    }

    public void setWareHousingDate(Date wareHousingDate) {
        this.wareHousingDate = wareHousingDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
