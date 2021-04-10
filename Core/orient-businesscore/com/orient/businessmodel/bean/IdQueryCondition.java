package com.orient.businessmodel.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * query condition info before do query action
 *
 * @author panduanduan
 * @create 2017-02-28 3:19 PM
 */
public class IdQueryCondition implements Serializable{

    private String sql;

    private List<String> params = new ArrayList<>();

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public IdQueryCondition(){

    }

    public IdQueryCondition(String sql, List<String> params) {
        this.sql = sql;
        this.params = params;
    }
}
