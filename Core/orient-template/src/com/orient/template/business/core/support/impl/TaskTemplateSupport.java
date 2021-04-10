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
import com.orient.utils.UtilFactory;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static com.orient.collab.config.CollabConstants.PLAN;
import static com.orient.collab.config.CollabConstants.STATUS_UNSTARTED;
import static com.orient.collab.config.CollabConstants.TASK;

/**
 * represent a task template node
 *
 * @author Seraph
 *         2016-09-24 下午2:06
 */
@Component
public class TaskTemplateSupport implements TemplateSupport<Task> {
    @Override
    public boolean importNode(CollabTemplateNode currentNode, CollabTemplateNode dependentNode, TemplateNodeImportHelper importHelper) {

        Task task = (Task) currentNode.getData();
        task.setId(null);

        CollabTemplateNode parentNode = currentNode.getParent();

        if(parentNode == null){
            CollabTemplateImportExtraInfo extraInfo = importHelper.getTemplateImportExtraInfo();
            task.setName(extraInfo.getNewRootNodeName());
            if(extraInfo.getMountNode() instanceof Plan){
                task.setParPlanId(((Plan) extraInfo.getMountNode()).getId());
                task.setParTaskId(null);
            }else{
                task.setParTaskId(((Task) extraInfo.getMountNode()).getId());
                task.setParPlanId(null);
            }
        }else if(Plan.class.equals(parentNode.getData().getClass())){
            task.setParPlanId(parentNode.getNewDataId());
        }else if(Task.class.equals(parentNode.getData().getClass())){
            task.setParTaskId(parentNode.getNewDataId());
        }

        String newId = this.orientSqlEngine.getTypeMappingBmService().insert(task);
        currentNode.setNewDataId(newId);
        return true;
    }

    @Override
    public void doExport(CollabTemplateNode<Task> templateNode, MutableBoolean dataSetted, List<Serializable> childrenData, List<Serializable> independentComponentsData, List<Serializable> relationComponentsData) {
        Task node = templateNode.getData();
        node.setStatus(STATUS_UNSTARTED);

        templateNode.setOldDataId(node.getId());

        List<Task> subTasks = this.orientSqlEngine.getTypeMappingBmService().get(Task.class,
                new CustomerFilter(Task.PAR_TASK_ID, EnumInter.SqlOperation.Equal, node.getId()));

        childrenData.addAll(subTasks);

        String modelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);

        CollabTeam collabTeam = new CollabTeam(TASK, node.getId());
        independentComponentsData.add(collabTeam);

        CollabFlow collabFlow = new CollabFlow(TASK, node.getId());
        independentComponentsData.add(collabFlow);

        CollabDevData collabDevData = new CollabDevData(modelId, node.getId());
        independentComponentsData.add(collabDevData);

        CollabCheckData collabCheckData = new CollabCheckData(modelId, node.getId());
        independentComponentsData.add(collabCheckData);

        CollabComp collabComp = new CollabComp(modelId, node.getId());
        independentComponentsData.add(collabComp);

        CollabDataFlowDefinition collabDataFlowDefinition = new CollabDataFlowDefinition(modelId,
                subTasks.stream().map(subTask -> Long.valueOf(subTask.getId())).collect(Collectors.toList()));
        relationComponentsData.add(collabDataFlowDefinition);
    }

    @Override
    public CollabTemplatePreviewNode convertTemplateNodeToPreviewNode(CollabTemplateNode node, String previewType){
        CollabTemplatePreviewNode collabTemplatePreviewNode = TemplateSupport.super.convertTemplateNodeToPreviewNode(node, previewType);
        Task task = (Task) node.getData();
        collabTemplatePreviewNode.setText(task.getName());
        collabTemplatePreviewNode.setIconCls("icon-CB_TASK");

        List<Long> collabCompIds = UtilFactory.newArrayList();
        List<CollabTemplateNode> independentComponents = node.getIndependentComponents();
        independentComponents.forEach(independentComponent -> {
            if(CollabComp.class.getName().equals(independentComponent.getType())){
                independentComponent.convertSerialBytesToData();
                CollabComp collabComp = (CollabComp) independentComponent.getData();
                collabCompIds.addAll(collabComp.getComponentIds());
            }
        });
        collabTemplatePreviewNode.addExtraInfo("collabComp", collabCompIds);
        return collabTemplatePreviewNode;
    }

    @Autowired
    protected ISqlEngine orientSqlEngine;

}
