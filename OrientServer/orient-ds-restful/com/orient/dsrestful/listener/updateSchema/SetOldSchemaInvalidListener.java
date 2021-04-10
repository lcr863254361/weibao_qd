package com.orient.dsrestful.listener.updateSchema;

import com.orient.dsrestful.event.UpdateSchemaEvent;
import com.orient.dsrestful.eventparam.UpdateSchemaParam;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * Created by GNY on 2018/4/2
 */
@Component
public class SetOldSchemaInvalidListener extends OrientEventListener {

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
        Schema oldSchema = param.getOldSchema();
        //2.将所有与schema相关的数据类、数据类字段属性、数据约束、数据视图的isValid字段设置为无效
        setOldSchemaInvalid(oldSchema);
    }

    /**
     * 将所有与schema相关的数据类、数据类字段属性、数据约束、数据视图设置为无效（就是假删除）
     *
     * @param oldSchema
     * @return ErrorInfo
     */
    public void setOldSchemaInvalid(Schema oldSchema) {
        try {
            //数据类的ISVALID字段设为无效
            StringBuffer updateTableValidSQL = new StringBuffer();
            updateTableValidSQL.append("UPDATE CWM_TABLES SET ")
                    .append("IS_VALID = 0")
                    .append(" WHERE SCHEMA_ID ='")
                    .append(oldSchema.getId())
                    .append("' AND IS_VALID = 1");
            metaDAOFactory.getJdbcTemplate().update(updateTableValidSQL.toString());
            //数据字段的ISVALID字段设为无效
            StringBuffer updateColumnValidSQL = new StringBuffer();
            updateColumnValidSQL.append("UPDATE CWM_TAB_COLUMNS SET ")
                    .append("IS_VALID = 0")
                    .append(" WHERE TABLE_ID IN (SELECT ID FROM CWM_TABLES WHERE SCHEMA_ID ='")
                    .append(oldSchema.getId())
                    .append("') AND IS_VALID ='1'");
            metaDAOFactory.getJdbcTemplate().update(updateColumnValidSQL.toString());
            //数据约束的ISVALID字段设为无效
            StringBuffer updateRestrictionValidSQL = new StringBuffer();
            updateRestrictionValidSQL.append("UPDATE CWM_RESTRICTION SET ")
                    .append("IS_VALID=0")
                    .append(" WHERE SCHEMA_ID='")
                    .append(oldSchema.getId())
                    .append("'");
            metaDAOFactory.getJdbcTemplate().update(updateRestrictionValidSQL.toString());
            //数据视图的ISVALID字段设为无效
            StringBuffer updateViewValidSQL = new StringBuffer();
            updateViewValidSQL.append("UPDATE CWM_VIEWS SET ")
                    .append("IS_VALID=0")
                    .append(" WHERE SCHEMA_ID='")
                    .append(oldSchema.getId())
                    .append("'");
            metaDAOFactory.getJdbcTemplate().update(updateViewValidSQL.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
