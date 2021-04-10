package com.orient.dsrestful.listener.updateSchema;

import com.orient.dsrestful.event.UpdateSchemaEvent;
import com.orient.dsrestful.eventparam.UpdateSchemaParam;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.service.UpdateViewService;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * Created by GNY on 2018/4/2
 */
@Component
public class UpdateViewListener extends OrientEventListener {

    @Autowired
    UpdateViewService updateViewService;

    @Autowired
    MetaDAOFactory metaDAOFactory;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == UpdateSchemaEvent.class || UpdateSchemaEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        UpdateSchemaParam param = (UpdateSchemaParam) orientEvent.getParams();
        Schema newSchema = param.getNewSchema();
        Schema oldSchema = param.getOldSchema();
        newSchema.setId(oldSchema.getId());
        //更新数据视图
        try {
            updateViewService.updateView(newSchema, oldSchema, param.getCreateViewSqlMap(), param.getColumnMap());
            metaDAOFactory.getSchemaDAO().merge(newSchema);
            metaDAOFactory.getHibernateTemplate().flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrientBaseAjaxException("-1", e.getMessage());
        }
    }

}
