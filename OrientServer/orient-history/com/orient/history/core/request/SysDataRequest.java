package com.orient.history.core.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/30 0030.
 */
public class SysDataRequest implements Serializable {

    //系统表名称
    private String sysTableName;

    //系统数据
    private List<Map<String, Object>> sysTableDataList = new ArrayList<>();

    private Map<String, Object> extraData = new HashMap<>();

    //可先传递过滤条件 后 初始化数据
    private String customFilterSql;

    public String getSysTableName() {
        return sysTableName;
    }

    public void setSysTableName(String sysTableName) {
        this.sysTableName = sysTableName;
    }

    public List<Map<String, Object>> getSysTableDataList() {
        return sysTableDataList;
    }

    public void setSysTableDataList(List<Map<String, Object>> sysTableDataList) {
        this.sysTableDataList = sysTableDataList;
    }

    public String getCustomFilterSql() {
        return customFilterSql;
    }

    public void setCustomFilterSql(String customFilterSql) {
        this.customFilterSql = customFilterSql;
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, Object> extraData) {
        this.extraData = extraData;
    }

    public SysDataRequest(String sysTableName, String customFilterSql) {
        this.sysTableName = sysTableName;
        this.customFilterSql = customFilterSql;
    }

    public SysDataRequest() {
    }
}
