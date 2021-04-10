package com.orient.metamodel.service;

import com.orient.metamodel.DBUtil;
import com.orient.metamodel.metadomain.*;
import com.orient.metamodel.metaengine.ErrorInfo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 保存table的普通属性，关系属性，参数关联约束
 * Created by GNY on 2018/3/27
 */
@Service
public class SaveTableColumnService extends DBUtil {

    /**
     * 数据类的数据字段普通属性、关系属性、统计属性、动态关联约束保存
     *
     * @param schema
     * @param columnMap
     * @param restrictionRefColumnsMap
     */
    public void saveTableColumns(Schema schema, Map<String, Column> columnMap, Map<String, List<Column>> restrictionRefColumnsMap) throws Exception {
        for (Table table : schema.getTables()) {
            saveTableColumn(table, columnMap, restrictionRefColumnsMap);
        }
    }

    public void saveTableColumn(Table table, Map<String, Column> columnMap, Map<String, List<Column>> restrictionRefColumnsMap) throws Exception {
        saveTableAttribute(table, columnMap, restrictionRefColumnsMap);
        saveTableRelationAttribute(table);          //保存关系属性CWM_RELATION_COLUMNS表
        saveArithColumnAttributeForTable(table);    //保存统计属性中算法用到的参数
        saveTableMultiAttribute(table);             //保存数据类的复合属性（排序属性，主键显示值...）
        if (table.getChildTables().size() != 0) {
            saveChildTableAttribute(table, columnMap, restrictionRefColumnsMap);
        }
    }

