package com.orient.dsrestful.listener.deleteSchema;

import com.orient.dsrestful.enums.SchemaDeleteResponseEnum;
import com.orient.dsrestful.event.DeleteSchemaEvent;
import com.orient.dsrestful.eventparam.DeleteSchemaParam;
import com.orient.metamodel.metadomain.*;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.metaengine.impl.MetaUtilImpl;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.springmvcsupport.exception.DSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 删除表：包括和CWM_TABLES表相关的表的数据记录，并删除物理业务表
 *
 * @author GNY
 * @create 2018-03-29 10:37
 */
@Component
public class DeleteTableListener extends OrientEventListener {

    @Autowired
    MetaUtilImpl metaEngine;

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
        //删除表(先删数据记录，再删物理表)
        try {
            deleteBusinessTables(param.getSchema());
        } catch (Exception e) {
            throw new DSException(SchemaDeleteResponseEnum.TYPE_EXCEPTION.getType(), e.getMessage());
        }
    }

    /**
     * 删除一个Schema下的所有业务表
     *
     * @param schema
     */
    public void deleteBusinessTables(Schema schema) throws Exception {
        //先删数据，再删业务表的物理表
        schema.getTables().forEach(table -> {
            try {
                //删除其他几张表的数据
                deleteTableAttribute(table);
                //查询物理表是否存在，如果物理表被人为通过数据库工具删除了，这时候去删除物理表会报错
                List<Map<String, Object>> mapList = metaDaoFactory.getJdbcTemplate().queryForList("SELECT * FROM USER_TABLES WHERE TABLE_NAME = '" + table.getTableName() + "'");
                if (mapList.size() > 0) {
                    metaDaoFactory.getTableDAO().delete(table); //删除table表的数据
                    metaDaoFactory.getJdbcTemplate().execute("DROP TABLE " + table.getTableName() + " purge "); //删除物理表
                    metaDaoFactory.getJdbcTemplate().execute("DROP sequence seq_" + table.getTableName());       //删除sequence
                }
            } catch (Exception e) {
                throw new DSException(SchemaDeleteResponseEnum.TYPE_EXCEPTION.getType(), e.getMessage());
            }
        });
    }

    private void deleteTableAttribute(Table table) throws Exception {
        //删除table的子表数据
        for (Table childTable : table.getChildTables()) {
            metaDaoFactory.getTableDAO().delete(childTable);
        }
        //删除table类在CWM_TABLE_COLUMN表中的相关数据
        List tableColumnList = metaDaoFactory.getTableColumnDAO().findByTableId(table);
        if (!tableColumnList.isEmpty()) {
            for (Iterator kIt = tableColumnList.iterator(); kIt.hasNext(); ) {
                TableColumn kColumn = (TableColumn) kIt.next();
                metaDaoFactory.getTableColumnDAO().delete(kColumn);
            }
        }
        //删除table类在CWM_TAB_COLUMN表中的相关数据
        for (Column column : table.getCwmTabColumnses()) {
            metaDaoFactory.getColumnDAO().delete(column);
        }
        //删除table的关联字段，就是删除在CWM_RELATION_COLUMN中的相关数据
        for (Column column : table.getCwmTabColumnses()) {
            if (column.getCategory().equals(Column.CATEGORY_RELATION)) {    //关系属性
                for (RelationColumns relationColumn : table.getCwmRelationColumnses()) {
                    if (relationColumn.getCwmTabColumnsByColumnId().equals(column)) {
                        metaDaoFactory.getRelationColumnsDAO().delete(relationColumn);
                    }
                }
            }
        }
        //删除该table的算法
        for (Column column : table.getCwmTabColumnses()) {
            if (!column.getArithAttribute().isEmpty()) {
                for (ArithAttribute arithAttribute : column.getArithAttribute()) {
                    metaDaoFactory.getArithAttributeDAO().delete(arithAttribute);
                }
            }
        }
    }

}
