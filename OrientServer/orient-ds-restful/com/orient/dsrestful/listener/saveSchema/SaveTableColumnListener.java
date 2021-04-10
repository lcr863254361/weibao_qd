package com.orient.dsrestful.listener.saveSchema;

import com.orient.dsrestful.enums.SchemaSaveResponseEnum;
import com.orient.dsrestful.event.SaveSchemaEvent;
import com.orient.dsrestful.eventparam.SaveSchemaParam;
import com.orient.metamodel.metadomain.Column;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.service.SaveTableColumnService;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.springmvcsupport.exception.DSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-31 10:46
 */
@Component
public class SaveTableColumnListener extends OrientEventListener {

    @Autowired
    MetaDAOFactory metaDAOFactory;

    @Autowired
    SaveTableColumnService saveTableColumnService;

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
        Map<String, Column> columnMap = param.getColumnMap();
        Map<String, List<Column>> restrictionRefColumnsMap = param.getRestrictionRefColumnsMap();
        //遍历该schema下的table数据类,保存数据类的普通属性，关系属性，参数关联约束
        //由于关系属性中需要使用tableId,所以必须等所有的Table记录都创建好后才能进行这步操作
        try {
            saveTableColumnService.saveTableColumns(param.getSchema(), columnMap, restrictionRefColumnsMap);
            metaDAOFactory.getHibernateTemplate().flush();
        } catch (Exception e) {
            throw new DSException(SchemaSaveResponseEnum.TYPE_EXCEPTION.getType(), e.getMessage());
        }
    }

}
