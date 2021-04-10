package com.orient.metamodel.metaengine.business;

import com.orient.metamodel.metadomain.Column;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metadomain.Table;
import com.orient.metamodel.metaengine.ErrorInfo;
import com.orient.metamodel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-28 14:09
 */
@Component
public class OracleSchemaTranslator extends AbstractSchemaTranslator {

    @Autowired
    SchemaService schemaService;

    @Autowired
    SaveTableService tableService;

    @Autowired
    UpdateTableService updateTableService;

    @Autowired
    SaveTableColumnService tableColumnService;

    @Autowired
    UpdateTableColumnService updateTableColumnService;

    @Autowired
    SaveRestrictionService restrictionService;

    @Autowired
    UpdateRestrictionService updateRestrictionService;

    @Autowired
    SaveViewService viewService;

    @Autowired
    UpdateViewService updateViewService;


    //当前连接的数据库的用户
    private String tsn = null;

    private static final int INTERVAL_TIME = 1;

    //记录所有字段的信息，方便查询表达式和过滤表达式字段信息的替换, <column.getIdentity(), column>
    private Map<String, Column> columnMap = new HashMap<>();

    //创建物化视图的SQL语句，key为生成顺序和视图名的组合，value为sql语句
    private Map<Integer, String> createViewSqlMap = new HashMap<>();

    //String: Restriction 的Identity
    private Map<String, List<Column>> restrictionRefColumnsMap = new HashMap<>();

    //记录所有需要修改字段在修改前的类型和长度的信息 key表示修改字段在CWM_TAB_COLUMNS表的ID值
    //value记录字段在CWM_TAB_COLUMNS表的type和MaxLength值，中间分隔符为"=="
    private Map<String, String> alterInfo = new HashMap<>();

    //记录所有已经修改了的实际表字段的信息 integer表示修改序号，由0,1,2...组成
    //String记录的是修改字段在CWM_TAB_COLUMNS表的ID值
    private Map<Integer, String> alterColumnMap = new HashMap<>();

    //修改column的状态
    private boolean status = false;

    @Override
    public void preProcess() {
        columnMap.clear();
        createViewSqlMap.clear();
        restrictionRefColumnsMap.clear();
        alterInfo.clear();
        alterColumnMap.clear();
    }

    @Override
    public void postProcess() {
    }

