package com.orient.history.core.engine.prepare.impl.collab;

import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.engine.prepare.annotation.PrepareIntermediator;
import com.orient.history.core.engine.prepare.impl.AbstractPrepareIntermediator;
import com.orient.history.core.request.FrontViewRequest;
import com.orient.history.core.request.SysDataRequest;
import com.orient.history.core.request.WorkFlowFrontViewRequest;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.utils.CommonTools;
import com.orient.workflow.service.ProcessInformationService;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
@PrepareIntermediator(types = {"com.orient.history.core.util.HisTaskTypeConstants$$COLLAB_TASK"}, order = 20)
@Scope(value = "prototype")
public class PreapareCollabBindIntermediator extends AbstractPrepareIntermediator {

    @Autowired
    ProcessInformationService processInformationService;

    @Autowired
    IFlowDataRelationService flowDataRelationService;

    @Override
    public void doPrepare(FrontViewRequest frontViewRequest) {
        super.doPrepare(frontViewRequest);
        String taskId = frontViewRequest.getTaskId();
        List<SysDataRequest> bindSysDataMaterials = new ArrayList<>();
        WorkFlowFrontViewRequest workFlowFrontViewRequest = (WorkFlowFrontViewRequest) frontViewRequest;
        //获取主流程ID
        String mainProcessInstaceId = processInformationService.getMainAndSubPIs(workFlowFrontViewRequest.getPiId()).get(0).getId();
        //获取任务设置信息
        List<FlowDataRelation> flowDataRelations = flowDataRelationService.list(Restrictions.eq("piId", mainProcessInstaceId));
        if (!CommonTools.isEmptyList(flowDataRelations)) {
            //获取任务绑定信息
            Table flowDataTable = FlowDataRelation.class.getAnnotation(Table.class);
            bindSysDataMaterials.add(new SysDataRequest(flowDataTable.name(), " PI_ID = '" + mainProcessInstaceId + "' "));
        }
        List<SysDataRequest> commonSysDataRequest = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_SYSDATA);
        if (!CommonTools.isEmptyList(commonSysDataRequest)) {
            commonSysDataRequest.addAll(bindSysDataMaterials);
        } else
            HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_SYSDATA, bindSysDataMaterials);
    }
}
