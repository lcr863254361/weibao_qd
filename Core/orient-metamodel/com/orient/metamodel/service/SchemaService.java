package com.orient.metamodel.service;

import com.orient.metamodel.DBUtil;
import com.orient.metamodel.metadomain.*;
import com.orient.metamodel.metaengine.ErrorInfo;
import com.orient.metamodel.metaengine.business.DynamicTableUtil;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.utils.CommonTools;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-28 15:32
 */
@Component
public class SchemaService extends DBUtil {

    /**
     * 将所有与schema相关的数据类、数据类字段属性、数据约束、数据视图设置为无效（就是假删除）
     *
     * @param oldSchema
     * @return ErrorInfo
     */
    public ErrorInfo setOldSchemaInvalid(Schema oldSchema) {
        ErrorInfo info = new ErrorInfo();
        try {
            // 数据类的ISVALID字段设为无效
            StringBuffer updateTableValidSQL = new StringBuffer();
            updateTableValidSQL.append("UPDATE CWM_TABLES SET ")
                    .append("IS_VALID = 0")
                    .append(" WHERE SCHEMA_ID ='")
                    .append(oldSchema.getId())
                    .append("' AND IS_VALID = 1");
            metaDAOFactory.getJdbcTemplate().update(updateTableValidSQL.toString());
            // 数据字段的ISVALID字段设为无效
            StringBuffer updateColumnValidSQL = new StringBuffer();
            updateColumnValidSQL.append("UPDATE CWM_TAB_COLUMNS SET ")
                    .append("IS_VALID = 0")
                    .append(" WHERE TABLE_ID IN (SELECT ID FROM CWM_TABLES WHERE SCHEMA_ID ='")
                    .append(oldSchema.getId())
                    .append("') AND IS_VALID ='1'");
            metaDAOFactory.getJdbcTemplate().update(updateColumnValidSQL.toString());
            // 数据约束的ISVALID字段设为无效
            StringBuffer updateRestrictionValidSQL = new StringBuffer();
            updateRestrictionValidSQL.append("UPDATE CWM_RESTRICTION SET ")
                    .append("IS_VALID=0")
                    .append(" WHERE SCHEMA_ID='")
                    .append(oldSchema.getId())
                    .append("'");
            metaDAOFactory.getJdbcTemplate().update(updateRestrictionValidSQL.toString());
            // 数据视图的ISVALID字段设为无效
            StringBuffer updateViewValidSQL = new StringBuffer();
            updateViewValidSQL.append("UPDATE CWM_VIEWS SET ")
                    .append("IS_VALID=0")
                    .append(" WHERE SCHEMA_ID='")
                    .append(oldSchema.getId())
                    .append("'");
            metaDAOFactory.getJdbcTemplate().update(updateViewValidSQL.toString());
        } catch (Exception e) {
            e.printStackTrace();
            info.ErrorId = -1;
            info.Warning = e.toString();
            return info;
        }
        return info;
    }


    /**
     * 物理删除业务数据。其中包括：
     * (1)业务表本身和(业务表的sequence不在这里删除)
     * (2)业务表的多对多关系
     * (3)以该业务表为父表的动态列数据表及其关系
     */
    public void deleteBusinessData(Schema oldSchema, Set<String> deleteTableList) {
        StringBuffer sql = new StringBuffer();
        sql.append("select S_TABLE_NAME,PID,ID from CWM_TABLES where IS_VALID=0 and SCHEMA_ID='")
                .append(oldSchema.getId())
                .append("' and map_table is null");
        List<Map<String, Object>> tableList = metaDAOFactory.getJdbcTemplate().queryForList(sql.toString());    //查询得到已经被删除的Table
        tableList.forEach(map -> deleteTableList.add(CommonTools.Obj2String(map.get("S_TABLE_NAME"))));
        for (Map<String, Object> tableMap : tableList) {
            List a = metaDAOFactory.getJdbcTemplate().
                    queryForList("SELECT * FROM user_tables where table_name='" + ((String) tableMap.get("S_TABLE_NAME")).toUpperCase() + "'");
            if (!a.isEmpty()) {
                String tableName = ((String) tableMap.get("S_TABLE_NAME")).toUpperCase();
                String id = CommonTools.Obj2String(tableMap.get("ID"));
                metaDAOFactory.getJdbcTemplate().execute("drop table " + tableName + " cascade constraints");    //删除业务表及其数据
                //metaDAOFactory.getJdbcTemplate().execute("DROP sequence SEQ_" + tableName);  //删除sequence
                metaDAOFactory.getJdbcTemplate().execute("DELETE CWM_TABLES WHERE ID = '" + id + "'");   //删除CWM_TABLES里的记录
                //查询是否有以该表作为动态表的父表
                int i = metaDAOFactory.getJdbcTemplate()
                        .queryForObject("SELECT COUNT(*) FROM CWM_TABLES WHERE MAP_TABLE IN(SELECT ID FROM CWM_TABLES WHERE UPPER(S_TABLE_NAME) ='"
                                + tableName.toUpperCase() + "') AND SHARE_TYPE ='3' AND IS_VALID =4", Integer.class);
                if (i != 0) {
                    DynamicTableUtil.deleteDynamicData(metaDAOFactory.getJdbcTemplate(), tableName, false);
                }
                //删除数据类时搜索多对多关联中间表里的数据，如果包含该表的数据，则删除
                metaDAOFactory.getJdbcTemplate().execute("DELETE CWM_RELATION_DATA WHERE UPPER(MAIN_TABLE_NAME)='" + tableName.toUpperCase() + "' OR UPPER(SUB_TABLE_NAME)='" + tableName.toUpperCase() + "'");
            }
            String pid = (String) tableMap.get("PID");
            if (pid != null && pid.equals("")) {
                int i = metaDAOFactory.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM ALL_SEQUENCES WHERE SEQUENCE_NAME='SEQ_" + ((String) tableMap.get("S_TABLE_NAME")).toUpperCase() + "' AND SEQUENCE_OWNER='" + getTsn() + "'", Integer.class);
                if (i != 0) {
                    metaDAOFactory.getJdbcTemplate().execute("DROP sequence SEQ_" + ((String) (tableMap.get("S_TABLE_NAME"))).toUpperCase());
                }
            }
            //删除自定义的表访问权限
            metaDAOFactory.getJdbcTemplate().execute("DELETE FROM CWM_SYS_PARTOPERATIONS WHERE TABLE_ID='" + tableMap.get("ID") + "'");
        }
    }

