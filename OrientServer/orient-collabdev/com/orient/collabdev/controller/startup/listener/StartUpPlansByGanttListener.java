package com.orient.collabdev.controller.startup.listener;

import com.orient.collabdev.business.startup.PlanStartBusiness;
import com.orient.collabdev.controller.startup.event.StartUpProjectEvent;
import com.orient.collabdev.controller.startup.event.StartUpProjectParam;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author GNY
 * @Date 2018/9/17 14:44
 * @Version 1.0
 **/
@Component
public class StartUpPlansByGanttListener extends OrientEventListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == StartUpProjectEvent.class || StartUpProjectEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        OrientEvent orientEvent = (OrientEvent) applicationEvent;
        StartUpProjectParam eventParam = (StartUpProjectParam) orientEvent.getParams();
        String projectNodeId = eventParam.getProjectNodeId();
        planStartBusiness.startPlans(projectNodeId);
    }

    @Autowired
    PlanStartBusiness planStartBusiness;

}
