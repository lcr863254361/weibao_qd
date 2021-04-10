package com.orient.dsrestful.listener.saveSchema;

import com.orient.dsrestful.enums.SchemaSaveResponseEnum;
import com.orient.dsrestful.event.SaveSchemaEvent;
import com.orient.dsrestful.eventparam.SaveSchemaParam;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.service.SaveViewService;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.springmvcsupport.exception.DSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-31 11:18
 */
@Component
public class SaveViewListener extends OrientEventListener {

    @Autowired
    MetaDAOFactory metaDAOFactory;

    @Autowired
    SaveViewService saveViewService;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == SaveSchemaEvent.class || SaveSchemaEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        SaveSchemaParam param = (SaveSchemaParam) orientEvent.getParams();
        try {
            saveViewService.saveView(param.getSchema(), param.getColumnMap(), param.getCreateViewSqlMap());
            metaDAOFactory.getHibernateTemplate().flush();
            metaDAOFactory.getJdbcTemplate().execute("UPDATE CWM_TABLES SET PID ='' WHERE PID IS NULL");
        } catch (Exception e) {
            throw new DSException(SchemaSaveResponseEnum.TYPE_EXCEPTION.getType(), e.getMessage());
        }
    }

}
