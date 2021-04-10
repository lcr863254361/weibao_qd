package com.orient.dsrestful.listener.saveSchema;

import com.orient.dsrestful.enums.SchemaSaveResponseEnum;
import com.orient.dsrestful.event.SaveSchemaEvent;
import com.orient.dsrestful.eventparam.SaveSchemaParam;
import com.orient.metamodel.metaengine.business.*;
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
 * @create 2018-03-31 11:33
 */
@Component
public class CreatePhysicalListener extends OrientEventListener {

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
        //7.执行创建业务表的SQL和创建物化视图的SQL
        try {
            OracleSchemaTranslator schemaIO = (OracleSchemaTranslator) param.getSchema().getBean("schemaio");
            HibernateDDLHelper.getInstance(schemaIO.getHibernateProperties()).generateDB(param.getSchema());
            saveViewService.createPhysicalView(false, param.getCreateViewSqlMap());
        } catch (Exception e) {
            throw new DSException(SchemaSaveResponseEnum.TYPE_EXCEPTION.getType(), e.getMessage());
        }
    }

}