    /**
     * 数据类的数据字段普通属性、关系属性、统计属性、动态关联约束保存
     *
     * @param table
     * @param columnMap
     */
    private void saveTableAttribute(Table table, Map<String, Column> columnMap, Map<String, List<Column>> restrictionRefColumnsMap) {
        try {
            // 遍历该数据类的普通属性、统计属性、关系属性
            for (Column column : table.getCwmTabColumnses()) {
                if (column.getPurpose() == null || column.getPurpose().isEmpty()) {
                    column.setPurpose("新增,修改,详细,列表");
                }
                setColumnName(column, table);
                columnMap.put(column.getIdentity(), column);
                saveColumn(column, restrictionRefColumnsMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //参数关联约束
        for (ConsExpression consExpression : table.getCwmConsExpressions()) {
            metaDAOFactory.getConsExpressionDAO().save(consExpression);
        }
    }

    /**
     * 引用表的字段属性的实际字段名是固定表或系统表的字段的实际名.
     *
     * @param column --字段
     * @param table  --字段所在的表
     * @return void
     * @throws
     */
    public void setColumnName(Column column, Table table) {
        String cName = column.getName();
        column.setName(cName);
        String columnName = column.getName() + "_" + table.getId();
        if (column.getMapColumn() != null && !column.getMapColumn().equals("")) {
            if (!column.getMapColumn().matches("^-?\\d+$")) {
                //系统字段，就用系统字段
                column.setColumnName(column.getMapColumn());
            } else if (column.getMapColumn().matches("^-?\\d+$")) {//(全部是数字)
                //共享表的字段
                List<Map<String, Object>> list = metaDAOFactory.getJdbcTemplate().queryForList("SELECT S_COLUMN_NAME FROM CWM_TAB_COLUMNS WHERE ID='" + column.getMapColumn() + "'");
                if (!list.isEmpty()) {
                    column.setColumnName((String) list.get(0).get("S_COLUMN_NAME"));
                } else {
                    column.setColumnName(columnName);
                }
            }
        } else {
            column.setColumnName(columnName);
        }
    }

    /**
     * 普通属性、统计属性的保存. 创建了自增字段的sequence
     *
     * @param column 普通属性
     * @return void
     * @throws Exception
     */
    public void saveColumn(Column column, Map<String, List<Column>> restrictionRefColumnsMap) throws Exception {
        //如果不是映射字段，则此处也不建立新的sequence
        if (column.getMapColumn() == null || column.getMapColumn().trim().length() == 0) {
            //如果是自增型属性
            if (column.getIsAutoIncrement() != null && column.getIsAutoIncrement().equals("True")) {
                String seqname = column.getColumnName() + "_seq";
                int i = metaDAOFactory.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM ALL_SEQUENCES WHERE SEQUENCE_NAME='" + seqname.toUpperCase() + "' AND SEQUENCE_OWNER='" + getTsn() + "'", Integer.class);
                if (i == 0) {    //如果sequence没有创建，则创建新的sequence
                    int value = 1;
                    int interval = 1;
                    if (column.getAutoAddDefault() != null) {
                        value = column.getAutoAddDefault().intValue();
                    }
                    if (column.getSeqInterval() != null) {
                        interval = column.getSeqInterval().intValue();
                    }
                    metaDAOFactory.getJdbcTemplate().execute("CREATE SEQUENCE " + seqname + " INCREMENT BY " + interval + " START WITH " + value + " NOMAXVALUE NOCACHE");
                }
            }
        }

        // 查看该数据类是否有数据约束
        if (column.getRestriction() != null) {
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
        metaDAOFactory.getColumnDAO().save(column);// 保存普通属性和统计属性及关系属性
    }

    /**
     * 关系属性详细信息保存.(修改已经建好的关系属性表)  这个函数似乎没有用
     *
     * @param table
     */
    private void saveTableRelationAttribute(Table table) throws Exception {

        for (Column column : table.getCwmTabColumnses()) {   //遍历column集合，找出所有的关系属性
            if (column.getCategory().equals(Column.CATEGORY_RELATION)) {    //关系属性
                for (RelationColumns relationColumn : table.getCwmRelationColumnses()) {
                    if (relationColumn.getCwmTabColumnsByColumnId().equals(column)) {
                        column.setRelationColumn(relationColumn);
                        metaDAOFactory.getRelationColumnsDAO().save(relationColumn);
                    }
                }
            }
        }
    }

    /**
     * 数据类的统计属性的算法的参数保存
     *
     * @param table
     */
    private void saveArithColumnAttributeForTable(Table table) throws Exception {
        for (Column column : table.getCwmTabColumnses()) {
            if (!column.getArithAttribute().isEmpty()) {
                //遍历算法的参数
                for (ArithAttribute arithAttribute : column.getArithAttribute()) {
                    arithAttribute.setAcolumn(column);    //设置引用该算法的字段
                    metaDAOFactory.getArithAttributeDAO().save(arithAttribute);
                }
            }
        }
    }

    /**
     * 保存数据类的组合唯一性约束、排序属性、主键显示值以及数据展现顺序
     *
     * @param table 数据类
     */
    public void saveTableMultiAttribute(Table table) throws Exception {
        Set<TableColumn> tableColumns = new HashSet<>();

        if (!table.getPkColumns().isEmpty()) {
            // 遍历数据类的主键显示值并保存
            for (int loop = 0; loop < table.getPkColumns().size(); loop++) {
                TableColumn pkColumn = new TableColumn();
                pkColumn.setTable(table);
                pkColumn.setColumn((Column) table.getPkColumns().get(loop));
                pkColumn.setOrder(new Long(loop));
                pkColumn.setType(0L);
                tableColumns.add(pkColumn);
                metaDAOFactory.getTableColumnDAO().save(pkColumn);// 保存主键显示值
            }
        }
        if (!table.getUkColumns().isEmpty()) {
            // 遍历数据类的组合唯一性约束并保存
            for (int loop = 0; loop < table.getUkColumns().size(); loop++) {
                TableColumn ukColumn = new TableColumn();
                ukColumn.setTable(table);
                ukColumn.setColumn((Column) table.getUkColumns().get(loop));
                ukColumn.setOrder(new Long(loop));
                ukColumn.setType(1L);
                tableColumns.add(ukColumn);
                metaDAOFactory.getTableColumnDAO().save(ukColumn);// 保存组合唯一性约束
            }
        }
        if (!table.getSkColumns().isEmpty()) {
            // 遍历数据类的排序属性并保存
            for (int loop = 0; loop < table.getSkColumns().size(); loop++) {
                TableColumn skColumn = new TableColumn();
                skColumn.setTable(table);
                skColumn.setColumn((Column) table.getSkColumns().get(loop));
                skColumn.setOrder(new Long(loop));
                skColumn.setType(2L);
                tableColumns.add(skColumn);
                metaDAOFactory.getTableColumnDAO().save(skColumn);// 保存排序属性
            }
        }
        if (!table.getZxColumns().isEmpty()) {
            // 遍历数据类的属性展现顺序属性并保存
            for (int loop = 0; loop < table.getZxColumns().size(); loop++) {
                TableColumn zxColumn = new TableColumn();
                zxColumn.setTable(table);
                zxColumn.setColumn((Column) table.getZxColumns().get(loop));
                zxColumn.setOrder(new Long(loop));
                zxColumn.setType(3L);
                tableColumns.add(zxColumn);
                metaDAOFactory.getTableColumnDAO().save(zxColumn);// 保存属性展现顺序属性
            }
        }
        table.setTableColumnSet(tableColumns);
    }

    /**
     * 子数据类的数据字段属性的保存.
     *
     * @param table 父数据类
     * @return void
     * @throws Exception
     */
    private void saveChildTableAttribute(Table table, Map<String, Column> columnMap, Map<String, List<Column>> restrictionRefColumnsMap) throws Exception {
        for (Table childTable : table.getChildTables()) {
            saveTableAttribute(childTable, columnMap, restrictionRefColumnsMap);          //保存数据类的普通属性，其中普通属性用到的Sequence和用到的约束
            saveTableRelationAttribute(childTable);                                       //保存关系属性
            saveArithColumnAttributeForTable(childTable);                                 //保存统计属性中算法用到的参数
            saveTableMultiAttribute(childTable);                                          //保存数据类的复合属性（排序属性，主键显示值...）
            if (childTable.getChildTables().size() != 0) {
                saveChildTableAttribute(childTable, columnMap, restrictionRefColumnsMap); //递归的保存子数据类的字段属性
            }
        }
    }

}
