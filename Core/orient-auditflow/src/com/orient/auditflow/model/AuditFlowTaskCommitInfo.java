package com.orient.auditflow.model;

import com.orient.sysmodel.domain.flow.AuditFlowOpinionEntity;
import com.orient.workflow.bean.AssignUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * information necessary to commit an audit flow task
 *
 * @author Seraph
 *         2016-08-03 下午3:17
 */
public class AuditFlowTaskCommitInfo {

    /**
     * 流程任务id
     */
    private String flowTaskId;

    /**
     * 后续任务的执行人,如果后续为fork分支节点,可指定分支后续的任务负责人
     */
    private Map<String, AssignUser> nextTasksUserAssign;

    /**
     * 流向名称
     */
    private String transitionName;

    /**
     * 审批意见
     */
    private List<AuditFlowOpinionEntity> opinions = new ArrayList<>();

    public List<AuditFlowOpinionEntity> getOpinions() {
        return opinions;
    }

    public void setOpinions(List<AuditFlowOpinionEntity> opinions) {
        this.opinions = opinions;
    }

    public String getFlowTaskId() {
        return flowTaskId;
    }

    public void setFlowTaskId(String flowTaskId) {
        this.flowTaskId = flowTaskId;
    }

    public String getTransitionName() {
        return transitionName;
    }

    public void setTransitionName(String transitionName) {
        this.transitionName = transitionName;
    }

    public Map<String, AssignUser> getNextTasksUserAssign() {
        return nextTasksUserAssign;
    }

    public void setNextTasksUserAssign(Map<String, AssignUser> nextTasksUserAssign) {
        this.nextTasksUserAssign = nextTasksUserAssign;
    }
}
