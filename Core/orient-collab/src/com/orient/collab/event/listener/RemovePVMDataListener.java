package com.orient.collab.event.listener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.event.ProjectTreeNodeDeletedEvent;
import com.orient.collab.event.ProjectTreeNodeDeletedEventParam;
import com.orient.collab.model.TreeDeleteResult;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.pvm.TaskCheckModel;
import com.orient.sysmodel.domain.pvm.TaskCheckRelation;
import com.orient.sysmodel.service.pvm.ITaskCheckModelService;
import com.orient.sysmodel.service.pvm.ITaskCheckRelationService;
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
 * Detail: delete related PVM data
 */
@Service
public class RemovePVMDataListener extends OrientEventListener {
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
            add(CollabConstants.PLAN);
            add(CollabConstants.TASK);
        }};

        TreeDeleteResult treeDeleteResult = param.getTreeDeleteResult();

        targetDic.forEach(targetName -> deletePVMData(treeDeleteResult, targetName));

    }

    private void deletePVMData(TreeDeleteResult treeDeleteResult, String targetModel) {
        List<Long> targetIds = CommonTools.stringListToLongList(treeDeleteResult.getTreeDeleteTargets().stream().filter(treeDeleteTarget -> targetModel.equals(treeDeleteTarget.getModelName()))
                .map(treeDeleteTarget -> treeDeleteTarget.getDataId()).collect(Collectors.toList()));
        if (!CommonTools.isEmptyList(targetIds)) {
            IBusinessModel businessModel = businessModelService.getBusinessModelBySName(targetModel, CollabConstants.COLLAB_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
            Long modelId = Long.valueOf(businessModel.getId());
            //delete check model
            List<TaskCheckModel> taskCheckModels = taskCheckModelService.list(Restrictions.eq("taskmodelid", modelId), Restrictions.in("taskdataid", targetIds));
            taskCheckModels.forEach(taskCheckModel -> taskCheckModelService.delete(taskCheckModel));
            //delete check model relation and business model data
            List<TaskCheckRelation> taskCheckRelations = taskCheckRelationService.list(Restrictions.eq("taskmodelid", modelId), Restrictions.in("taskdataid", targetIds));
            taskCheckRelations.forEach(taskCheckRelation -> {
                //delete business model data
                IBusinessModel checkModel = businessModelService.getBusinessModelById(taskCheckRelation.getCheckmodelid().toString(), EnumInter.BusinessModelEnum.Table);
                orientSqlEngine.getBmService().delete(checkModel, taskCheckRelation.getCheckdataid().toString());
                taskCheckRelationService.delete(taskCheckRelation);
            });

        }
    }

    @Autowired
    ITaskCheckModelService taskCheckModelService;

    @Autowired
    ITaskCheckRelationService taskCheckRelationService;

    @Autowired
    IBusinessModelService businessModelService;

    @Autowired
    ISqlEngine orientSqlEngine;
}
