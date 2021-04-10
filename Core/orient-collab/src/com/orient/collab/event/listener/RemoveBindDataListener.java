package com.orient.collab.event.listener;

import com.orient.collab.config.CollabConstants;
import com.orient.collab.event.ProjectTreeNodeDeletedEvent;
import com.orient.collab.event.ProjectTreeNodeDeletedEventParam;
import com.orient.collab.model.Project;
import com.orient.collab.model.Task;
import com.orient.collab.model.TreeDeleteResult;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collab.CollabPrjModelRelationEntity;
import com.orient.sysmodel.domain.component.CwmComponentModelEntity;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.domain.flow.TaskDataRelation;
import com.orient.sysmodel.service.collab.ICollabPrjModelRelationService;
import com.orient.sysmodel.service.component.IComponentBindService;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.sysmodel.service.flow.ITaskDataRelationService;
import com.orient.utils.CommonTools;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mengbin on 16/8/25.
 * Purpose:
 * Detail: delete bind data info
 * delete flow data relation
 * delete his flow data relation
 * delete task data relation
 * delete his task data relation
 * delete prj model relation
 * delete componnet bind
 */
@Service
public class RemoveBindDataListener extends OrientEventListener {
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return ProjectTreeNodeDeletedEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }

        OrientEvent orientEvent = (OrientEvent) event;
        ProjectTreeNodeDeletedEventParam param = (ProjectTreeNodeDeletedEventParam) orientEvent.getParams();

        if (!param.getTreeDeleteResult().getDeleteSuccess()) {
            orientEvent.aboardEvetn();
            return;
        }

        List<String> targetDic = new ArrayList<String>() {{
            add(CollabConstants.PROJECT);
            add(CollabConstants.PLAN);
            add(CollabConstants.TASK);
        }};

        TreeDeleteResult treeDeleteResult = param.getTreeDeleteResult();

        targetDic.forEach(targetName -> deleteBindData(treeDeleteResult, targetName));
    }

    private void deleteBindData(TreeDeleteResult treeDeleteResult, String targetModel) {
        List<String> targetIds = treeDeleteResult.getTreeDeleteTargets().stream().filter(treeDeleteTarget -> targetModel.equals(treeDeleteTarget.getModelName()))
                .map(treeDeleteTarget -> treeDeleteTarget.getDataId()).collect(Collectors.toList());
        if (!CommonTools.isEmptyList(targetIds)) {
            //delete flow data relation
            List<FlowDataRelation> flowDataRelationList = flowDataRelationService.list(Restrictions.eq(FlowDataRelation.TABLE_NAME, targetModel), Restrictions.in(FlowDataRelation.DATA_ID, targetIds));
            flowDataRelationList.forEach(flowDataRelation -> flowDataRelationService.delete(flowDataRelation));
            //delete task data relation
            if (CollabConstants.TASK.equals(targetModel)) {
                //delete task data relation
                List<TaskDataRelation> taskDataRelations = taskDataRelationService.list(Restrictions.eq(TaskDataRelation.TABLE_NAME, targetModel), Restrictions.in(TaskDataRelation.DATA_ID, targetIds));
                taskDataRelations.forEach(taskDataRelation -> taskDataRelationService.delete(taskDataRelation));
                //delete component bind
                String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
                List<CwmComponentModelEntity> componentModelEntities = componentBindService.list(Restrictions.eq("modelId", taskModelId), Restrictions.in("dataId", targetIds));
                componentModelEntities.forEach(cwmComponentModelEntity -> componentBindService.delete(cwmComponentModelEntity));
            }
            if (CollabConstants.PROJECT.equals(targetModel)) {
                //delete project model relation
                String prjModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Project.class);
                List<CollabPrjModelRelationEntity> collabPrjModelRelationEntities = collabPrjModelRelationService.list(Restrictions.eq("prjModelId", prjModelId), Restrictions.in("prjId", targetIds));
                collabPrjModelRelationEntities.forEach(collabPrjModelRelationEntity -> collabPrjModelRelationService.delete(collabPrjModelRelationEntity));
            }
        }

    }

    @Autowired
    IFlowDataRelationService flowDataRelationService;

    @Autowired
    ITaskDataRelationService taskDataRelationService;

    @Autowired
    ICollabPrjModelRelationService collabPrjModelRelationService;

    @Autowired
    ISqlEngine orientSqlEngine;

    @Autowired
    IComponentBindService componentBindService;

}
