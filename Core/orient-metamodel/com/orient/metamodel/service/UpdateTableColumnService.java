package com.orient.metamodel.service;

import com.orient.metamodel.DBUtil;
import com.orient.metamodel.metadomain.*;
import com.orient.metamodel.metaengine.ErrorInfo;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-04-02 20:35
 */
@Service
public class UpdateTableColumnService extends DBUtil {

    @Autowired
    SaveTableColumnService saveTableColumnService;

    /**
     * 数据类的数据字段普通属性、关系属性、统计属性、动态关联约束保存
     *
     * @param newSchema
     * @param oldSchema
     * @param columnMap
     * @param restrictionRefColumnsMap
     */
    public void updateTableColumns(Schema newSchema, Schema oldSchema, Map<String, Column> columnMap, Map<String, List<Column>> restrictionRefColumnsMap, Map<String, String> alterInfo, Set<String> updateTableList) throws Exception {
        for (Table table : newSchema.getTables()) {
            Table oldTable = (Table) oldSchema.getTableById(table.getId());
            if (oldTable == null) { //如果内存中没有这个table记录，则创建并保存table的字段属性
                saveTableColumnService.saveTableColumn(table, columnMap, restrictionRefColumnsMap);
            } else {     //更新数据类的普通属性和关系属性和参数关联约束
                updateTableColumn(table, oldTable, columnMap, restrictionRefColumnsMap, alterInfo, updateTableList);
            }
        }
    }

    /**
     * 1.更新数据类的普通属性和关系属性和参数关联约束
     * 2.遍历表和子表，更新字段
     *
     * @param table
     * @param columnMap
     * @param restrictionRefColumnsMap @throws Exception
     * @param alterInfo
     */
    private void updateTableColumn(Table table, Table oldTable, Map<String, Column> columnMap, Map<String, List<Column>> restrictionRefColumnsMap, Map<String, String> alterInfo, Set<String> updateTableList) throws Exception {
        updateColumn(table, oldTable, columnMap, restrictionRefColumnsMap, alterInfo, updateTableList);
        updateRelationColumn(table, oldTable);//更新关系属性和统计属性
        updateMultiAttribute(table, oldTable);//更新复合属性
        //遍历子表，进行同样的处理
        for (Table childTable : table.getChildTables()) {
            updateTableColumn(childTable, oldTable, columnMap, restrictionRefColumnsMap, alterInfo, updateTableList);
        }
        metaDAOFactory.getTableDAO().merge(table);
    }

