package com.orient.history.core.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/26 0026.
 * 前端保存历史请求，根据请求
 * 1.准备处理工厂
 * 2.准备工厂订单信息
 * 3.准备处理链
 * 4.处理订单
 * 5.返回处理结果
 */
public class FrontViewRequest implements Serializable {

    //任务ID
    private String taskId;

    //任务类型
    private String taskType;

    //需要序列化保存的模型及数据集合
    private List<ModelDataRequest> modelDataRequestList = new ArrayList<>();

    //需要序列化保存的系统表描述及数据集合
    private List<SysDataRequest> sysDataRequests = new ArrayList<>();

    //额外参数
    private Map<String, String> extraData = new HashMap<>();

    public Map<String, String> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, String> extraData) {
        this.extraData = extraData;
    }

    public List<ModelDataRequest> getModelDataRequestList() {
        return modelDataRequestList;
    }

    public void setModelDataRequestList(List<ModelDataRequest> modelDataRequestList) {
        this.modelDataRequestList = modelDataRequestList;
    }

    public List<SysDataRequest> getSysDataRequests() {
        return sysDataRequests;
    }

    public void setSysDataRequests(List<SysDataRequest> sysDataRequests) {
        this.sysDataRequests = sysDataRequests;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
