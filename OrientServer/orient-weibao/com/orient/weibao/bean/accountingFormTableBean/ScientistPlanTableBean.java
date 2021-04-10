package com.orient.weibao.bean.accountingFormTableBean;

import com.orient.utils.UtilFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ScientistPlanTableBean implements Serializable {
    private String fillPerson;
    private String homeSeaArea;
    private String planHomeJingdu;
    private String planHomeWeidu;
    private String mainTask;
    private String workProgress;
    private String attention;
    private String planDivingDepth;
    private String planFloatDepth;
    private String density;
    private String isSubmitTable;
    private String homeMapPathList;
//    private List<Map> showCarryToolList= UtilFactory.newArrayList();
    private String tableFlag;
    private String tableState;

    public String getPlanFloatDepth() {
        return planFloatDepth;
    }

    public void setPlanFloatDepth(String planFloatDepth) {
        this.planFloatDepth = planFloatDepth;
    }

    public String getTableState() {
        return tableState;
    }

    public void setTableState(String tableState) {
        this.tableState = tableState;
    }

    public String getTableFlag() {
        return tableFlag;
    }

    public void setTableFlag(String tableFlag) {
        this.tableFlag = tableFlag;
    }

    public String getFillPerson() {
        return fillPerson;
    }

    public void setFillPerson(String fillPerson) {
        this.fillPerson = fillPerson;
    }

    public String getHomeSeaArea() {
        return homeSeaArea;
    }

    public void setHomeSeaArea(String homeSeaArea) {
        this.homeSeaArea = homeSeaArea;
    }

    public String getPlanHomeJingdu() {
        return planHomeJingdu;
    }

    public void setPlanHomeJingdu(String planHomeJingdu) {
        this.planHomeJingdu = planHomeJingdu;
    }

    public String getPlanHomeWeidu() {
        return planHomeWeidu;
    }

    public void setPlanHomeWeidu(String planHomeWeidu) {
        this.planHomeWeidu = planHomeWeidu;
    }

    public String getMainTask() {
        return mainTask;
    }

    public void setMainTask(String mainTask) {
        this.mainTask = mainTask;
    }

    public String getWorkProgress() {
        return workProgress;
    }

    public void setWorkProgress(String workProgress) {
        this.workProgress = workProgress;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getPlanDivingDepth() {
        return planDivingDepth;
    }

    public void setPlanDivingDepth(String planDivingDepth) {
        this.planDivingDepth = planDivingDepth;
    }

    public String getIsSubmitTable() {
        return isSubmitTable;
    }

    public void setIsSubmitTable(String isSubmitTable) {
        this.isSubmitTable = isSubmitTable;
    }

    public String getHomeMapPathList() {
        return homeMapPathList;
    }

    public void setHomeMapPathList(String homeMapPathList) {
        this.homeMapPathList = homeMapPathList;
    }

//    public List<Map> getShowCarryToolList() {
//        return showCarryToolList;
//    }
//
//    public void setShowCarryToolList(List<Map> showCarryToolList) {
//        this.showCarryToolList = showCarryToolList;
//    }


    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }
}
