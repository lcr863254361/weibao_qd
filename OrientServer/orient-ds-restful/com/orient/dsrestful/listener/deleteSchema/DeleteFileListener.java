package com.orient.dsrestful.listener.deleteSchema;

import com.orient.dsrestful.event.DeleteSchemaEvent;
import com.orient.dsrestful.eventparam.DeleteSchemaParam;
import com.orient.sysmodel.service.file.FileService;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * 删除该schema下的文件和CWM_FILE表中对应的记录
 *
 * @author GNY
 * @create 2018-03-29 10:27
 */
@Component
public class DeleteFileListener extends OrientEventListener {

    @Autowired
    FileService fileService;

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
        //删除该schema下的所有附件以及附件记录
        fileService.deleteFilesBySchemaId(param.getSchema().getId(), 1L);
    }

}
