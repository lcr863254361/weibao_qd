package com.orient.pvm.bean.sync;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbin on 16/8/1.
 * Purpose:
 * Detail:
 */
public class Check {

    private String headName , headCode, dispalyValue, type;

    private List<CheckOP> checkOps = new ArrayList<>();

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getHeadCode() {
        return headCode;
    }

    public void setHeadCode(String headCode) {
        this.headCode = headCode;
    }

    public String getDispalyValue() {
        return dispalyValue;
    }

    public void setDispalyValue(String dispalyValue) {
        this.dispalyValue = dispalyValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CheckOP> getCheckOps() {
        return checkOps;
    }

    public void setCheckOps(List<CheckOP> checkOps) {
        this.checkOps = checkOps;
    }
}
