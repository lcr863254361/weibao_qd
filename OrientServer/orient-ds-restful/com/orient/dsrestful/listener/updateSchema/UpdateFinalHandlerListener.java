package com.orient.dsrestful.listener.updateSchema;

import com.orient.dsrestful.event.UpdateSchemaEvent;
import com.orient.dsrestful.eventparam.UpdateSchemaParam;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.business.HibernateDDLHelper;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.metaengine.business.OracleSchemaTranslator;
import com.orient.metamodel.metaengine.impl.MetaUtilImpl;
import com.orient.metamodel.service.SaveViewService;
import com.orient.metamodel.service.SchemaService;
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
public class UpdateFinalHandlerListener extends OrientEventListener {

    @Autowired
    MetaDAOFactory metaDAOFactory;

    @Autowired
    SchemaService schemaService;

    @Autowired
    SaveViewService viewService;

    @Autowired
    MetaUtilImpl metaEngine;

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
        Set<String> deleteTableList = param.getDeleteTableList();
        //执行创建业务表的SQL和创建物化视图的SQL
        try {
            OracleSchemaTranslator schemaIO = (OracleSchemaTranslator) param.getNewSchema().getBean("schemaio");
            HibernateDDLHelper.getInstance(schemaIO.getHibernateProperties()).generateDB(param.getNewSchema());
            schemaService.deleteBusinessData(oldSchema, deleteTableList);     //物理删除业务数据
            schemaService.deleteLogicTableColumn(oldSchema); //逻辑删除tableColumn,注意：viewColumn已经被物理删除
            metaDAOFactory.getJdbcTemplate().execute("UPDATE CWM_TABLES SET PID = '' WHERE PID IS NULL AND SCHEMA_ID='" + oldSchema.getId() + "'");
            viewService.createPhysicalView(true, param.getCreateViewSqlMap());
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrientBaseAjaxException("-1", e.getMessage());
        }
        //最后再删除表的sequence，如果前面没有异常就把sequence删除，如果前面异常，就执行不到删除sequence的代码，到时候闪回删除表就可以了
        //如果在删除物理表的时候就删除sequence，万一出现异常就无法恢复sequence
        deleteSequence(deleteTableList);
        //刷新缓存
        metaEngine.refreshMetaData(oldSchema.getId());
    }

    private void deleteSequence(Set<String> deleteTableList) {
        deleteTableList.forEach(deleteTableName -> {
            metaDAOFactory.getJdbcTemplate().execute("DROP sequence SEQ_" + deleteTableName);  //删除sequence
        });
    }

}
