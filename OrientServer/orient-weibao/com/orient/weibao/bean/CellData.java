package com.orient.weibao.bean;

import java.util.List;

/**
 * @author js_liuyangchao@163.com
 * @create 2019-03-16 11:13
 * @desc
 **/
public class CellData {

    private String msg;
    private String state;
    List<Fields> column;
    List<String[]> data;
    List<ViewData> newData;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Fields> getColumn() {
        return column;
    }

    public void setColumn(List<Fields> column) {
        this.column = column;
    }

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }

    public List<ViewData> getNewData() {
        return newData;
    }

    public void setNewData(List<ViewData> newData) {
        this.newData = newData;
    }
}
