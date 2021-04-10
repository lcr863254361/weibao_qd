package com.orient.collab.business.projectCore.cmd.concrete;

import com.orient.collab.business.projectCore.cmd.Command;
import com.orient.collab.model.Plan;
import com.orient.collab.model.StatefulModel;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.business.FlowExecutionBusiness;
import com.orient.sysmodel.domain.collab.CollabFlowRelation;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.service.collab.ICollabFlowRelationService;
import com.orient.sysmodel.service.collab.impl.CollabFlowRelationService;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.utils.UtilFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.ProcessInstance;

import java.util.List;

import static com.orient.collab.config.CollabConstants.PLAN;
import static com.orient.collab.config.CollabConstants.TASK;

/**
 * CloseCollabFlowCmd
 *
 * @author Seraph
 *         2016-08-16 下午3:03
 */
public class CloseCollabFlowCmd implements Command<Boolean> {

    public CloseCollabFlowCmd(StatefulModel sm, ProcessInstance processInstance) {
        this.sm = sm;
        this.processInstance = processInstance;
    }

    @Override
    public Boolean execute() throws Exception {
        ICollabFlowRelationService collabFlowRelationService = OrientContextLoaderListener.Appwac.getBean(ICollabFlowRelationService.class);
        List<CollabFlowRelation> collabFlowRelations = collabFlowRelationService.getSubCollabFlowDataRelationsCascade(processInstance.getId());

        FlowExecutionBusiness flowExecutionBusiness = OrientContextLoaderListener.Appwac.getBean("flowExecutionBusiness", FlowExecutionBusiness.class);

        List<String> toDeleteFlowRelationIds = UtilFactory.newArrayList();
        for(CollabFlowRelation collabFlowRelation : collabFlowRelations){
            flowExecutionBusiness.endProcessInstance(collabFlowRelation.getSubPiId());
            toDeleteFlowRelationIds.add(String.valueOf(collabFlowRelation.getId()));
        }

        if(toDeleteFlowRelationIds.size()>0){
            collabFlowRelationService.delete(toDeleteFlowRelationIds.toArray(new String[toDeleteFlowRelationIds.size()]));
        }
        flowExecutionBusiness.endProcessInstance(processInstance.getId());
        return true;
    }

    private StatefulModel sm;
    private ProcessInstance processInstance;
}
