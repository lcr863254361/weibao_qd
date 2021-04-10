package com.orient.collab.business.projectCore.flowListener;

import com.orient.auditflow.config.AuditFlowType;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.business.GanttBusiness;
import com.orient.collab.business.projectCore.cmd.CommandService;
import com.orient.collab.business.projectCore.cmd.concrete.BaselineEditCmd;
import com.orient.collab.model.Project;
import com.orient.flow.config.FlowType;
import com.orient.flow.extend.annotation.CommentInfo;
import com.orient.flow.extend.annotation.FlowTaskExecutionEventMarker;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.utils.CommonTools;
import com.orient.workflow.WorkFlowConstants;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-09-13 下午4:34
 */
@CommentInfo(displayName = "项目基线修改监听器", allowSelect = false)
@FlowTaskExecutionEventMarker(tasks = {FlowTaskExecutionEventListener.FLOW_END}, flowTypes = FlowType.Audit, flow = "com.orient.collab.config.CollabConstants$$BASELINE_CHANGE_AUDIT_NAME")
public class ProjectBaseLineEditAuditEndListener implements FlowTaskExecutionEventListener {

    @Override
    public void triggered(ExecutionImpl execution, TaskImpl task, TaskDefinitionImpl taskDefinition) {

        List<FlowDataRelation> flowDataRelations = flowDataRelationService.list(new Criterion[]{
                        Restrictions.eq(FlowDataRelation.PI_ID, execution.getProcessInstance().getId()),
                        Restrictions.eq(FlowDataRelation.MAIN_TYPE, AuditFlowType.WbsBaseLineEditAudit.toString())},
                Order.desc(FlowDataRelation.CREATE_TIME));

        if (!CommonTools.isEmptyList(flowDataRelations)) {
            FlowDataRelation flowDataRelation = flowDataRelations.get(0);
            if (WorkFlowConstants.STATE_END_ERROR.equals(execution.getState())) {
                return;
            }
            Project project = orientSqlEngine.getTypeMappingBmService().getById(Project.class, flowDataRelation.getDataId());
            try {
                this.commandService.execute(new BaselineEditCmd(project, true, orientSqlEngine, businessModelService));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void left(ExecutionImpl execution, String activityName, TaskDefinitionImpl taskDefinition, String signalName) {

    }


    @Autowired
    protected IBusinessModelService businessModelService;
    @Autowired
    private GanttBusiness ganttBusiness;
    @Autowired
    private ISqlEngine orientSqlEngine;
    @Autowired
    private IFlowDataRelationService flowDataRelationService;
    @Autowired
    private CommandService commandService;
}
