package com.orient.sysman.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/19 0019
 */
public class UserFilter implements Serializable {

    /**
     * key: in / not in
     * value : 1,2,3
     */
    private Map<String, String> idFilter;

    private Map<String, String> roleFilter;

    private Map<String, String> depFilter;

    private String extraSql;

    public Map<String, String> getIdFilter() {
        return idFilter;
    }

    public void setIdFilter(Map<String, String> idFilter) {
        this.idFilter = idFilter;
    }

    public Map<String, String> getRoleFilter() {
        return roleFilter;
    }

    public void setRoleFilter(Map<String, String> roleFilter) {
        this.roleFilter = roleFilter;
    }

    public Map<String, String> getDepFilter() {
        return depFilter;
    }

    public void setDepFilter(Map<String, String> depFilter) {
        this.depFilter = depFilter;
    }

    public String getExtraSql() {
        return extraSql;
    }

    public void setExtraSql(String extraSql) {
        this.extraSql = extraSql;
    }
}
