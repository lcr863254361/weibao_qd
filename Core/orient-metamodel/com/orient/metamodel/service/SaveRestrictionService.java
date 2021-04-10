package com.orient.metamodel.service;

import com.orient.metamodel.DBUtil;
import com.orient.metamodel.metadomain.*;
import com.orient.metamodel.metadomain.Enum;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-28 10:03
 */
@Service
public class SaveRestrictionService extends DBUtil {

    public void saveRestrictions(Schema schema, Map<String, Column> columnMap) {
        schema.getRestrictions().forEach(restriction -> saveRestriction(restriction, columnMap));
    }

    public void modifyRestrictionColumns(Schema schema, Map<String, List<Column>> restrictionRefColumnsMap) throws Exception {

        for (String identity : restrictionRefColumnsMap.keySet()) {
            Restriction restriction = getRestrictionByIdentity(identity, schema);
            if (restriction == null) {
                throw new Exception("普通属性引用的约束没有找到！");
            }
            List<Column> allColumns = restrictionRefColumnsMap.get(identity);
            for (Column loopColumn : allColumns) {
                loopColumn.setRestriction(restriction);
                metaDAOFactory.getColumnDAO().merge(loopColumn);
            }
        }
    }

    private Restriction getRestrictionByIdentity(String identity, Schema schema) {
        for (Restriction restriction : schema.getRestrictions()) {
            if (restriction.getIdentity().equalsIgnoreCase(identity)) {
                return restriction;
            }
        }
        return null;
    }

