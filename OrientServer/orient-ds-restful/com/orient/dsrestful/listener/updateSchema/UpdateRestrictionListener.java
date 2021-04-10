package com.orient.dsrestful.listener.updateSchema;

import com.orient.dsrestful.event.UpdateSchemaEvent;
import com.orient.dsrestful.eventparam.UpdateSchemaParam;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.service.SaveRestrictionService;
import com.orient.metamodel.service.UpdateRestrictionService;
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
public class UpdateRestrictionListener extends OrientEventListener {

    @Autowired
    SaveRestrictionService restrictionService;

    @Autowired
    UpdateRestrictionService updateRestrictionService;

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
        try {
         /*   //todo 这里加一个flush看看是否可以让对象同步
            metaDAOFactory.getHibernateTemplate().flush();*/
            //5.更新数据约束
            updateRestrictionService.updateRestrictions(newSchema, oldSchema, param.getColumnMap());
            //6.修改引用约束的Column的记录，即修改CWM_TAB_COLUMN
            restrictionService.modifyRestrictionColumns(newSchema, param.getRestrictionRefColumnsMap());
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrientBaseAjaxException("-1", e.getMessage());
        }
    }

}
