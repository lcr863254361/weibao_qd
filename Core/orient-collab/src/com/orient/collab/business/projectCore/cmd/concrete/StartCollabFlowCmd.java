package com.orient.collab.business.projectCore.cmd.concrete;

import com.orient.collab.business.projectCore.cmd.Command;
import com.orient.collab.model.Plan;
import com.orient.collab.model.StatefulModel;
import com.orient.collab.model.Task;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.business.FlowExecutionBusiness;
import com.orient.flow.config.FlowType;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collab.CollabFlowRelation;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.service.collab.ICollabFlowRelationService;
import com.orient.sysmodel.service.collab.impl.CollabFlowRelationService;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.ProcessInstance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.orient.collab.config.CollabConstants.PLAN;
import static com.orient.collab.config.CollabConstants.TASK;
import static com.orient.workflow.bean.JBPMInfo.COLLAB_TASK_ID;
import static com.orient.workflow.bean.JBPMInfo.FLOW_DATA_RELATION;

/**
 * start collab flow
 *
 * @author Seraph
 *         2016-08-10 下午2:59
 */
public class StartCollabFlowCmd implements Command<ProcessInstance> {

    public StartCollabFlowCmd(StatefulModel sm, String pdId){
        this.sm = sm;
        this.pdId = pdId;
    }

    @Override
    public ProcessInstance execute() throws Exception {

        String modelName = (sm instanceof Task) ? TASK : PLAN;
        FlowDataRelation flowDataRelation = new FlowDataRelation();
        flowDataRelation.setTableName(modelName);
        flowDataRelation.setDataId(sm.getId());
        flowDataRelation.setCreateTime(new Date());
        flowDataRelation.setMainType(FlowType.Collab.toString());

        List<FlowDataRelation> flowDataRelations = UtilFactory.newArrayList();
        flowDataRelations.add(flowDataRelation);

        Map<String, Object> flowVariables = UtilFactory.newHashMap();
        flowVariables.put(FLOW_DATA_RELATION, flowDataRelations);

        FlowExecutionBusiness flowExecutionBusiness = OrientContextLoaderListener.Appwac.getBean("flowExecutionBusiness", FlowExecutionBusiness.class);
        ProcessInstance processInstance = flowExecutionBusiness.startPrcInstanceByPrcDefId(pdId, null, null, flowVariables);

        if(TASK.equals(modelName)){
            Task task = (Task) sm;
            String parModel = PLAN;
            String parDataId = task.getParPlanId();
            if(CommonTools.isNullString(parDataId)){
                parModel = TASK;
                parDataId = task.getParTaskId();
            }
            IFlowDataRelationService flowDataRelationService = OrientContextLoaderListener.Appwac.getBean(IFlowDataRelationService.class);
            FlowDataRelation parFlowDataRelation = flowDataRelationService.list(new Criterion[]{
                    Restrictions.eq(FlowDataRelation.MAIN_TYPE, FlowType.Collab.toString()),
                    Restrictions.eq(FlowDataRelation.TABLE_NAME, parModel),
                    Restrictions.eq(FlowDataRelation.DATA_ID, parDataId)},
                    Order.desc(FlowDataRelation.CREATE_TIME)).get(0);
            String parPiId = parFlowDataRelation.getPiId();

            CollabFlowRelation collabFlowRelation = new CollabFlowRelation(parPiId, processInstance.getId(), sm.getId());
            ICollabFlowRelationService collabFlowRelationService = OrientContextLoaderListener.Appwac.getBean(ICollabFlowRelationService.class);
            collabFlowRelationService.save(collabFlowRelation);
        }

        return processInstance;
    }

    private StatefulModel sm;
    private String pdId;
}