    public void saveRestriction(Restriction restriction, Map<String, Column> columnMap) {
        try {
            metaDAOFactory.getRestrictionDAO().save(restriction);
            saveRestrictionDetail(restriction, columnMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存数据约束公共属性的其他属性，如枚举约束的枚举值，表枚举的枚举值所在数据类等.
     *
     * @param restriction 数据约束对象
     * @throws Exception 异常信息
     */
    private void saveRestrictionDetail(Restriction restriction, Map<String, Column> columnMap) throws Exception {

        // 判断是否为枚举约束，1表示为枚举约束，2表示为数据表枚举约束，3表示为范围约束，4表示动态范围约束
        if (restriction.getType().intValue() == Restriction.TYPE_ENUM) {
            for (Enum e : restriction.getCwmEnums()) {
                e.setRestrictionID(restriction.getId());
                metaDAOFactory.getEnumDAO().save(e);// 保存约束值
            }
        } else if (restriction.getType().intValue() == Restriction.TYPE_TABLEENUM) {// 数据表枚举约束
            TableEnum te = restriction.getTableEnum();
            te.setRestrictionId(restriction.getId());
            saveTableEnumDetail(te, columnMap);
        } else if (restriction.getType().intValue() == Restriction.TYPE_DYNAMICRANGEENUM) {//动态范围约束
            TableEnum te = restriction.getTableEnum();
            te.setRestrictionId(restriction.getId());
            metaDAOFactory.getTableEnumDAO().save(te);
        }
    }

    /**
     * 数据表枚举约束的关联数据类以及过滤表达式的保存.
     *
     * @param te --数据表枚举约束对象
     * @return void
     * @throws Exception
     */
    public void saveTableEnumDetail(TableEnum te, Map<String, Column> columnMap) throws Exception {

        //过滤表达式
        String expression = te.getExpression();
        if (expression != null && !expression.isEmpty()) {

            String newExpression = convertColumnIdentity2ColumnName(expression, columnMap);
            te.setExpression(newExpression);
        }
        //关联数据类的关系字符串
        StringBuffer relation = new StringBuffer();
        Boolean bl = false;
        for (Iterator relationIt = te.getTabledetailSet().iterator(); relationIt.hasNext(); ) {
            RelationDetail rd = (RelationDetail) relationIt.next();
            if (rd.getType().equals("0")) {
                //type为0,表示数据类A和数据类B之间是A.id=B.A_id;(A是leftTable，B是rightTable) A为1，B为多
                relation.append(" and ")
                        .append(rd.getFromTable().getTableName())
                        .append(".ID=")
                        .append(rd.getToTable().getTableName())
                        .append(".")
                        .append(rd.getRfromTable().getTableName())
                        .append("_ID");
            } else if (rd.getType().equals("1")) {
                //type为1,表示数据类A和数据类B之间是A.B_id=B.id; B为1，A为多
                relation.append(" and ")
                        .append(rd.getToTable().getTableName())
                        .append(".ID=")
                        .append(rd.getFromTable().getTableName())
                        .append(".")
                        .append(rd.getRtoTable().getTableName())
                        .append("_ID");
            } else if (rd.getType().equals("2")) {
                //type为2,表示两个数据类(A,B)之间的关系是多对多
                //c1.main_table_name = A.NAME and c1.main_data_id=A.id and c1.sub_table_name = B.NAME and c1.sub_data_id = B.ID
                bl = true;
                relation.append(" and ")
                        .append("CWM_RELATION_DATA.MAIN_TABLE_NAME='")
                        .append(rd.getToTable().getTableName())
                        .append("' and ")
                        .append("CWM_RELATION_DATA.MAIN_DATA_ID=")
                        .append(rd.getToTable().getTableName()).append(".ID")
                        .append(" and ")
                        .append("CWM_RELATION_DATA.SUB_TABLE_NAME='")
                        .append(rd.getFromTable().getTableName())
                        .append("' and ")
                        .append("CWM_RELATION_DATA.SUB_DATA_ID=")
                        .append(rd.getFromTable().getTableName())
                        .append(".ID");
            } else if (rd.getType().equals("3")) {
                //和2相反
                bl = true;
                relation.append(" and ")
                        .append("CWM_RELATION_DATA.MAIN_TABLE_NAME='")
                        .append(rd.getFromTable().getTableName())
                        .append("' and ")
                        .append("CWM_RELATION_DATA.MAIN_DATA_ID=")
                        .append(rd.getFromTable().getTableName())
                        .append(".ID")
                        .append(" and ")
                        .append("CWM_RELATION_DATA.SUB_TABLE_NAME='")
                        .append(rd.getToTable().getTableName())
                        .append("' and ")
                        .append("CWM_RELATION_DATA.SUB_DATA_ID=")
                        .append(rd.getToTable().getTableName())
                        .append(".ID");
            }

        }
        StringBuffer tableList = new StringBuffer();
        for (int c = 0; c < te.getCwmRelationTableEnums().size(); c++) {
            Table table = (Table) te.getCwmRelationTableEnums().get(c);
            tableList.append(table.getTableName()).append(",");
        }
        if (bl) {
            tableList.append("CWM_RELATION_DATA,");
        }
        String tableStr = tableList.toString();
        StringBuffer columnname = new StringBuffer();
        if (te.getColumn().getType().equals("Date")) {
            columnname.append("to_char(")
                    .append(te.getColumn().getColumnName())
                    .append(",'yyyy-mm-dd') ")
                    .append(te.getColumn().getColumnName());
        } else if (te.getColumn().getType().equals("DateTime")) {
            columnname.append("to_char(")
                    .append(te.getColumn().getColumnName())
                    .append(",'yyyy-mm-dd hh24:mi:ss') ")
                    .append(te.getColumn().getColumnName());
        } else if (te.getColumn().getType().equals("Boolean")) {
            columnname.append("decode(")
                    .append(te.getColumn().getColumnName())
                    .append(",'0','False','1','True') ")
                    .append(te.getColumn().getColumnName());
        } else {
            columnname.append(te.getColumn().getColumnName());
        }
        StringBuffer tableEnumSql = new StringBuffer();
        tableEnumSql.append("select distinct ")
                .append(columnname.toString()).append(" from ")
                .append(tableStr.substring(0, tableStr.length() - 1));
        if (expression != null && expression.length() > 0) {
            tableEnumSql.append(" where ").append(expression);
            if (relation.length() != 0) {
                tableEnumSql.append(relation.toString());
            }
        }
        te.setTableEnumSql(tableEnumSql.toString());
        //根据RelationTableEnums中存储的table,创建RelationTableEnum实例，并保存至数据库
        metaDAOFactory.getTableEnumDAO().save(te);
        Set<RelationTableEnum> rets = new HashSet<>();
        for (int i = 0; i < te.getCwmRelationTableEnums().size(); i++) {
            RelationTableEnum rte = new RelationTableEnum();
            Table rtable = (Table) te.getCwmRelationTableEnums().get(i);
            Table originTable = null;
            Table fromTable = null;
            Table toTable = null;
            String type = null;
            for (Iterator m = te.getTabledetailSet().iterator(); m.hasNext(); ) {
                RelationDetail rd = (RelationDetail) m.next();
                if (rd.getToTable().equals(rtable)) {
                    originTable = rd.getFromTable();
                    fromTable = rd.getRfromTable();
                    toTable = rd.getRtoTable();
                    type = rd.getType();
                    break;
                }
            }
            rte.setOriginTable(originTable);
            rte.setFromtable(fromTable);
            rte.setTotable(toTable);
            rte.setType(type);
            rte.setCwmTableEnum(te);
            rte.setCwmTables(rtable);
            rte.setOrder(new Long(i));
            metaDAOFactory.getRelationTableEnumDAO().save(rte);// 保存数据表枚举约束的关联数据类
            rets.add(rte);
        }
        te.setRelationTableEnums(rets);
    }

    /**
     * 将xml中描述的column转化为数据库的字段名称
     *
     * @param containIdentity --要替换的字符串		//[columnIdentity] 运算符 字段值 逻辑运算符
     * @param columnMap       --目前所有的数据库字段
     * @return String                --替换过后的字符串		//[CWM_TAB_COLUMN中的S_NAME] 运算符 字段值 逻辑运算符
     * @throws
     */
    private String convertColumnIdentity2ColumnName(String containIdentity, Map<String, Column> columnMap) {
        Iterator itor = columnMap.keySet().iterator();
        while (itor.hasNext()) {
            String columnIdentity = (String) itor.next();
            Column loopColumn = columnMap.get(columnIdentity);
            //得到业务表中的字段名称
            if (containIdentity.indexOf("[" + columnIdentity + "]") >= 0) {
                if (loopColumn.getCategory().equals(new Long(1))) {
                    //普通属性
                    containIdentity = containIdentity.replaceAll("\\[" + columnIdentity + "\\]", (String) loopColumn.getColumnName());
                } else {
                    //目前不能选择关系属性作为过滤表达式的字段。貌似不会跑到这里
                    //关系属性或者统计属性
                    //关系属性的字段为  关系表名称_ID  先得到关系表名称，再拼接
                    String refTableName = (String) ((Map) metaDAOFactory.getJdbcTemplate().
                            queryForList("select S_TABLE_NAME from CWM_TABLES where id=(select REF_TABLE_ID from CWM_RELATION_COLUMNS where COLUMN_ID='" + loopColumn.getId() + "')")
                            .get(0)).get("S_TABLE_NAME");
                    containIdentity = containIdentity.replaceAll("\\[" + columnIdentity + "\\]", loopColumn.getTable().getTableName() + "." + refTableName + "_ID");
                }
            }
        }
        return containIdentity;
    }

}
