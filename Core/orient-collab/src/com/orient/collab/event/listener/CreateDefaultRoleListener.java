package com.orient.collab.event.listener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.business.strategy.ProjectTreeNodeStrategy;
import com.orient.collab.event.ProjectTreeNodeCreatedEvent;
import com.orient.collab.event.ProjectTreeNodeCreatedEventParam;
import com.orient.modeldata.eventListener.SaveModelDataListener;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-07-14 下午3:01
 */
@Component
public class CreateDefaultRoleListener extends SaveModelDataListener {

    @Autowired
    @Qualifier("BusinessModelService")
    protected IBusinessModelService businessModelService;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return ProjectTreeNodeCreatedEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }

        OrientEvent orientEvent = (OrientEvent) event;
        ProjectTreeNodeCreatedEventParam eventSource = (ProjectTreeNodeCreatedEventParam) orientEvent.getParams();

        if (!eventSource.isCreateData()) {
            return;
        }

        Map dataMap = eventSource.getDataMap();
        String userId = UserContextUtil.getUserId();
        String modelId = eventSource.getModelId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId,modelId,null, EnumInter.BusinessModelEnum.Table);

        ProjectTreeNodeStrategy nodeStrategy = ProjectTreeNodeStrategy.fromString(businessModel.getMatrix().getName());
        if(nodeStrategy == null){
            return;
        }

        nodeStrategy.createDefaultRole(businessModel, dataMap);
    }
}
