package com.orient.template.business.core.support.impl;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.model.Directory;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;
import com.orient.collab.model.Task;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.template.business.core.support.TemplateNodeImportHelper;
import com.orient.template.business.core.support.TemplateSupport;
import com.orient.template.model.*;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static com.orient.collab.config.CollabConstants.PLAN;
import static com.orient.collab.config.CollabConstants.PROJECT;
import static com.orient.collab.config.CollabConstants.STATUS_UNSTARTED;

/**
 * represent a plan template node
 *
 * @author Seraph
 *         2016-09-18 下午1:38
 */
@Component
public class PlanTemplateSupport implements TemplateSupport<Plan> {

    @Override
    public boolean importNode(CollabTemplateNode currentNode, CollabTemplateNode dependentNode, TemplateNodeImportHelper importHelper) {
        CollabTemplateNode parentNode = currentNode.getParent();

        Plan plan = (Plan) currentNode.getData();
        plan.setId(null);

        if(parentNode == null){
            CollabTemplateImportExtraInfo extraInfo = importHelper.getTemplateImportExtraInfo();
            plan.setName(extraInfo.getNewRootNodeName());
            if(extraInfo.getMountNode() instanceof Plan){
                plan.setParPlanId(((Plan) extraInfo.getMountNode()).getId());
                plan.setParProjectId(null);
            }else{
                plan.setParProjectId(((Project) extraInfo.getMountNode()).getId());
                plan.setParPlanId(null);
            }
        }else if(Project.class.getName().equals(parentNode.getType())){
            plan.setParProjectId(parentNode.getNewDataId());
        }else if(Plan.class.getName().equals(parentNode.getType())){
            plan.setParPlanId(parentNode.getNewDataId());
        }

        String newId = this.orientSqlEngine.getTypeMappingBmService().insert(plan);
        currentNode.setNewDataId(newId);
        return true;
    }

    @Override
    public void doExport(CollabTemplateNode<Plan> templateNode, MutableBoolean dataSetted, List<Serializable> childrenData, List<Serializable> independentComponentsData, List<Serializable> relationComponentsData) {
        Plan node = templateNode.getData();
        node.setStatus(STATUS_UNSTARTED);

        templateNode.setOldDataId(node.getId());

        List<Plan> subPlans = this.orientSqlEngine.getTypeMappingBmService().get(Plan.class,
                new CustomerFilter(Plan.PAR_PLAN_ID, EnumInter.SqlOperation.Equal, node.getId()));

        List<Task> subTasks = this.orientSqlEngine.getTypeMappingBmService().get(Task.class,
                new CustomerFilter(Task.PAR_PLAN_ID, EnumInter.SqlOperation.Equal, node.getId()));

        childrenData.addAll(subPlans);
        childrenData.addAll(subTasks);

        String planModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Plan.class);

        CollabTeam collabTeam = new CollabTeam(PLAN, node.getId());
        independentComponentsData.add(collabTeam);

        CollabFlow collabFlow = new CollabFlow(PLAN, node.getId());
        independentComponentsData.add(collabFlow);

        CollabDevData collabDevData = new CollabDevData(planModelId, node.getId());
        independentComponentsData.add(collabDevData);

        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        CollabDataFlowDefinition collabDataFlowDefinition = new CollabDataFlowDefinition(taskModelId,
                subTasks.stream().map(subTask -> Long.valueOf(subTask.getId())).collect(Collectors.toList()));
        relationComponentsData.add(collabDataFlowDefinition);
    }

    @Override
    public CollabTemplatePreviewNode convertTemplateNodeToPreviewNode(CollabTemplateNode node, String previewType){
        CollabTemplatePreviewNode collabTemplatePreviewNode = TemplateSupport.super.convertTemplateNodeToPreviewNode(node, previewType);
        Plan plan = (Plan) node.getData();
        collabTemplatePreviewNode.setText(plan.getName());
        collabTemplatePreviewNode.setIconCls("icon-CB_PLAN");

        return collabTemplatePreviewNode;
    }

    @Autowired
    protected ISqlEngine orientSqlEngine;
}
