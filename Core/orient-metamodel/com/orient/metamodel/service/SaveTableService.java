package com.orient.metamodel.service;

import com.orient.metamodel.DBUtil;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metadomain.Table;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 保存table记录到CWM_TABLES表里
 *
 * @author GNY
 * @create 2018-03-28 13:42
 */
@Service
public class SaveTableService extends DBUtil {

    public void saveTables(Schema schema) {
        try {
            for (Table table : schema.getTables()) {
                setTableName(schema, table);  //设置表的名称
                metaDAOFactory.getTableDAO().save(table); //保存数据类
                if (!table.getChildTables().isEmpty()) {   //如果存在子表则保存其子数据类
                    saveChildTable(schema, table);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据是否是引用表来确定表的实际名称
     *
     * @param schema 表所在的Schema
     * @param table  表的实例
     * @return void
     */
    public void setTableName(Schema schema, Table table) {
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

    /**
     * 子数据类保存
     *
     * @param schema 所在的schema
     * @param table  父数据类
     */
    private void saveChildTable(Schema schema, Table table) throws Exception {
        // 遍历该数据类的子数据类
        for (Table childTable : table.getChildTables()) {
            childTable.setParentTable(table);
            setTableName(schema, childTable);
            metaDAOFactory.getTableDAO().save(childTable);
            // 递归保存其子数据类
            if (childTable.getChildTables().size() != 0) {
                saveChildTable(schema, childTable);
            }
        }
    }

}
