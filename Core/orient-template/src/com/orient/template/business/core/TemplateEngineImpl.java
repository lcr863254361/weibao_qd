package com.orient.template.business.core;

import com.orient.sysmodel.domain.template.CollabTemplate;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.sysmodel.service.template.impl.CollabTemplateNodeService;
import com.orient.template.business.core.support.TemplateNodeImportHelper;
import com.orient.template.business.core.support.TemplateSupport;
import com.orient.template.business.core.support.TemplateSupportRegistry;
import com.orient.template.model.CollabTemplateImportExtraInfo;
import com.orient.template.model.CollabTemplatePreviewNode;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * a default implementation of {@link TemplateEngine}
 *
 * @author Seraph
 *         2016-09-13 上午10:42
 */
@Component
public class TemplateEngineImpl implements TemplateEngine{

    @Override
    public void doImport(CollabTemplateNode rootNode, CollabTemplateImportExtraInfo extraInfo) {

        TemplateNodeImportHelper templateNodeHelper = new TemplateNodeImportHelper(extraInfo);
        String rootType = rootNode.getType();
        TemplateSupport templateSupport = templateSupportRegistry.getTemplateSupport(rootType);
        rootNode.convertSerialBytesToData();
        if(templateSupport.importNode(rootNode, null, templateNodeHelper)){
            doImportWithoutSelf(rootNode, templateNodeHelper);
        }
    }

    @Override
    public <T extends Serializable> CollabTemplateNode doExport(T node, CollabTemplate collabTemplate) {

        CollabTemplateNode templateNode = exportRecursively(node);
        templateNode.setRoot(true);
        templateNode.setTemplateId(String.valueOf(collabTemplate.getId()));
        this.collabTemplateNodeService.save(templateNode);
        return templateNode;
    }

    @Override
    public CollabTemplatePreviewNode convertTemplateNodeToPreviewNode(CollabTemplateNode node, String previewType) {
        TemplateSupport templateSupport = templateSupportRegistry.getTemplateSupport(node.getType());
        return templateSupport.convertTemplateNodeToPreviewNode(node, previewType);
    }

    private <T extends Serializable> CollabTemplateNode exportRecursively(T node){
        TemplateSupport templateSupport = templateSupportRegistry.getTemplateSupport(node.getClass().getName());

        MutableBoolean dataSetted = new MutableBoolean(false);
        List<Serializable> childrenData = new ArrayList<>();
        List<Serializable> independentComponentsData = new ArrayList<>();
        List<Serializable> relationComponentsData = new ArrayList<>();

        CollabTemplateNode curNode = templateSupport.exportNode(node, dataSetted, childrenData, independentComponentsData, relationComponentsData);
        if(!dataSetted.booleanValue()){

            for(Serializable independentComponentData : independentComponentsData){
                CollabTemplateNode independentTemplateNode = exportRecursively(independentComponentData);
                independentTemplateNode.setIndependentCompRef(curNode);
                curNode.getIndependentComponents().add(independentTemplateNode);
            }

            for(Serializable relationComponentData : relationComponentsData){
                CollabTemplateNode relationTemplateNode = exportRecursively(relationComponentData);
                relationTemplateNode.setRelationCompRef(curNode);
                curNode.getRelationComponents().add(relationTemplateNode);
            }

            for(Serializable child : childrenData){
                CollabTemplateNode childTemplateNode = exportRecursively(child);
                childTemplateNode.setParent(curNode);
                curNode.getChildren().add(childTemplateNode);
            }
        }

        curNode.convertDataToSerialBytes();
        return curNode;

    }

    private void doImportWithoutSelf(CollabTemplateNode rootNode, TemplateNodeImportHelper templateNodeHelper){
        List<CollabTemplateNode> nodes = rootNode.getIndependentComponents();

        Consumer<CollabTemplateNode> importNode = (templateNode) -> {
            TemplateSupport templateSupport = templateSupportRegistry.getTemplateSupport(templateNode.getType());
            templateNode.convertSerialBytesToData();
            if(templateSupport.importNode(templateNode, null, templateNodeHelper)){
                this.doImportWithoutSelf(templateNode, templateNodeHelper);
            }
        };

        nodes.forEach(importNode);
        nodes = rootNode.getChildren();
        nodes.forEach(importNode);

        //well, the subtree of this node has been imported already
        nodes = rootNode.getRelationComponents();
        for(CollabTemplateNode templateNode : nodes){
            templateNode.convertSerialBytesToData();
            TemplateSupport templateSupport = templateSupportRegistry.getTemplateSupport(templateNode.getType());
            if(templateSupport.importNode(templateNode, rootNode, templateNodeHelper)){
                this.doImportWithoutSelf(templateNode, templateNodeHelper);
            }
        }
    }

    @Autowired
    @Qualifier("templateSupportRegistry")
    private TemplateSupportRegistry templateSupportRegistry;
    @Autowired
    private CollabTemplateNodeService collabTemplateNodeService;
}
