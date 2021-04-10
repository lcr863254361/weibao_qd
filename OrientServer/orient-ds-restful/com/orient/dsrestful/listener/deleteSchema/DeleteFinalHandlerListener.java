package com.orient.dsrestful.listener.deleteSchema;

import com.orient.dsrestful.enums.SchemaDeleteResponseEnum;
import com.orient.dsrestful.event.DeleteSchemaEvent;
import com.orient.dsrestful.eventparam.DeleteSchemaParam;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.MetaUtil;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.springmvcsupport.exception.DSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * 删除schema
 * 1.CWM_SYS_ROLE_SCHEMA,CWM_SCHEMA中的记录
 * 2.内存中的schema
 *
 * @author GNY
 * @create 2018-03-29 15:00
 */
@Component
public class DeleteFinalHandlerListener extends OrientEventListener {

    @Autowired
    MetaUtil metaEngine;

    @Autowired
    MetaDAOFactory metaDaoFactory;

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
        Schema schema = param.getSchema();
        try {
            //删除内存中的schema
            metaEngine.getMeta(false).deleteSchema(schema.getId());
            //删除CWM_SCHEMA表中的schema记录
            metaDaoFactory.getSchemaDAO().delete(schema);
            //删除在CWM_SYS_ROLE_SCHEMA表中的role和schema的关系记录
            String sql = "DELETE FROM CWM_SYS_ROLE_SCHEMA WHERE SCHEMA_ID ='" + schema.getId() + "'";
            metaDaoFactory.getJdbcTemplate().execute(sql);
        } catch (Exception e) {
            throw new DSException(SchemaDeleteResponseEnum.TYPE_EXCEPTION.getType(), e.getMessage());
        }
    }

}
