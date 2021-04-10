package com.orient.collabdev.business.version.event.listener.common;

import com.orient.collabdev.business.version.event.CollabVersionChangeEvent;
import com.orient.collabdev.business.version.event.CollabVersionChangeEventParam;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * 增加父亲节点版本
 *
 * @author panduanduan
 * @create 2018-08-11 2:34 PM
 */
@Component
public class IncreaseParentNodeVersion extends OrientEventListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == CollabVersionChangeEvent.class || CollabVersionChangeEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        OrientEvent orientEvent = (OrientEvent) applicationEvent;
        CollabVersionChangeEventParam eventParam = (CollabVersionChangeEventParam) orientEvent.getParams();
        ManagerStatusEnum managerStatusEnum = eventParam.getProjectStatus();
        if (managerStatusEnum.equals(ManagerStatusEnum.PROCESSING)) {
            //
        }
    }
}
