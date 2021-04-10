package com.orient.template.business.core.support.impl;

import com.orient.collab.model.Task;
import com.orient.component.ComponentInterface;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.component.CwmComponentEntity;
import com.orient.sysmodel.domain.component.CwmComponentModelEntity;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.sysmodel.service.component.IComponentBindService;
import com.orient.sysmodel.service.component.IComponentService;
import com.orient.template.business.core.support.TemplateNodeImportHelper;
import com.orient.template.business.core.support.TemplateSupport;
import com.orient.template.model.CollabComp;
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
 * template support for collab component
 *
 * @author Seraph
 *         2016-10-19 上午10:03
 */
@Component
public class CollabCompTemplateSupport  implements TemplateSupport<CollabComp> {

    @Override
    public boolean importNode(CollabTemplateNode<CollabComp> currentNode, CollabTemplateNode dependentNode, TemplateNodeImportHelper importHelper) {
        String newTaskDataId = currentNode.getIndependentCompRef().getNewDataId();

        CollabComp collabComp = currentNode.getData();
        collabComp.getComponentIds().forEach(
            compId -> {
                CwmComponentModelEntity cwmComponentModelEntity = new CwmComponentModelEntity();
                /*cwmComponentModelEntity.setModelId(collabComp.getModelId());
                cwmComponentModelEntity.setDataId(newTaskDataId);*/
                cwmComponentModelEntity.setBelongComponent(componentService.getById(compId));
                this.componentBindService.save(cwmComponentModelEntity);
            }
        );

        return false;
    }

    @Override
    public void doExport(CollabTemplateNode<CollabComp> templateNode, MutableBoolean dataSetted, List<Serializable> childrenData, List<Serializable> independentComponentsData, List<Serializable> relationComponentsData) {
        CollabComp collabComp = templateNode.getData();
        List<CwmComponentModelEntity> cwmComponentModelEntitys = this.componentBindService.list(
                Restrictions.eq("modelId", collabComp.getModelId()), Restrictions.eq("dataId", collabComp.getDataId()));

        Optional.ofNullable(cwmComponentModelEntitys).ifPresent(cwmComponentModelEntities -> {
            cwmComponentModelEntities.forEach(cwmComponentModelEntity -> collabComp.getComponentIds().add(cwmComponentModelEntity.getBelongComponent().getId()));
        });

    }

    @Override
    public CollabTemplatePreviewNode convertTemplateNodeToPreviewNode(CollabTemplateNode<CollabComp> node, String previewType) {
        CollabTemplatePreviewNode collabTemplatePreviewNode = new CollabTemplatePreviewNode(node, previewType);
        node.convertSerialBytesToData();

        CollabComp collabComp = node.getData();

        List<Long> componentIds = collabComp.getComponentIds();
        if(componentIds.size() > 0){
            CwmComponentEntity cwmComponentEntity = this.componentService.getById(componentIds.get(0));
            String className = cwmComponentEntity.getClassname();
            ComponentInterface componentInterface = (ComponentInterface) OrientContextLoaderListener.Appwac.getBean(className);
            String jsCLass = componentInterface.getDashBordExtClass();

            Map<String, Object> componentBind = UtilFactory.newHashMap();
            componentBind.put("belongComponent", cwmComponentEntity);
            componentBind.put("componentExtJsClass", jsCLass);
            String taskModelId = this.sqlEngine.getTypeMappingBmService().getModelId(Task.class);
            componentBind.put("taskModelId", taskModelId);
            collabTemplatePreviewNode.addExtraInfo("componentBind", componentBind);
        }
        return collabTemplatePreviewNode;
    }

    @Autowired
    private IComponentBindService componentBindService;

    @Autowired
    private IComponentService componentService;

    @Autowired
    private ISqlEngine sqlEngine;

}
