package com.orient.template.business.core.support.impl;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.model.*;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.domain.user.UserDAO;
import com.orient.template.business.core.support.TemplateNodeImportHelper;
import com.orient.template.business.core.support.TemplateSupport;
import com.orient.template.model.CollabTemplatePreviewNode;
import com.orient.template.model.GanttGraph;
import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * support for gantt graph's template operation
 *
 * @author Seraph
 *         2016-09-28 下午2:15
 */
@Component
public class GanttGraphTemplateSupport implements TemplateSupport<GanttGraph> {

    @Override
    public boolean importNode(CollabTemplateNode currentNode, CollabTemplateNode dependentNode, TemplateNodeImportHelper importHelper) {
        return true;
    }

    @Override
    public CollabTemplateNode exportNode(GanttGraph node, MutableBoolean dataSetted, List<Serializable> childrenData, List<Serializable> independentComponentsData, List<Serializable> relationComponentsData) {
        CollabTemplateNode templateNode = new CollabTemplateNode();
        templateNode.setData(node);
        templateNode.setType(node.getClass().getName());

        List<GanttPlanDependency> dependencies = this.orientSqlEngine.getTypeMappingBmService().get(GanttPlanDependency.class,
                new CustomerFilter(GanttPlanDependency.BELONGED_PROJECT_ID, EnumInter.SqlOperation.Equal, node.getProjectId()));

        childrenData.addAll(dependencies);
        return templateNode;
    }

    @Override
    public CollabTemplatePreviewNode convertTemplateNodeToPreviewNode(CollabTemplateNode node, String previewType){
        CollabTemplatePreviewNode collabTemplatePreviewNode = new CollabTemplatePreviewNode(node, previewType);

        CollabTemplateNode projectNode = node.getRelationCompRef();
        collabTemplatePreviewNode.addExtraInfo("dependency", getPreviewGanttDependencyInfo(node));
        collabTemplatePreviewNode.addExtraInfo("task", getPreviewGanttTaskInfo(projectNode));
        collabTemplatePreviewNode.addExtraInfo("assignment", getPreviewGanttAssignmentInfo(node));
        return collabTemplatePreviewNode;
    }

    private List<GanttPlan> getPreviewGanttTaskInfo(CollabTemplateNode projectNode){
        GanttPlan root = new GanttPlan();
        iterateChildPlan(projectNode, root);

        return root.getChildren();
    }

    private void iterateChildPlan(CollabTemplateNode node, GanttPlan parent){
        List<CollabTemplateNode> children = node.getChildren();
        for(CollabTemplateNode child : children){
            if(!child.getType().equals(Plan.class.getName())){
                continue;
            }

            //convert and add
            child.convertSerialBytesToData();
            GanttPlan ganttPlan = new GanttPlan((Plan) child.getData());
            parent.getChildren().add(ganttPlan);

            if(!CommonTools.isNullString(ganttPlan.getPrincipal())){
                User user = this.userDao.findById(ganttPlan.getPrincipal());
                if(user != null){
                    ganttPlan.setResourceName(user.getAllName());
                }
            }
            iterateChildPlan(child, ganttPlan);
        }

        if(parent.getChildren().size() == 0){
            parent.setLeaf(true);
        }else{
            parent.getChildren().sort((o1, o2) -> {
                Double diff = Double.valueOf(o1.getDisplayOrder()) - Double.valueOf(o2.getDisplayOrder());
                return diff > 0 ? 1 : (diff == 0 ? 0 : -1);
            });
        }
    }

    private List<GanttPlanDependency> getPreviewGanttDependencyInfo(CollabTemplateNode rootNode){
        List<GanttPlanDependency> retV = UtilFactory.newArrayList();

        List<CollabTemplateNode> dependencyTemplateNodes = rootNode.getChildren();
        for(CollabTemplateNode node : dependencyTemplateNodes){
            node.convertSerialBytesToData();
            retV.add((GanttPlanDependency) node.getData());
        }
        return retV;
    }

    private GanttAssignmentData getPreviewGanttAssignmentInfo(CollabTemplateNode rootNode){
        GanttAssignmentData retV = new GanttAssignmentData();
        return retV;
    }

    @Autowired
    protected ISqlEngine orientSqlEngine;
    @Autowired
    private UserDAO userDao;
}
