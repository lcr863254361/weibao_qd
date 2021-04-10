package com.orient.collab.event.listener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.business.strategy.ProjectTreeNodeStrategy;
import com.orient.collab.event.ProjectTreeNodeEditEvent;
import com.orient.collab.event.ProjectTreeNodeEditEventParam;
import com.orient.modeldata.eventListener.UpdateModelDataListener;
import com.orient.utils.CommonTools;
import com.orient.web.base.OrientEventBus.OrientEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * UpdateDefaultRoleListener
 *
 * @author Seraph
 *         2016-08-18 下午4:20
 */
@Component
public class UpdateDefaultRoleListener extends UpdateModelDataListener {

    @Autowired
    @Qualifier("BusinessModelService")
    protected IBusinessModelService businessModelService;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return ProjectTreeNodeEditEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }

        OrientEvent orientEvent = (OrientEvent) event;
        ProjectTreeNodeEditEventParam eventSource = (ProjectTreeNodeEditEventParam) orientEvent.getParams();

        String newUserIds = CommonTools.Obj2String(eventSource.getDataMap().get("PRINCIPAL_" + eventSource.getModelId()));
        String oriUserIds = eventSource.getOriginalUserId();

        if(newUserIds.equals(oriUserIds)){
            return;
        }

        String modelId = eventSource.getModelId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);

        ProjectTreeNodeStrategy nodeStrategy = ProjectTreeNodeStrategy.fromString(businessModel.getMatrix().getName());
        if(nodeStrategy == null){
            return;
        }
        nodeStrategy.updateDefaultRole(businessModel, CommonTools.Obj2String(eventSource.getDataMap().get("ID")), oriUserIds, newUserIds);

    }
}