    /**
     * 更新普通属性、关系属性、参数关联约束.
     *
     * @param table     数据类对象
     * @param columnMap
     * @param alterInfo
     * @throws Exception
     */
    private void updateColumn(Table table, Table oldTable, Map<String, Column> columnMap, Map<String, List<Column>> restrictionRefColumnsMap, Map<String, String> alterInfo, Set<String> updateTableList) throws Exception {
        //如果有删除的字段 删除物理字段
        List<String> latestColumnNames = new ArrayList<>();
        for (Column column : table.getCwmTabColumnses()) {
            latestColumnNames.add(column.getName());
            Column oldColumn = (Column) oldTable.getColumnByName(column.getName());
            if (oldColumn == null) {  //新增的Column
                saveTableColumnService.setColumnName(column, table);
                columnMap.put(column.getIdentity(), column);
                saveTableColumnService.saveColumn(column, restrictionRefColumnsMap);
            } else {                 //旧的Column
                column.setId(oldColumn.getId());
                saveTableColumnService.setColumnName(column, table);
                //如果列显示名改了，记录表名到更新表集合中
                if (!oldColumn.getDisplayName().equals(column.getDisplayName())) {
                    updateTableList.add(oldTable.getTableName());
                }
                if (column.getType() != null && column.getMaxLength() != null) {//数据类型发生改变
                    if (!column.getType().equals(oldColumn.getType()) || !column.getMaxLength().equals(oldColumn.getMaxLength())) {
                        alterInfo.put(oldColumn.getId(), oldColumn.getType() + "==" + oldColumn.getMaxLength());
                        column.setIsValid(Column.ISVAILD_MODIFY);
                        //如果列类型和最大长度修改了，记录表名到更新表集合中
                        updateTableList.add(oldTable.getTableName());
                    }
                }
                columnMap.put(column.getIdentity(), column);

                //如果不是映射字段，则需要查看是否需要建立新的Sequence或者是否需要删除Seq
                if (column.getMapColumn() == null || column.getMapColumn().trim().length() == 0) {
                    if (column.getIsAutoIncrement() != null && column.getIsAutoIncrement().equals("True")) {//修改的普通属性为自增属性
                        String name = column.getColumnName() + "_seq";
                        int i = metaDAOFactory.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM ALL_SEQUENCES WHERE SEQUENCE_NAME='"
                                + name.toUpperCase() + "' AND SEQUENCE_OWNER='" + getTsn() + "'", Integer.class);
                        if (i == 0) {    //没有建立
                            int value = 1;
                            int interval = 1;
                            if (column.getAutoAddDefault() != null) {
                                value = column.getAutoAddDefault().intValue();
                            }
                            if (column.getSeqInterval() != null) {
                                interval = column.getSeqInterval().intValue();
                            }
                            metaDAOFactory.getJdbcTemplate().execute("CREATE SEQUENCE " + name + " INCREMENT BY " + interval + " START WITH " + value + " NOMAXVALUE NOCACHE");
                        }
                    } else if (oldColumn.getIsAutoIncrement() != null && oldColumn.getIsAutoIncrement().equals("True") && column.getIsAutoIncrement().equals("FALSE")) {//将自增属性修改为非自增属性时删除该字段建立的序列
                        String seqName = oldColumn.getColumnName() + "_seq";
                        int i = metaDAOFactory.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM ALL_SEQUENCES WHERE SEQUENCE_NAME='" + seqName.toUpperCase() + "' AND SEQUENCE_OWNER='" + getTsn() + "'", Integer.class);
                        if (i != 0) {
                            metaDAOFactory.getJdbcTemplate().execute("DROP SEQUENCE " + seqName);
                        }
                    }
                    if (column.getRestriction() != null) {
                        // 查看该数据类是否有数据约束
                        String resIdentity = column.getRestriction().getIdentity();
                        List<Column> columnList = restrictionRefColumnsMap.get(resIdentity);
                        if (columnList == null) {
                            columnList = new ArrayList<>();
                            columnList.add(column);
                        } else {
                            columnList.add(column);
                        }
                        restrictionRefColumnsMap.put(resIdentity, columnList);
                        column.setRestriction(null);
                    }
                }
            }
        }

