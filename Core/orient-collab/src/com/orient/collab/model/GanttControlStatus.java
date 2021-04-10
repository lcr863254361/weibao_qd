package com.orient.collab.model;

import com.orient.auditflow.model.AuditFlowInfo;

/**
 * used to control gantt panel's status
 *
 * @author Seraph
 *         2016-09-13 下午2:52
 */
public class GanttControlStatus {


    private AuditFlowInfo baselineSetAuditFlowInfo;
    private AuditFlowInfo baselineEditAuditFlowInfo;
    private boolean baselineSetAuditIsLastest;
    private boolean canSetBaseline;
    private boolean canEditBaseline;
    private boolean canStartProject = true;
    private boolean canSubmitProject = true;
    private String projectStatus;

    public boolean isBaselineSetAuditIsLastest() {
        return baselineSetAuditIsLastest;
    }

    public void setBaselineSetAuditIsLastest(boolean baselineSetAuditIsLastest) {
        this.baselineSetAuditIsLastest = baselineSetAuditIsLastest;
    }

    public AuditFlowInfo getBaselineSetAuditFlowInfo() {
        return baselineSetAuditFlowInfo;
    }

    public void setBaselineSetAuditFlowInfo(AuditFlowInfo baselineSetAuditFlowInfo) {
        this.baselineSetAuditFlowInfo = baselineSetAuditFlowInfo;
    }

    public AuditFlowInfo getBaselineEditAuditFlowInfo() {
        return baselineEditAuditFlowInfo;
    }

    public void setBaselineEditAuditFlowInfo(AuditFlowInfo baselineEditAuditFlowInfo) {
        this.baselineEditAuditFlowInfo = baselineEditAuditFlowInfo;
    }

    public boolean isCanSetBaseline() {
        return canSetBaseline;
    }

    public void setCanSetBaseline(boolean canSetBaseline) {
        this.canSetBaseline = canSetBaseline;
    }

    public boolean isCanEditBaseline() {
        return canEditBaseline;
    }

    public void setCanEditBaseline(boolean canEditBaseline) {
        this.canEditBaseline = canEditBaseline;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public boolean isCanStartProject() {
        return canStartProject;
    }

    public void setCanStartProject(boolean canStartProject) {
        this.canStartProject = canStartProject;
    }

    public boolean isCanSubmitProject() {
        return canSubmitProject;
    }

    public void setCanSubmitProject(boolean canSubmitProject) {
        this.canSubmitProject = canSubmitProject;
    }
}
