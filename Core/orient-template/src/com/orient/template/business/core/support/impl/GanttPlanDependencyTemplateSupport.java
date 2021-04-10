package com.orient.template.business.core.support.impl;

import com.orient.collab.model.GanttPlanDependency;
import com.orient.collab.model.Plan;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.template.business.core.support.TemplateNodeImportHelper;
import com.orient.template.business.core.support.TemplateSupport;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-09-18 下午1:43
 */
@Component
public class GanttPlanDependencyTemplateSupport implements TemplateSupport<GanttPlanDependency> {

    @Override
    public boolean importNode(CollabTemplateNode currentNode, CollabTemplateNode dependentNode, TemplateNodeImportHelper importHelper) {

        GanttPlanDependency ganttPlanDependency = (GanttPlanDependency) currentNode.getData();

        CollabTemplateNode projectTemplateNode = currentNode.getParent().getRelationCompRef();
        Map<String, String> oldNewPlanIdMap = importHelper.getOldNewIdMap(projectTemplateNode, false);
        ganttPlanDependency.setId(null);
        //.setBlngProjectId(projectTemplateNode.getNewDataId());
        ganttPlanDependency.setStartPlanId(oldNewPlanIdMap.get(Plan.class.getName() + ganttPlanDependency.getStartPlanId()));
        ganttPlanDependency.setFinishPlanId(oldNewPlanIdMap.get(Plan.class.getName() + ganttPlanDependency.getFinishPlanId()));
        this.orientSqlEngine.getTypeMappingBmService().insert(ganttPlanDependency);
        return false;
    }

    @Override
    public CollabTemplateNode exportNode(GanttPlanDependency node, MutableBoolean dataSetted, List<Serializable> childrenData, List<Serializable> independentComponentsData, List<Serializable> relationComponentsData) {
        CollabTemplateNode templateNode = new CollabTemplateNode();
        templateNode.setData(node);
        templateNode.setType(node.getClass().getName());
        templateNode.setOldDataId(node.getId());

        return templateNode;
    }

    @Autowired
    protected ISqlEngine orientSqlEngine;
}