    public void deleteLogicTableColumn(Schema oldSchema) {
        //将所有可用数据类的被设置为无效的字段改成删除
        String columnSql = "update cwm_tab_columns set is_valid = '3'" +
                " where id in (select id  from cwm_tab_columns  " +
                "where table_id in (select id  from cwm_tables  " +
                "where schema_id = '" + oldSchema.getId() + "' and is_valid = '1') and is_valid = '0')";
        metaDAOFactory.getJdbcTemplate().execute(columnSql);
        //删除cwm_relation_columns表中（被删除的关系属性）的数据
        columnSql = "delete cwm_relation_columns where id in" +
                " (select r.id from cwm_relation_columns r,cwm_tab_columns c where r.column_id=c.id and c.is_valid='3')";
        metaDAOFactory.getJdbcTemplate().execute(columnSql);
        columnSql = "delete cwm_tab_columns where is_valid = '3'";
        metaDAOFactory.getJdbcTemplate().execute(columnSql);
    }

    /**
     * 把schema改造为转化为xml需要的格式
     *
     * @param schema
     */
    public void dealSchema(Schema schema) throws Exception {
        Set<Restriction> restrictions = schema.getRestrictions();
        for (Iterator itor_Res = restrictions.iterator(); itor_Res.hasNext(); ) {
            Restriction restriction = (Restriction) itor_Res.next();
            if (restriction.getIsValid().equals(new Long(1)) && restriction.getType().equals(Restriction.TabelEnumType)) {
                //(1)将TableEnum中的relationTableEnums转化成	cwmRelationTableEnums<Table>  tabledetailSet<relationDetail>
                TableEnum tableEnum = restriction.getTableEnum();
                if (tableEnum == null) {
                    continue;
                }
                final Map<Long, Table> rteSet = new HashMap<>();
                final Set<RelationDetail> relationSet = new HashSet<>();
                Set<RelationTableEnum> relationTableEnums = tableEnum.getRelationTableEnums();
                for (Iterator iter_rte = relationTableEnums.iterator(); iter_rte.hasNext(); ) {
                    RelationTableEnum relationTableEnum = (RelationTableEnum) iter_rte.next();
                    Table refTable = relationTableEnum.getCwmTables();    //所关联的数据类
                    long order = relationTableEnum.getOrder();
                    rteSet.put(order, refTable);
                    Table originTable = relationTableEnum.getOriginTable();
                    Table fromTable = relationTableEnum.getFromtable();//关系属性所在的数据类
                    Table toTable = relationTableEnum.getTotable();
                    String type = relationTableEnum.getType();
                    RelationDetail rd = new RelationDetail();
                    rd.setFromTable(originTable);
                    rd.setToTable(refTable);
                    rd.setRfromTable(fromTable);
                    rd.setRtoTable(toTable);
                    rd.setType(type);
                    relationSet.add(rd);
                }
                tableEnum.setTabledetailSet(relationSet);
                tableEnum.setCwmRelationTableEnums(rteSet);
                String expr = tableEnum.getExpression();    //设置过滤表达式
                if (expr != null && expr.length() != 0) {
                    //将字段名替换成字段的唯一名称
                    for (long i = 0; i < rteSet.keySet().size(); i++) {
                        final Table table = rteSet.get(i);
                        List<IColumn> allColumns = table.getColumns();
                        for (IColumn column : allColumns) {
                            if (!column.getCategory().equals(Column.CATEGORY_COMMON)) {
                                continue;
                            }
                            String a = column.getColumnName();
                            expr = expr.replaceAll(a, column.getId());
                            tableEnum.setExpression(expr);
                        }
                    }
                }
            }
        }

        //view
        Set<View> allView = schema.getViews();
        for (Iterator itor_View = allView.iterator(); itor_View.hasNext(); ) {
            View view = (View) itor_View.next();
            //(1) 设置xml 中的 RelationDetail 元素 和cwmViewRelationtables
            final Set<RelationDetail> relationSet = new HashSet<>();//视图关联数据类Set
            final Map<String, Table> viewRelationTableSet = new HashMap<>();//视图关联数据类的关联明细Map<cwmViewRelationtables>
            Set RelationTableSet = view.getViewRelationTables();
            for (Iterator iter_RefColumn = RelationTableSet.iterator(); iter_RefColumn.hasNext(); ) {
                RelationDetail detail = new RelationDetail();
                ViewRefColumn relationColumn = (ViewRefColumn) iter_RefColumn.next();
                Table table = relationColumn.getCwmTables();
                Long order = relationColumn.getOrder();
                Table originTable = relationColumn.getOriginTable();
                Table fromTable = relationColumn.getFromTable();
                Table toTable = relationColumn.getToTable();
                String cite = null;
                if (relationColumn.getOrtView() != null) {
                    //得到真正的物化视图
                    String ortViewId = relationColumn.getOrtView();
                    View ortView = metaDAOFactory.getViewDAO().findById(ortViewId);
                    if (ortView != null) {
                        cite = ortView.getCite();
                    }
                }
                String type = relationColumn.getType();
                detail.setFromTable(originTable);
                detail.setToTable(table);
                detail.setRfromTable(fromTable);
                detail.setRtoTable(toTable);
                detail.setType(type);
                if (cite != null) {
                    detail.setViewName(cite);
                } else {
                    detail.setViewName(null);
                }
                relationSet.add(detail);
                String key;
                if (cite != null) {
                    key = String.valueOf(order) + "===" + cite;
                } else {
                    key = String.valueOf(order);
                }
                viewRelationTableSet.put(key, table);
            }
            view.setCwmViewRelationtables(viewRelationTableSet);
            view.setTabledetailSet(relationSet);
            //(2)设置XML 中的 cwmReturnViewColumns 返回属性
            final Map<String, Column> returnViewColumns = new HashMap<>();//视图的返回字段
            Set<ViewResultColumn> returnColumnSet = view.getReturnColumns();
            for (Iterator iter_reslutColumn = returnColumnSet.iterator(); iter_reslutColumn.hasNext(); ) {
                ViewResultColumn resultColumn = (ViewResultColumn) iter_reslutColumn.next();
                Long order = resultColumn.getOrder();
                Column retColumn = resultColumn.getCwmTabColumns();
                returnViewColumns.put(String.valueOf(order), retColumn);
            }
            view.setCwmReturnViewColumns(returnViewColumns);
            //(3)设置XML 中的 cwmPaixuViewColumns 排序属性
            final Map<String, Column> viewSortColumnMap = new HashMap<>();
            Set<ViewSortColumn> sortColumnSet = view.getPaixuColumns();
            for (Iterator iter_sortColumn = sortColumnSet.iterator(); iter_sortColumn.hasNext(); ) {
                ViewSortColumn viewsortColumn = (ViewSortColumn) iter_sortColumn.next();
                Long order = viewsortColumn.getOrder();
                Column sortColumn = viewsortColumn.getCwmTabColumns();
                viewSortColumnMap.put(String.valueOf(order), sortColumn);
            }
            view.setCwmPaixuViewColumns(viewSortColumnMap);
            //(4)设置过滤表达式
            String expr = view.getExpression();
            if (expr != null && !expr.isEmpty()) {
                //将字段名替换成字段的唯一名称
                for (Iterator iter_rtSet = viewRelationTableSet.keySet().iterator(); iter_rtSet.hasNext(); ) {
                    final Table table = viewRelationTableSet.get(iter_rtSet.next());
                    List<IColumn> allColumns = table.getColumns();

                    for (IColumn column : allColumns) {
                        if (!column.getCategory().equals(Column.CATEGORY_COMMON)) {
                            continue;
                        }
                        String a = column.getColumnName();
                        expr = expr.replaceAll(a, column.getId());
                        view.setExpression(expr);
                    }
                }
            }
            /*//设置统计视图的统计参数项
            if (view.getType().equals(new Long(2))) {
                Set canshuxiang = view.getCanshuxiang();
            }*/
        }
    }

}
