package com.orient.history.core.binddata.model;

import com.orient.history.core.binddata.handler.IBindDataHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/26 0026.
 * 绑定系统数据描述
 */
public class BindSysData extends TaskBindData {

    public BindSysData() {
        super(IBindDataHandler.BIND_TYPE_SYSDATA);
    }

    //绑定的系统表名称
    private String sysTableName;

    //原始数据
    private List<Map<String, Object>> oriSysDataList = new ArrayList<>();

    public String getSysTableName() {
        return sysTableName;
    }

    public void setSysTableName(String sysTableName) {
        this.sysTableName = sysTableName;
    }

    public List<Map<String, Object>> getOriSysDataList() {
        return oriSysDataList;
    }

    public void setOriSysDataList(List<Map<String, Object>> oriSysDataList) {
        this.oriSysDataList = oriSysDataList;
    }
}
