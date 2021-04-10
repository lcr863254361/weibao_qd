package com.orient.pvm.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by mengbin on 16/7/30.
 * Purpose:
 * Detail:
 */
public class CheckTemplateInfo implements Serializable {

    /**
     * 多行的数据,其中map中的key为 columnName
     */
    private List<Map<String, String>> datas;
    private String signroles;
    private String modelDisplayName;
    private String remark;

    public String getModelDisplayName() {
        return modelDisplayName;
    }

    public void setModelDisplayName(String modelDisplayName) {
        this.modelDisplayName = modelDisplayName;
    }

    public List<Map<String, String>> getDatas() {
        return datas;
    }

    public void setDatas(List<Map<String, String>> datas) {
        this.datas = datas;
    }

    public String getSignroles() {
        return signroles;
    }

    public void setSignroles(String signroles) {
        this.signroles = signroles;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
