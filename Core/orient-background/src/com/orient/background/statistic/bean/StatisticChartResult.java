package com.orient.background.statistic.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/5 0005.
 */
public class StatisticChartResult<M, N> implements Serializable {

    //原始结果
    private List<Map<String, Object>> data = new ArrayList<>();

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    private String errorMsg = "";

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    //前处理结果:key 为图形id；value为图形数据信息
    private M preProcessResult;
    //后处理结果:key 为图形id；value为图形数据信息
    private N postProcessResult;

    public M getPreProcessResult() {
        return preProcessResult;
    }

    public void setPreProcessResult(M preProcessResult) {
        this.preProcessResult = preProcessResult;
    }

    public N getPostProcessResult() {
        return postProcessResult;
    }

    public void setPostProcessResult(N postProcessResult) {
        this.postProcessResult = postProcessResult;
    }
}
