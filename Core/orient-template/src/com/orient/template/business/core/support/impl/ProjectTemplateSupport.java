package com.orient.template.business.core.support.impl;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.model.Directory;
import com.orient.collab.model.GanttPlanDependency;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.template.business.core.support.TemplateNodeImportHelper;
import com.orient.template.business.core.support.TemplateSupport;
import com.orient.template.model.CollabTeam;
import com.orient.template.model.CollabTemplateImportExtraInfo;
import com.orient.template.model.CollabTemplatePreviewNode;
import com.orient.template.model.GanttGraph;
import com.orient.utils.UtilFactory;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

import static com.orient.collab.config.CollabConstants.PROJECT;
import static com.orient.collab.config.CollabConstants.STATUS_UNSTARTED;

/**
 * represent a project template node
 *
 * @author Seraph
 *         2016-09-18 上午10:14
 */
@Component("projectTemplateSupport")
public class ProjectTemplateSupport implements TemplateSupport<Project>{

    @Override
    public boolean importNode(CollabTemplateNode currentNode, CollabTemplateNode dependentNode, TemplateNodeImportHelper importHelper) {
        Project project = (Project) currentNode.getData();
        project.setId(null);
        CollabTemplateImportExtraInfo extraInfo = importHelper.getTemplateImportExtraInfo();
        Directory dir = (Directory) extraInfo.getMountNode();
        project.setName(extraInfo.getNewRootNodeName());
        project.setParDirId(dir.getId());

        String newId = this.orientSqlEngine.getTypeMappingBmService().insert(project);
        currentNode.setNewDataId(newId);

        return true;
    }

    @Override
    public CollabTemplateNode exportNode(Project node, MutableBoolean dataSetted, List<Serializable> childrenData, List<Serializable> independentComponentsData, List<Serializable> relationComponentsData) {
        node.setStatus(STATUS_UNSTARTED);

        CollabTemplateNode templateNode = new CollabTemplateNode();
        templateNode.setData(node);
        templateNode.setType(node.getClass().getName());
        templateNode.setOldDataId(node.getId());

        List<Plan> subPlans = this.orientSqlEngine.getTypeMappingBmService().get(Plan.class,
                new CustomerFilter(Plan.PAR_PROJECT_ID, EnumInter.SqlOperation.Equal, node.getId()));

        childrenData.addAll(subPlans);

        GanttGraph ganttGraph = new GanttGraph();
        ganttGraph.setProjectId(node.getId());

        relationComponentsData.add(ganttGraph);

        CollabTeam collabTeam = new CollabTeam(PROJECT, node.getId());
        independentComponentsData.add(collabTeam);
        return templateNode;
    }

    @Override
    public CollabTemplatePreviewNode convertTemplateNodeToPreviewNode(CollabTemplateNode node, String previewType){
        CollabTemplatePreviewNode collabTemplatePreviewNode = TemplateSupport.super.convertTemplateNodeToPreviewNode(node, previewType);
        Project project = (Project) node.getData();
        collabTemplatePreviewNode.setText(project.getName());
        collabTemplatePreviewNode.setIconCls("icon-CB_PROJECT");

        return collabTemplatePreviewNode;
    }

    @Autowired
    protected ISqlEngine orientSqlEngine;
}