        Predicate<Column> matched = column -> !latestColumnNames.contains(column.getName());
        List<Column> toDelColumns = oldTable.getCwmTabColumnses().stream().filter(matched).collect(Collectors.toList());
        toDelColumns.forEach(toDelColumn -> dropLogicColumn(toDelColumn, oldTable.getTableName()));
        //保存关联约束
        List ceList = metaDAOFactory.getConsExpressionDAO().findByTableId(oldTable);//取得数据库中该数据类的所有参数关联约束
        if (ceList.size() != 0) {
            for (Iterator ceIt = ceList.iterator(); ceIt.hasNext(); ) {
                ConsExpression consExpression = (ConsExpression) ceIt.next();
                metaDAOFactory.getConsExpressionDAO().delete(consExpression);       //删除数据库中该数据类的所有参数关联约束
            }
        }
        for (ConsExpression consExpression : table.getCwmConsExpressions()) {
            metaDAOFactory.getConsExpressionDAO().save(consExpression);           //保存从模型中得到的该数据类的参数关联约束
        }
    }

    /**
     * 更新关系属性
     *
     * @param table 数据类对象
     * @throws Exception 异常信息
     */
    private void updateRelationColumn(Table table, Table oldTable) throws Exception {
        for (Column column : table.getCwmTabColumnses()) {
            if (column.getCategory().equals(Column.CATEGORY_RELATION)) { // 该字段是关系属性字段
                Column oldColumn = (Column) oldTable.getColumnById(column.getId());
                if (oldColumn == null) {//该Column是新增的Column,则关系属性的详细信息也要添加
                    for (RelationColumns relationColumn : table.getCwmRelationColumnses()) {
                        if (relationColumn.getCwmTabColumnsByColumnId().equals(column)) {
                            column.setRelationColumn(relationColumn);
                            metaDAOFactory.getRelationColumnsDAO().save(relationColumn);// 保存关系属性
                        }
                    }
                } else {
                    RelationColumns oldRelationColumn = oldColumn.getRelationColumn();
                    for (RelationColumns relationColumn : table.getCwmRelationColumnses()) {
                        if (relationColumn.getCwmTabColumnsByColumnId().equals(column)) {
                            column.setRelationColumn(relationColumn);
                            relationColumn.setId(oldRelationColumn.getId());
                            metaDAOFactory.getRelationColumnsDAO().merge(relationColumn);// 更新关系属性
                        }
                    }
                }
            } else if (column.getCategory().equals(Column.CATEGORY_ARITH)) {
                // 该字段是统计属性字段
                Column oldColumn = (Column) oldTable.getColumnById(column.getId());
                if (oldColumn == null) {
                    //该Column是新增的Column,则统计属性的详细信息也要添加
                    for (ArithAttribute arithAttribute : column.getArithAttribute()) {
                        arithAttribute.setAcolumn(column);
                        metaDAOFactory.getArithAttributeDAO().save(arithAttribute);
                    }
                } else {
                    //先清除老的算法属性再重新添加
                    for (ArithAttribute oldArithAttribute : oldColumn.getArithAttribute()) {
                        metaDAOFactory.getArithAttributeDAO().delete(oldArithAttribute);
                    }
                    for (ArithAttribute newArithAttribute : column.getArithAttribute()) {
                        newArithAttribute.setAcolumn(column);
                        metaDAOFactory.getArithAttributeDAO().save(newArithAttribute);
                    }
                }
            }
            metaDAOFactory.getColumnDAO().merge(column);
        }
    }

    /**
     * 更新数据类的复合属性，包括主键显示值、唯一性约束、排序属性、属性展现顺序.
     *
     * @param table
     * @param oldTable
     * @throws Exception
     */
    private void updateMultiAttribute(Table table, Table oldTable) throws Exception {
        List kList = metaDAOFactory.getTableColumnDAO().findByTableId(table);//删除所有的主键、唯一性约束和排序属性
        if (!kList.isEmpty()) {
            for (Iterator kIt = kList.iterator(); kIt.hasNext(); ) {
                TableColumn kColumn = (TableColumn) kIt.next();
                metaDAOFactory.getTableColumnDAO().delete(kColumn);
            }
        }
        //重新保存所有的主键、唯一性约束和排序属性
        saveTableColumnService.saveTableMultiAttribute(table);
    }

    /**
     * @param toDelColumn
     * @param tableName   删除物理字段
     */
    private void dropLogicColumn(Column toDelColumn, String tableName) {
        StringBuffer dropSql = new StringBuffer();
        //先判断是否为关系属性
        if (toDelColumn.getCite().contains("关系属性")) {
            //然后删除物理字段前，先查找表中是否有相应的字段名
            dropSql.append(" select column_name from user_tab_columns where table_name='")
                    .append(tableName)
                    .append("' ");
            //获取该表中的所有字段名称
            List<Map<String, Object>> columnNameMaps = metaDAOFactory.getJdbcTemplate().queryForList(dropSql.toString());
            boolean isContainsValue = false;
            for (Map<String, Object> columnNameMap : columnNameMaps) {
                //遍历结果,查看是否有物理字段存在
                if (columnNameMap.containsValue(toDelColumn.getColumnName())) {
                    isContainsValue = true;
                }
            }
            if (!isContainsValue) {
                //如果不存在，则不进行删除操作
                return;
            }
        }
        dropSql = new StringBuffer();
        dropSql.append(" alter table ")
                .append(tableName)
                .append(" drop (")
                .append(toDelColumn.getColumnName())
                .append(")");
        metaDAOFactory.getJdbcTemplate().execute(dropSql.toString());
    }

    /**
     * 判断Table的Column是否有修改
     *
     * @param table
     * @return ErrorInfo
     */
    public ErrorInfo verifyTableColumn(Table table) {
        ErrorInfo info = new ErrorInfo();
        if (table.getChildTables().size() != 0) {
            Set<Table> childTables = table.getChildTables();
            for (Table childTable : childTables) {
                info = verifyTableColumn(childTable);
                if (info.ErrorId != 0) {
                    return info;
                }
            }
        }
        for (Column column : table.getCwmTabColumnses()) {
            if (column.getPropertyCategory() != null && !column.getPropertyCategory().equals("0")) {
                if (column.getCategory().equals(Column.CATEGORY_COMMON)) {
                    info = verifyDetail(column);
                    if (info.ErrorId != 0) {
                        return info;
                    }
                }
            }
        }
        return info;
    }

    /**
     * 确认column的修改是否符合要求
     *
     * @param tabColumn
     * @return ErrorInfo 1: 有了数据不能设置为全文检索  2：无法修改数据类型  3：String数据的长度无法修改
     */
    private ErrorInfo verifyDetail(Column tabColumn) {

        ErrorInfo info = new ErrorInfo();
        Schema newSchema = tabColumn.getTable().getSchema();
        try {
            String sql = "SELECT ID,S_COLUMN_NAME,DISPLAY_NAME,S_NAME,TABLE_ID FROM CWM_TAB_COLUMNS "
                    + "WHERE S_NAME='" + tabColumn.getName() + "' AND TABLE_ID IN (SELECT ID FROM CWM_TABLES WHERE SCHEMA_ID='" + newSchema.getId()
                    + "' AND S_NAME='" + tabColumn.getTable().getName() + "') AND CATEGORY='" + Column.CATEGORY_COMMON + "' AND IS_VALID='1'";

            List<Map<String, Object>> dbColumnRecordList = metaDAOFactory.getJdbcTemplate().queryForList(sql);
            List<Column> dbColumnList = new ArrayList<>();
            if (!dbColumnRecordList.isEmpty()) {
                for (Map record : dbColumnRecordList) {
                    String columnId = (String) record.get("ID");
                    Column dbColumn = metaDAOFactory.getColumnDAO().findById(columnId);
                    if (dbColumn != null) {
                        dbColumnList.add(dbColumn);
                    }
                }
            }
            for (Column dbColumn : dbColumnList) {
                if (dbColumn.getIsValid().equals(Column.ISVAILD_VALID)) {
                    Table dbTable = dbColumn.getTable();
                    //查询业务表是否已经有数据
                    String SQL = "SELECT COUNT (*) A FROM " + dbTable.getTableName() + " WHERE " + dbColumn.getColumnName() + " IS NOT NULL";
                    if (metaDAOFactory.getJdbcTemplate().queryForObject(SQL, Integer.class) != 0) { //已经有数据
                        if (tabColumn.getPropertyCategory().equals("1")) {
                            info.Warning = "数据表'" + dbTable.getDisplayName() + "(" + dbTable.getName() + ")'的字段'" + dbColumn.getDisplayName() + "(" + dbColumn.getName() + ")'含有数据，无法修改该字段为全文检索属性，请重新从数据库打开数据模型编辑并保存";
                            info.ErrorId = 1;
                            return info;
                        } else if (tabColumn.getPropertyCategory().equals("2")) {
                            info.Warning = "数据表'" + dbTable.getDisplayName() + "(" + dbTable.getName() + ")'的字段'" + dbColumn.getDisplayName() + "(" + dbColumn.getName() + ")'含有数据，无法修改该字段的数据类型，请重新从数据库打开数据模型编辑并保存";
                            info.ErrorId = 2;
                            return info;
                        } else if (tabColumn.getPropertyCategory().equals("3") && tabColumn.getType().equals("String")) {
                            info.Warning = "数据表'" + dbTable.getDisplayName() + "(" + dbTable.getName() + ")'的字段'" + dbColumn.getDisplayName() + "(" + dbColumn.getName() + ")'含有数据，无法修改该字符型字段的长度，请重新从数据库打开数据模型编辑并保存";
                            info.ErrorId = 3;
                            return info;
                        }
                    }
                }
            }
        } catch (Exception e) {
            info.ErrorId = -1;
            info.Warning = e.toString();
            return info;
        }
        return info;
    }

}
