package com.orient.modeldata.eventListener;

import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.sysmodel.dao.flow.MJDao;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-02-17 11:04
 */
@Component
public class CopyMJToSysSecrecyListener extends OrientEventListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }

        OrientEvent orientEvent = (OrientEvent) event;
        SaveModelDataEventParam eventSource = (SaveModelDataEventParam) orientEvent.getParams();

        Map dataMap = eventSource.getDataMap();
        String hasMJ = (String) dataMap.get("HASMJ");
        String tableName = (String) dataMap.get("TableName");
        String Id = (String) dataMap.get("ID");
        if(!hasMJ.isEmpty()){
            String secrecyValue = (String) dataMap.get("SYS_SECRECY");
            mjDao.CopyMJToSysSecrecy(tableName, secrecyValue, Id);
        }

    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == SaveModelDataEvent.class || SaveModelDataEvent.class.isAssignableFrom(eventType);
    }

    @Autowired
    private MJDao mjDao;

}