    @Override
    public boolean schema2XMLSchema(Schema schema) {
        try {
            schemaService.dealSchema(schema);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void saveAsDB(Schema newSchema) throws Exception {
        //1.在CWM_SCHEMA表插入记录
        metadaofactory.getSchemaDAO().save(newSchema);
        /*-----------------------------------
         * 数据类及其属性的保存
         * ---------------------------------*/
        //2.遍历schema的数据类,仅保存数据类
        tableService.saveTables(newSchema);
        //3.遍历该schema下的table数据类,保存数据类的普通属性，关系属性，参数关联约束
        //由于关系属性中需要使用tableId,所以必须等所有的Table都建好后才能进行这步操作
        tableColumnService.saveTableColumns(newSchema, columnMap, restrictionRefColumnsMap);
        metadaofactory.getHibernateTemplate().flush();
        //4.遍历schema的数据约束，保存数据约束及其相关属性
        restrictionService.saveRestrictions(newSchema, columnMap);
        metadaofactory.getHibernateTemplate().flush();
        //5.修改引用约束的Column的记录，即修改CWM_TAB_COLUMN
        restrictionService.modifyRestrictionColumns(newSchema, restrictionRefColumnsMap);
        //6.保存视图
        viewService.saveView(newSchema, columnMap, createViewSqlMap);
        metadaofactory.getHibernateTemplate().flush();
        metadaofactory.getJdbcTemplate().execute("UPDATE CWM_TABLES SET PID ='' WHERE PID IS NULL");
        columnMap.clear();
        //7.执行创建业务表的SQL和创建物化视图的SQL
        HibernateDDLHelper.getInstance(hibernateProperties).generateDB(newSchema);
        viewService.createPhysicalView(false, createViewSqlMap);
    }

    @Override
    public void updateDB(Schema newSchema, Schema oldSchema) throws Exception {
        ErrorInfo info;
        try {
            newSchema.setId(oldSchema.getId());
            //1.校验字段修改是否符合规则
            for (Table table : newSchema.getTables()) {
                info = updateTableColumnService.verifyTableColumn(table);
                if (info.ErrorId != 0) {
                    throw new Exception(info.Warning);
                }
            }
            //2.将所有与schema相关的数据类、数据类字段属性、数据约束、数据视图的isValid字段设置为无效
            info = schemaService.setOldSchemaInvalid(oldSchema);
            if (info.ErrorId != 0) {
                throw new Exception(info.Warning);
            }
            //3.更新数据类
            updateTableService.updateTables(newSchema, oldSchema);
            //4.更新数据类的Column
            //updateTableColumnService.updateTableColumns(newSchema, oldSchema, columnMap, restrictionRefColumnsMap, alterInfo);
            //5.更新数据约束
            updateRestrictionService.updateRestrictions(newSchema, oldSchema, columnMap);
            //6.修改引用约束的Column的记录，即修改CWM_TAB_COLUMN
            restrictionService.modifyRestrictionColumns(newSchema, restrictionRefColumnsMap);
            //7.更新数据视图
            updateViewService.updateView(newSchema, oldSchema, createViewSqlMap, columnMap);
            if (info.ErrorId != 0) {
                throw new Exception(info.Warning);
            }
            metadaofactory.getSchemaDAO().merge(newSchema);
            columnMap.clear();
            metadaofactory.getHibernateTemplate().flush();
            //8.修改实际业务表的字段属性
            try {
                alterColumn(newSchema, alterColumnMap);
            } catch (Exception e) {
                resume();
                throw new Exception("数据库更新实际表字段类型长度发生异常，数据库已回滚到最近一次正常状态，请重新打开数据模型进行操作！");
            }
            //9.执行创建业务表的SQL和创建物化视图的SQL
            HibernateDDLHelper.getInstance(hibernateProperties).generateDB(newSchema);
            //schemaService.deleteBusinessData(oldSchema);    //物理删除业务数据
            schemaService.deleteLogicTableColumn(oldSchema); //逻辑删除tableColumn,注意：viewColumn已经被物理删除
            metadaofactory.getJdbcTemplate().execute("UPDATE CWM_TABLES SET PID = '' WHERE PID IS NULL AND SCHEMA_ID='" + oldSchema.getId() + "'");
            viewService.createPhysicalView(true, createViewSqlMap);
        } catch (Exception e) {
            e.printStackTrace();
            resume();
            throw new Exception(e.toString());
        }
    }

    /**
     * 恢复被修改了的实际表的字段.
     */
    private void resume() {
        try {
            for (int i = 0; i < alterColumnMap.keySet().size(); i++) {
                String id = alterColumnMap.get(i);
                String[] columnInfo = alterInfo.get(id).split("==");
                String type = columnInfo[0];
                String length = columnInfo[1];
                try {
                    List list = this.getMetadaofactory().getJdbcTemplate().queryForList("select S_COLUMN_NAME,TABLE_ID from CWM_TAB_COLUMNS where id='" + id + "'");
                    if (!list.isEmpty()) {
                        for (Iterator it = list.iterator(); it.hasNext(); ) {
                            Map map = (Map) it.next();
                            String tableId = (String) map.get("TABLE_ID");
                            int a = Integer.parseInt(length) * 2;
                            String columnName = (String) map.get("S_COLUMN_NAME");
                            String tableName = (String) ((Map) (this.getMetadaofactory().getJdbcTemplate().queryForList(
                                    "select S_TABLE_NAME as c from CWM_TABLES WHERE ID='" + tableId + "' and IS_VALID=1").get(0))).get("c");
                            alter(a, columnName, type, tableName);
                            alterChildTableColumn(a, columnName, type, tableId);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            status = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改字段在实际表中的类型、长度等信息.
     *
     * @param a          字段长度
     * @param columnName 字段实际名称
     * @param type       字段类型
     * @param tableName  实际表名称
     * @throws Exception
     */
    private void alter(int a, String columnName, String type, String tableName) throws Exception {
        try {
            List list = this.getMetadaofactory().getJdbcTemplate().queryForList("SELECT * FROM USER_TABLES WHERE TABLE_NAME ='" + tableName.toUpperCase() + "'");
            if (!list.isEmpty()) {
                StringBuffer alterSql = new StringBuffer();
                alterSql.append(" alter table ").append(tableName).append(
                        " modify (").append(columnName);
                if (type.equals("String")) {
                    alterSql.append(" varchar2").append("(").append(a).append("))");
                } else if (type.equals("Byte")) {
                    alterSql.append(" varchar2(1))");
                } else if (type.equals("ODS") || type.equals("Hadoop")) {
                    alterSql.append(" varchar2(400))");
                } else if (type.equals("Boolean")) {
                    alterSql.append(" number(1,0))");
                } else if (type.equals("Date")) {
                    alterSql.append(" timestamp(6))");
                } else if (type.equals("DateTime")) {
                    alterSql.append(" timestamp)");
                } else if (type.equals("Float") || type.equals("Double")) {
                    alterSql.append(" float)");
                } else if (type.equals("Integer")) {
                    alterSql.append(" number(10,0))");
                } else if (type.equals("BigInteger")) {
                    alterSql.append(" number(19,0))");
                } else if (type.equals("Text") || type.equals("Check")) {
                    StringBuffer dropsql = new StringBuffer();
                    dropsql.append(" alter table ").append(tableName).append(
                            " drop (").append(columnName).append(")");
                    this.getMetadaofactory().getJdbcTemplate().execute(dropsql.toString());
                    StringBuffer aSql = new StringBuffer();
                    aSql.append(" alter table ").append(tableName).append(
                            " add (").append(columnName).append(" clob").append(")");
                    this.getMetadaofactory().getJdbcTemplate().execute(aSql.toString());
                } else if (type.equals("Decimal")) {
                    alterSql.append(" number(30,10))");
                }
                if (!type.equals("Text")) {
                    this.getMetadaofactory().getJdbcTemplate().execute(alterSql.toString());
                }
            }
        } catch (DataAccessException e) {
            resume();
            throw new Exception();
        }
    }

    /**
     * 修改子数据类中该字段的数据类型、长度等信息.
     *
     * @param a          字段长度
     * @param columnName 字段实际名称
     * @param type       字段类型
     * @param tableId    数据类id
     * @throws Exception 异常信息
     */
    private void alterChildTableColumn(final int a, final String columnName, final String type, String tableId) throws Exception {
        try {
            List list = this.getMetadaofactory().getJdbcTemplate()
                    .queryForList("SELECT ID AS a,S_TABLE_NAME AS b FROM CWM_TABLES WHERE PID ='" + tableId + "' AND IS_VALID = 1");
            if (!list.isEmpty()) {
                for (Iterator it = list.iterator(); it.hasNext(); ) {
                    Map map = (Map) it.next();
                    String name = (String) map.get("b");
                    String id = (String) map.get("a");
                    if (name != null) {
                        alter(a, columnName, type, name);
                    }
                    if (id != null) {
                        alterChildTableColumn(a, columnName, type, id);
                    }
                }
            }
        } catch (DataAccessException e) {
            resume();
            throw new Exception();
        } catch (Exception e) {
            resume();
            throw e;
        }
    }

    /**
     * 修改业务表字段的类型和长度等
     *
     * @param schema         数据模型
     * @param alterColumnMap
     * @throws Exception 异常信息
     */
    private void alterColumn(Schema schema, Map<Integer, String> alterColumnMap) throws Exception {
        try {
            status = true;
            List list = this.getMetadaofactory().getJdbcTemplate()
                    .queryForList("SELECT ID,DISPLAY_NAME,S_COLUMN_NAME,TABLE_ID,TYPE,MAX_LENGTH FROM CWM_TAB_COLUMNS WHERE IS_VALID=2");
            if (!list.isEmpty()) {
                verifyExistData(list);
                int i = 0;
                for (Iterator it = list.iterator(); it.hasNext(); i++) {
                    Map map = (Map) it.next();
                    String tableId = (String) map.get("TABLE_ID");
                    int a = ((BigDecimal) map.get("MAX_LENGTH")).intValue() * 2;
                    String columnName = (String) map.get("S_COLUMN_NAME");
                    String oldType = (String) map.get("TYPE");
                    String tableName = (String) ((Map) (this.getMetadaofactory().getJdbcTemplate().queryForList(
                            "SELECT S_TABLE_NAME AS C FROM CWM_TABLES WHERE ID='" + tableId + "' and IS_VALID=1").get(0))).get("c");
                    alter(a, columnName, oldType, tableName);
                    alterChildTableColumn(a, columnName, oldType, tableId);
                    alterColumnMap.put(i, map.get("ID").toString());//记录修改字段的记录
                }
            }
            StringBuffer updateSignSql = new StringBuffer();
            updateSignSql.append("UPDATE CWM_TAB_COLUMNS SET IS_VALID=1 WHERE IS_VALID=2");
            metadaofactory.getJdbcTemplate().execute(updateSignSql.toString());
        } catch (DataAccessException e) {
            e.printStackTrace();
            resume();
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
            resume();
            throw e;
        }
    }

    /**
     * 校验该字段在实际表中是否存在数据，若不存在数据，则可以修改该字段的类型长度等.
     *
     * @param list 要修改的字段属性集合
     * @throws Exception 异常信息
     */
    private void verifyExistData(List list) throws Exception {
        ErrorInfo info = new ErrorInfo();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map map = (Map) it.next();
            String tableId = (String) map.get("TABLE_ID");
            String columnName = (String) map.get("S_COLUMN_NAME");
            String cdn = (String) map.get("DISPLAY_NAME");
            List tableList = metadaofactory.getJdbcTemplate().queryForList(
                    "select S_TABLE_NAME as c ,DISPLAY_NAME as b from CWM_TABLES WHERE ID='"
                            + tableId + "' and IS_VALID=1");
            String tableName = (String) ((Map) tableList.get(0)).get("c");
            String tdn = (String) ((Map) tableList.get(0)).get("c");
            List a = metadaofactory.getJdbcTemplate().queryForList("SELECT * FROM user_tables where table_name='" + tableName.toUpperCase() + "'");
            if (!a.isEmpty()) {
                if (metadaofactory.getJdbcTemplate().queryForObject(
                        "SELECT count(*) A FROM " + tableName + " WHERE " + columnName + " IS NOT NULL", Integer.class) != 0) {
                    info.ErrorId = 1;
                    info.Warning = "数据表'" + tdn + "(" + tableName + ")'的字段'" + cdn + "(" + columnName + ")'含有数据，无法修改该字段的类型或长度，修改保存失败";
                }
            }

            verifyChild(columnName, cdn, tableId);
            if (info.ErrorId != 0) throw new Exception(info.Warning);
        }
        //throw new Exception(info.Warnning);
    }

    /**
     * 校验该字段在子数据类的实际表中是否存在数据.
     *
     * @param columnName 实际字段名称
     * @param cdn        字段显示名
     * @param tableId    父数据类id
     * @throws Exception 异常信息
     */
    private void verifyChild(String columnName, String cdn, String tableId) throws Exception {
        List list = metadaofactory.getJdbcTemplate()
                .queryForList("select ID AS a,S_TABLE_NAME AS b,DISPLAY_NAME AS c FROM CWM_TABLES WHERE PID='" + tableId + "' AND IS_VALID=1");
        if (!list.isEmpty()) {
            for (Iterator it = list.iterator(); it.hasNext(); ) {
                Map map = (Map) it.next();
                String name = (String) map.get("b");
                String tdn = (String) map.get("c");
                String id = (String) map.get("a");
                if (name != null) {
                    List a = metadaofactory.getJdbcTemplate().queryForList("SELECT * FROM user_tables WHERE table_name='" + name.toUpperCase() + "'");
                    if (!a.isEmpty()) {
                        if (metadaofactory.getJdbcTemplate()
                                .queryForObject("SELECT COUNT(*) A FROM " + name + " WHERE " + columnName + " IS NOT NULL", Integer.class) != 0) {
                            String errorMsg = "数据表'" + tdn + "(" + name + ")'的字段'" + cdn + "(" + columnName + ")'含有数据，无法修改该字段的类型或长度，修改保存失败";
                            throw new Exception(errorMsg);
                        }
                    }
                }
                if (id != null) {
                    verifyChild(columnName, cdn, id);
                }
            }
        }
    }

}
