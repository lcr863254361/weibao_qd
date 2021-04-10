package com.orient.dsrestful.listener.updateSchema;

import com.orient.dsrestful.event.UpdateSchemaEvent;
import com.orient.dsrestful.eventparam.UpdateSchemaParam;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metadomain.Table;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by GNY on 2018/4/2
 */
@Component
public class UpdateTableListener extends OrientEventListener {

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
        Set<String> addTableList = param.getAddTableList();
        newSchema.setId(oldSchema.getId());
        //3.更新数据类
        for (Table table : newSchema.getTables()) {
            try {
                updateTable(newSchema, oldSchema, table, addTableList);
            } catch (Exception e) {
                e.printStackTrace();
                throw new OrientBaseAjaxException("-1", "更新table记录失败：" + e.getMessage());
            }
        }
    }

    /**
     * 更新数据类
     *
     * @param newSchema --数据模型对象
     * @param table     --数据类
     * @param oldSchema --数据库模型对象
     * @throws Exception
     */
    private void updateTable(Schema newSchema, Schema oldSchema, Table table, Set<String> addTableList) throws Exception {

        ITable dbTable = oldSchema.getTableByName(table.getName());
        if (dbTable != null) { //非新增表
            table.setId(dbTable.getId());
            setTableName(newSchema, table, addTableList);
            table.setIsValid(Table.VALID);
            for (Table childTable : table.getChildTables()) {
                updateChildTable(newSchema, table, childTable, oldSchema, addTableList);
            }
        } else {//新增表
            setTableName(newSchema, table, addTableList);             //设置表的名称
            addTableList.add(table.getTableName());
            metaDAOFactory.getTableDAO().save(table);   //保存数据类
            if (table.getChildTables().size() != 0) {   //是否存在子数据类
                for (Table childTable : table.getChildTables()) {
                    updateChildTable(newSchema, table, childTable, oldSchema, addTableList);//更新其子数据类
                }
            }
        }
    }

    /**
     * 根据是否是引用表来确定表的实际名称
     *
     * @param schema 表所在的Schema
     * @param table  表的实例
     */
    private void setTableName(Schema schema, Table table, Set<String> addTableList) {
        if (table.getType() == null || table.getType().equals("0")) {    //普通数据类
            table.setTableName(table.getName() + "_" + schema.getId());
        } else if (table.getType().equals("1")) {                        //系统表
            table.setTableName(table.getMapTable());
        } else if (table.getType().equals("2")) {                        //共享模型的数据类
            List<Map<String, Object>> list = metaDAOFactory.getJdbcTemplate().queryForList("SELECT S_TABLE_NAME FROM CWM_TABLES WHERE ID ='" + table.getMapTable() + "'");
            if (!list.isEmpty()) {
                table.setTableName((String) list.get(0).get("S_TABLE_NAME"));
            } else {
                table.setTableName(table.getName() + "_" + schema.getId());
            }
        }
    }

    private void updateChildTable(Schema schema, Table parentTable, Table childTable, Schema dbSchema, Set<String> addTableList) throws Exception {
        ITable dbTable = dbSchema.getTableByName(childTable.getName());
        if (dbTable != null) {    //非新增子表
            childTable.setId(dbTable.getId());
            childTable.setParentTable(parentTable);
            setTableName(schema, childTable, addTableList);
            childTable.setIsValid(Table.VALID);
            childTable.setSchema(schema);
            if (childTable.getChildTables().size() != 0) {
                for (Table loopTable : childTable.getChildTables()) {
                    updateChildTable(schema, childTable, loopTable, dbSchema, addTableList);//如果存在子表，更新其子数据类
                }
            }
        } else {    //新增子表
            //设置表的名称
            setTableName(schema, childTable, addTableList);
            addTableList.add(childTable.getTableName());
            childTable.setParentTable(parentTable);
            metaDAOFactory.getTableDAO().save(childTable);//保存数据类
            if (childTable.getChildTables().size() != 0) {//是否存在子数据类
                Set<Table> childTableSet = childTable.getChildTables();
                for (Table loopTable : childTableSet) {
                    updateChildTable(schema, childTable, loopTable, dbSchema, addTableList);//更新其子数据类
                }
            }
        }
    }

}
