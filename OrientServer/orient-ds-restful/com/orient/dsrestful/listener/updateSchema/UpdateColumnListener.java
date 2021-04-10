package com.orient.dsrestful.listener.updateSchema;

import com.orient.dsrestful.event.UpdateSchemaEvent;
import com.orient.dsrestful.eventparam.UpdateSchemaParam;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.service.UpdateTableColumnService;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by GNY on 2018/4/2
 */
@Component
public class UpdateColumnListener extends OrientEventListener {

    @Autowired
    UpdateTableColumnService updateTableColumnService;

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
        Set<String> updateTableList = param.getUpdateTableList();
        newSchema.setId(oldSchema.getId());
        param.setNeedFlashback(true);
        //更新数据类的Column
        try {
            updateTableColumnService.updateTableColumns(newSchema, oldSchema, param.getColumnMap(), param.getRestrictionRefColumnsMap(), param.getAlterInfo(),updateTableList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrientBaseAjaxException("-1", e.getMessage());
        }
    }

}
