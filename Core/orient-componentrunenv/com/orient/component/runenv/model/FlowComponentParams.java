package com.orient.component.runenv.model;

import java.util.ArrayList;
import java.util.List;

public class FlowComponentParams {

    /**
     * 项目编号
     */
    private String projectCode;

    private List<ComponentParam> paramList = new ArrayList<ComponentParam>();

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public List<ComponentParam> getParamList() {
        return paramList;
    }

    public void setParamList(List<ComponentParam> paramList) {
        this.paramList = paramList;
    }


}
