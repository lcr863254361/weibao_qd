package com.orient.metamodel.service;

import com.orient.metamodel.DBUtil;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metadomain.Table;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.operationinterface.ITable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-04-02 20:03
 */
@Service
public class UpdateTableService extends DBUtil{

    @Autowired
    SaveTableService saveTableService;

    public void updateTables(Schema newSchema, Schema oldSchema) throws Exception {
        for (Table table : newSchema.getTables()) {
            updateTable(newSchema, oldSchema, table);
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
    private void updateTable(Schema newSchema, Schema oldSchema, Table table) throws Exception {

        ITable dbTable = oldSchema.getTableByName(table.getName());
        if (dbTable != null) { //非新增表
            table.setId(dbTable.getId());
            saveTableService.setTableName(newSchema, table);  //非新增表需要修改表名
            table.setIsValid(Table.VALID);
            for (Table childTable : table.getChildTables()) {
                updateChildTable(newSchema, table, childTable, oldSchema);
            }
        } else {  //新增表
            saveTableService.setTableName(newSchema, table); //设置表的名称
            metaDAOFactory.getTableDAO().save(table);        //保存数据类
            if (table.getChildTables().size() != 0) {        //是否存在子数据类
                for (Table childTable : table.getChildTables()) {
                    updateChildTable(newSchema, table, childTable, oldSchema);//更新其子数据类
                }
            }
        }
    }

    private void updateChildTable(Schema schema, Table parentTable, Table childTable, Schema dbSchema) throws Exception {
        ITable dbTable = dbSchema.getTableByName(childTable.getName());
        if (dbTable != null) {
            childTable.setId(dbTable.getId());
            childTable.setParentTable(parentTable);
            saveTableService.setTableName(schema, childTable);
            childTable.setIsValid(Table.VALID);
            childTable.setSchema(schema);
            if (childTable.getChildTables().size() != 0) {
                // 是否存在子数据类
                for (Table loopTable : childTable.getChildTables()) {
                    updateChildTable(schema, childTable, loopTable, dbSchema);//更新其子数据类
                }
            }
        } else {
            //设置表的名称
            saveTableService.setTableName(schema, childTable);
            childTable.setParentTable(parentTable);
            metaDAOFactory.getTableDAO().save(childTable);//保存数据类
            if (childTable.getChildTables().size() != 0) {//是否存在子数据类
                Set<Table> childTableSet = childTable.getChildTables();
                for (Table loopTable : childTableSet) {
                    updateChildTable(schema, childTable, loopTable, dbSchema);//更新其子数据类
                }
            }
        }
    }

}
