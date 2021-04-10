package com.orient.history.core.engine.prepare.impl.plan;

import com.orient.collab.config.CollabConstants;
import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.engine.prepare.annotation.PrepareIntermediator;
import com.orient.history.core.engine.prepare.impl.AbstractPrepareIntermediator;
import com.orient.history.core.request.FrontViewRequest;
import com.orient.history.core.request.SysDataRequest;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.utils.CommonTools;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
@PrepareIntermediator(types = {"com.orient.history.core.util.HisTaskTypeConstants$$PLAN_TASK"}, order = 1)
@Scope(value = "prototype")
public class PreapareCommonPlanIntermediator extends AbstractPrepareIntermediator {

    @Autowired
    IFlowDataRelationService flowDataRelationService;

    @Override
    public void doPrepare(FrontViewRequest frontViewRequest) {
        super.doPrepare(frontViewRequest);
        String taskId = frontViewRequest.getTaskId();
        //增加控制流相关信息
        List<FlowDataRelation> flowDataRelationList = flowDataRelationService.list(Restrictions.and(Restrictions.eq("tableName", CollabConstants.PLAN), Restrictions.eq("dataId", taskId)), Order.desc("id"));
        if (!CommonTools.isEmptyList(flowDataRelationList)) {
            //保存控制流
            FlowDataRelation flowDataRelation = flowDataRelationList.get(0);
            String piId = flowDataRelation.getPiId();
            HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_CONTROLFLOWDATA, piId);
            //保存数据与流程关系
            SysDataRequest dataFlowRelationData = new SysDataRequest();
            dataFlowRelationData.setSysTableName(FlowDataRelation.class.getAnnotation(Table.class).name());
            dataFlowRelationData.setCustomFilterSql(" ID= " + flowDataRelation.getId().toString());
            //存至变量中
            List<SysDataRequest> commonSysDataRequest = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_SYSDATA);
            if (!CommonTools.isEmptyList(commonSysDataRequest)) {
                commonSysDataRequest.add(dataFlowRelationData);
            } else
                HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_SYSDATA, new ArrayList<SysDataRequest>() {{
                    add(dataFlowRelationData);
                }});
        }
    }
}
