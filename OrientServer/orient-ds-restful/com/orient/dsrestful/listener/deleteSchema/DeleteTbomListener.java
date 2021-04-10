package com.orient.dsrestful.listener.deleteSchema;

import com.orient.dsrestful.event.DeleteSchemaEvent;
import com.orient.dsrestful.eventparam.DeleteSchemaParam;
import com.orient.sysmodel.service.tbom.TbomService;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * 删除tbom相关数据
 *
 * @author GNY
 * @create 2018-03-29 14:56
 */
@Component
public class DeleteTbomListener extends OrientEventListener {

    @Autowired
    TbomService tbomService;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == DeleteSchemaEvent.class || DeleteSchemaEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        DeleteSchemaParam param = (DeleteSchemaParam) orientEvent.getParams();
        tbomService.deleteTbom(param.getSchema().getId());
    }

}
