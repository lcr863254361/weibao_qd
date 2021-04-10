package com.orient.history.core.binddata.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class TaskBindData implements Serializable {

    //绑定的数据类型
    private String taskBindType;

    private String className = this.getClass().getName();

    //自定义参数
    private Map<String, Object> extraData = new HashMap<>();

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTaskBindType() {
        return taskBindType;
    }

    public void setTaskBindType(String taskBindType) {
        this.taskBindType = taskBindType;
    }

    public TaskBindData(String taskBindType) {
        this.taskBindType = taskBindType;
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, Object> extraData) {
        this.extraData = extraData;
    }
}
