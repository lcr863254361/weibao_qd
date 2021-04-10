package com.orient.collab.event.listener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.event.ProjectTreeNodeDeletedEvent;
import com.orient.collab.event.ProjectTreeNodeDeletedEventParam;
import com.orient.collab.model.TreeDeleteResult;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.sysmodel.domain.taskdata.DataObjectOldVersionEntity;
import com.orient.sysmodel.service.taskdata.IDataObjectOldVersionService;
import com.orient.sysmodel.service.taskdata.IDataObjectService;
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
 * Detail: delete related design data
 */
@Service
public class RemoveDesignDataListener extends OrientEventListener {
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

        targetDic.forEach(targetName -> deleteDesignData(treeDeleteResult, targetName));
    }

    private void deleteDesignData(TreeDeleteResult treeDeleteResult, String targetModel) {
        List<String> targetIds = treeDeleteResult.getTreeDeleteTargets().stream().filter(treeDeleteTarget -> targetModel.equals(treeDeleteTarget.getModelName()))
                .map(treeDeleteTarget -> treeDeleteTarget.getDataId()).collect(Collectors.toList());
        if (!CommonTools.isEmptyList(targetIds)) {
            String collabSchemaId = CollabConstants.COLLAB_SCHEMA_ID;
            IBusinessModel businessModel = businessModelService.getBusinessModelBySName(targetModel, collabSchemaId, EnumInter.BusinessModelEnum.Table);
            List<DataObjectEntity> dataObjectEntities = dataObjectService.list(Restrictions.eq("modelid", businessModel.getId()),
                    Restrictions.in("dataid", targetIds));
            dataObjectEntities.forEach(dataObjectEntity -> dataObjectService.delete(dataObjectEntity));

            List<DataObjectOldVersionEntity> dataObjectOldVersionEntities = dataObjectOldVersionService.list(Restrictions.eq("modelid", businessModel.getId()),
                    Restrictions.in("dataid", targetIds));
            dataObjectOldVersionEntities.forEach(dataObjectOldVersionEntity -> dataObjectOldVersionService.delete(dataObjectOldVersionEntity));
        }
    }

    @Autowired
    IDataObjectService dataObjectService;

    @Autowired
    IDataObjectOldVersionService dataObjectOldVersionService;

    @Autowired
    protected IBusinessModelService businessModelService;

}
