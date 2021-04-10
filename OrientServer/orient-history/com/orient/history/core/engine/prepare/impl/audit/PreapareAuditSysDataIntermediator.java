package com.orient.history.core.engine.prepare.impl.audit;

import com.orient.background.business.AuditFlowModelBindBusiness;
import com.orient.flow.business.FlowTaskBusiness;
import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.engine.prepare.annotation.PrepareIntermediator;
import com.orient.history.core.engine.prepare.impl.AbstractPrepareIntermediator;
import com.orient.history.core.request.AuditFrontViewRequest;
import com.orient.history.core.request.FrontViewRequest;
import com.orient.history.core.request.SysDataRequest;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.sysmodel.domain.flow.AuditFlowModelBindEntity;
import com.orient.sysmodel.domain.flow.AuditFlowTaskSettingEntity;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.utils.CommonTools;
import com.orient.workflow.WorkFlowConstants;
import com.orient.workflow.service.ProcessInformationService;
import org.hibernate.criterion.Restrictions;
import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
@PrepareIntermediator(types = {"com.orient.history.core.util.HisTaskTypeConstants$$AUDIT_TASK"}, order = 10)
@Scope(value = "prototype")
public class PreapareAuditSysDataIntermediator extends AbstractPrepareIntermediator {

    @Autowired
    FlowTaskBusiness flowTaskBusiness;

    @Autowired
    ProcessInformationService processInformationService;

    @Autowired
    IFlowDataRelationService flowDataRelationService;

    @Autowired
    AuditFlowModelBindBusiness auditFlowModelBindBusiness;

    @Override
    public void doPrepare(FrontViewRequest frontViewRequest) {
        super.doPrepare(frontViewRequest);
        //强转为父类
        AuditFrontViewRequest auditFrontViewRequest = (AuditFrontViewRequest) frontViewRequest;
        String taskId = auditFrontViewRequest.getTaskId();
        HistoryTaskImpl task = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_DESC_KEY);
        String taskName = flowTaskBusiness.getHistoryActivityName(task.getId());
        List<SysDataRequest> bindSysDataMaterials = new ArrayList<>();
        //获取任务绑定信息
        String processInstanceId = auditFrontViewRequest.getPiId();
        //获取主流程ID
        String mainProcessInstaceId = processInformationService.getMainAndSubPIs(processInstanceId).get(0).getId();
        //获取任务设置信息
        List<FlowDataRelation> flowDataRelations = flowDataRelationService.list(Restrictions.eq("piId", mainProcessInstaceId));
        if (!CommonTools.isEmptyList(flowDataRelations)) {
            //取第一条
            FlowDataRelation flowDataRelation = flowDataRelations.get(0);
            //获取任务绑定信息
            Table flowDataTable = FlowDataRelation.class.getAnnotation(Table.class);
            bindSysDataMaterials.add(new SysDataRequest(flowDataTable.name(), " PI_ID = '" + mainProcessInstaceId + "' "));
            //获取模型与流程定义绑定信息
            String pdId = processInformationService.getProcessDefinitionIdByHisPiId(mainProcessInstaceId);
            List<AuditFlowModelBindEntity> auditFlowModelBindEntities = auditFlowModelBindBusiness.getModelBindPds(flowDataRelation.getTableName());
            final AuditFlowModelBindEntity[] auditFlowModelBindEntity = {null};
            auditFlowModelBindEntities.forEach(tmpModelBind -> {
                if (pdId.equals(tmpModelBind.getFlowName() + WorkFlowConstants.PROCESS_NAME_VERSION_CONNECTER + tmpModelBind.getFlowVersion())) {
                    auditFlowModelBindEntity[0] = tmpModelBind;
                }
            });
            if (null != auditFlowModelBindEntity[0]) {
                //获取任务设置信息
                Table flowSettingTable = AuditFlowTaskSettingEntity.class.getAnnotation(Table.class);
                bindSysDataMaterials.add(new SysDataRequest(flowSettingTable.name(), " BELONG_AUDIT_BIND = " + auditFlowModelBindEntity[0].getId() + " AND TASK_NAME = '" + taskName + "' "));
            }
        }
        List<SysDataRequest> commonSysDataRequest = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_SYSDATA);
        if (!CommonTools.isEmptyList(commonSysDataRequest)) {
            commonSysDataRequest.addAll(bindSysDataMaterials);
        } else
            HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_SYSDATA, bindSysDataMaterials);
    }
}
