package com.orient.dsrestful.listener.saveSchema;

import com.orient.dsrestful.enums.SchemaSaveResponseEnum;
import com.orient.dsrestful.event.SaveSchemaEvent;
import com.orient.dsrestful.eventparam.SaveSchemaParam;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.service.SaveRestrictionService;
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
 * @create 2018-03-31 11:12
 */
@Component
public class SaveRestrictionListener extends OrientEventListener {

    @Autowired
    MetaDAOFactory metaDAOFactory;

    @Autowired
    SaveRestrictionService saveRestrictionService;

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
        Schema schema = param.getSchema();
        //遍历schema的数据约束，保存数据约束及其相关属性
        saveRestrictionService.saveRestrictions(schema, param.getColumnMap());
        metaDAOFactory.getHibernateTemplate().flush();
        //修改引用约束的Column的记录，即修改CWM_TAB_COLUMN
        try {
            saveRestrictionService.modifyRestrictionColumns(schema, param.getRestrictionRefColumnsMap());
        } catch (Exception e) {
            throw new DSException(SchemaSaveResponseEnum.TYPE_EXCEPTION.getType(), e.getMessage());
        }
    }

}
