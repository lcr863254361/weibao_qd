package com.orient.template.business.core.support.impl;

import com.orient.collab.model.*;
import com.orient.metamodel.metadomain.Restriction;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collab.CollabDataFlow;
import com.orient.sysmodel.domain.collab.CollabJobCarvePos;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.sysmodel.service.collab.impl.CollabDataFlowService;
import com.orient.sysmodel.service.collab.impl.CollabJobCarvePosService;
import com.orient.template.business.core.support.TemplateNodeImportHelper;
import com.orient.template.business.core.support.TemplateSupport;
import com.orient.template.model.CollabDataFlowDefinition;
import com.orient.template.model.CollabTemplatePreviewNode;
import com.orient.utils.UtilFactory;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * support for collab data flow's template operation
 *
 * @author Seraph
 *         2016-10-13 下午2:44
 */
@Component
public class CollabDataFlowDefinitionTemplateSupport implements TemplateSupport<CollabDataFlowDefinition> {

    @Override
    public boolean importNode(CollabTemplateNode<CollabDataFlowDefinition> currentNode, CollabTemplateNode dependentNode, TemplateNodeImportHelper importHelper) {
        CollabDataFlowDefinition collabDataFlowDefinition = currentNode.getData();

        CollabTemplateNode projectTemplateNode = currentNode.getRelationCompRef();
        Map<String, String> oldNewProjectTreeIdMap = importHelper.getOldNewIdMap(projectTemplateNode, false);

        List<CollabDataFlow> dataFlowTransitions = collabDataFlowDefinition.getDataFlowTransitions();
        if(dataFlowTransitions != null){
            dataFlowTransitions.forEach(collabDataFlow -> {
                String newSrcId = oldNewProjectTreeIdMap.get(Task.class.getName() + collabDataFlow.getSrcid());
                String newDestId = oldNewProjectTreeIdMap.get(Task.class.getName() + collabDataFlow.getDestnyid());
                collabDataFlow.setId(null);
                collabDataFlow.setSrcid(Long.valueOf(newSrcId));
                collabDataFlow.setDestnyid(Long.valueOf(newDestId));
                this.collabDataFlowService.save(collabDataFlow);
            });
        }

        List<CollabJobCarvePos> carvePoses = collabDataFlowDefinition.getCarvePoses();
        if(carvePoses != null){
            carvePoses.forEach(collabJobCarvePos -> {
                collabJobCarvePos.setId(null);
                String newDataId = oldNewProjectTreeIdMap.get(Task.class.getName() + collabJobCarvePos.getDataid());
                collabJobCarvePos.setDataid(Long.valueOf(newDataId));
                this.collabJobCarvePosService.save(collabJobCarvePos);
            });
        }
        return false;
    }

    @Override
    public void doExport(CollabTemplateNode<CollabDataFlowDefinition> templateNodeNode, MutableBoolean dataSetted, List<Serializable> childrenData, List<Serializable> independentComponentsData, List<Serializable> relationComponentsData) {
        CollabDataFlowDefinition collabDataFlowDefinition = templateNodeNode.getData();

        if(collabDataFlowDefinition.getChildTaskIds().size() == 0){
            return;
        }

        List<CollabDataFlow> dataFlowTransitions = this.collabDataFlowService.list(
                Restrictions.eq(CollabDataFlow.MODEL_ID, Long.valueOf(collabDataFlowDefinition.getModelId())),
                Restrictions.in(CollabDataFlow.DESTNY_ID, collabDataFlowDefinition.getChildTaskIds()),
                Restrictions.in(CollabDataFlow.SRC_ID, collabDataFlowDefinition.getChildTaskIds()));
        collabDataFlowDefinition.setDataFlowTransitions(dataFlowTransitions);

        List<CollabJobCarvePos> carvePoses = this.collabJobCarvePosService.list(
                Restrictions.eq(CollabJobCarvePos.MODEL_ID, Long.valueOf(collabDataFlowDefinition.getModelId())),
                Restrictions.in(CollabJobCarvePos.DATA_ID, collabDataFlowDefinition.getChildTaskIds()));
        templateNodeNode.getData().setCarvePoses(carvePoses);
    }

    @Override
    public CollabTemplatePreviewNode convertTemplateNodeToPreviewNode(CollabTemplateNode<CollabDataFlowDefinition> node, String previewType){
        CollabTemplatePreviewNode collabTemplatePreviewNode = new CollabTemplatePreviewNode(node, previewType);

        node.convertSerialBytesToData();
        CollabDataFlowDefinition collabDataFlowDefinition = node.getData();
        List<DataFlowActivity> dataFlowActivities = UtilFactory.newArrayList();

        Map<String, String> taskIdNameMap = UtilFactory.newHashMap();

        List<CollabTemplateNode> children = node.getRelationCompRef().getChildren();
        Optional.ofNullable(children).ifPresent(childrens -> {
            children.forEach(child ->{
                if(child.getType().equals(Task.class.getName())){
                    child.convertSerialBytesToData();
                    Task task = (Task) child.getData();
                    taskIdNameMap.put(task.getId(), task.getName());
                }
            });
        });

        Optional.ofNullable(collabDataFlowDefinition.getCarvePoses()).ifPresent(collabJobCarvePoses -> {
            collabJobCarvePoses.forEach(collabJobCarvePos -> {
                DataFlowActivity dataFlowActivity = new DataFlowActivity();
                dataFlowActivity.setId(String.valueOf(collabJobCarvePos.getDataid()));
                dataFlowActivity.setDispalyName(taskIdNameMap.get(String.valueOf(collabJobCarvePos.getDataid())));
                dataFlowActivity.setPos(collabJobCarvePos);
                dataFlowActivities.add(dataFlowActivity);
            });
        });

        collabTemplatePreviewNode.addExtraInfo("transitions", collabDataFlowDefinition.getDataFlowTransitions());
        collabTemplatePreviewNode.addExtraInfo("activities", dataFlowActivities);
        return collabTemplatePreviewNode;
    }

    @Autowired
    private CollabDataFlowService collabDataFlowService;
    @Autowired
    private CollabJobCarvePosService collabJobCarvePosService;
    @Autowired
    protected ISqlEngine orientSqlEngine;

}
