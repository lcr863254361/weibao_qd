package com.orient.pvm.bean.sync;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbin on 16/8/1.
 * Purpose:
 * Detail:
 */
public class CheckModel {

    public static String TYPE_HTML = "HTML";  //只包含HTML
    public static String TYPE_MODEL = "MODEL"; //只包含MODEL数据
    public static String TYPE_ALL = "ALL";     //包含HTML和MODEL
    private String modelId;
    private String modelName;
    private String taskId;
    private String taskModelName;
    private String taskPath;
    private String plan_Start;
    private String plan_End;
    private String modelStatus;
    private String projectName;
    private String remark;
    private String period;

    private String signfileids;                         //签署照片集合

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    private Team team;
    private Signers signers;
    private Columns columns;

    private Files files;

    public Files getFiles() {
        return files;
    }

    public void setFiles(Files files) {
        this.files = files;
    }

    private String modelType;   //检查表格是DS建立的模型还是HTML

    private List<Row> rows = new ArrayList<>();

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskModelName() {
        return taskModelName;
    }

    public void setTaskModelName(String taskModelName) {
        this.taskModelName = taskModelName;
    }

    public String getTaskPath() {
        return taskPath;
    }

    public void setTaskPath(String taskPath) {
        this.taskPath = taskPath;
    }

    public String getPlan_Start() {
        return plan_Start;
    }

    public void setPlan_Start(String plan_Start) {
        this.plan_Start = plan_Start;
    }

    public String getPlan_End() {
        return plan_End;
    }

    public void setPlan_End(String plan_End) {
        this.plan_End = plan_End;
    }

    public String getModelStatus() {
        return modelStatus;
    }

    public void setModelStatus(String modelStatus) {
        this.modelStatus = modelStatus;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectNam) {
        this.projectName = projectNam;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Signers getSigners() {
        return signers;
    }

    public void setSigners(Signers signers) {
        this.signers = signers;
    }

    public Columns getColumns() {
        return columns;
    }

    public void setColumns(Columns columns) {
        this.columns = columns;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSignfileids() {
        return signfileids;
    }

    public void setSignfileids(String signfileids) {
        this.signfileids = signfileids;
    }
}
