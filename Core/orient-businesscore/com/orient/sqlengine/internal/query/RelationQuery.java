package com.orient.sqlengine.internal.query;

import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum;
import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.BusinessTableEnum;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.metamodel.operationinterface.IView;
import com.orient.sqlengine.internal.SqlEngineHelper;
import com.orient.utils.StringUtil;

import java.util.List;

/**
 * @author zhulc@cssrc.com.cn
 * @ClassName RelationQuery
 * 模型指定列的查询语句
 * @date Apr 28, 2012
 */

public class RelationQuery {
    IBusinessModel bm;
    String queryColName;
    List<String> dataList;

    public RelationQuery(IBusinessModel bm, String queryColName, List<String> dataList) {
        this.bm = bm;
        this.queryColName = queryColName;
        this.dataList = dataList;
    }

    public String toSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select * ");
        sql.append(" from ").append(bm.getS_table_name());
        appendWhereClause(sql);

        StringBuilder sql1 = new StringBuilder();

        sql1.append(" select ").append(queryColName);
        sql1.append(" from (").append(sql).append(")");
        return sql1.toString();

    }

    private void appendWhereClause(StringBuilder sql) {
        if (bm.getModelType() == BusinessModelEnum.View) {
            IView view = (IView) bm.getMatrix();
            // 当视图中已含有where过滤条件时，则不添加where条件
            if (view.getViewSql().indexOf(" where ") < 0) {
                sql.append(" where 1=1 ");
            }
        } else {
            sql.append(" where 1=1 ");
        }

        /* 树节点的过滤 */
        if (null != bm.getTreeNodeFilterModelBean()) {
            // tbom定义的静态过滤表达式
            if (!StringUtil.isEmpty(bm.getTreeNodeFilterModelBean().getStatic_filter())) {
                sql.append(" and " + bm.getTreeNodeFilterModelBean().getStatic_filter());
            }
            if (!StringUtil.isEmpty(bm.getTreeNodeFilterModelBean().getExtNode_filter())) {
                // 动态节点的过滤表达式
                sql.append(" and " + bm.getTreeNodeFilterModelBean().getExtNode_filter());
            }
        }

        /* 角色过滤 */
        if (null != bm.getMatrixRight()) {
            /* 密级过滤 */
            if (bm.getSecrecyable()
                    && !SqlEngineHelper.isNullString(bm.getUserSecrecy())) {
                sql.append("cwm_secrecy_convert(sys_secrecy) >= cwm_secrecy_convert('");
                sql.append(bm.getUserSecrecy()).append("') or sys_secrecy is null ");
            }

            /* 表访问权限过滤 */
            String filter = bm.getMatrixRight().getFilter();
            if (!SqlEngineHelper.isNullString(filter)) {
                StringBuilder newSql = new StringBuilder();
                newSql.append("select * from (").append(sql).append(") ")
                        .append(bm.getS_table_name());
                String dataFilter = QueryHelper.getDataFilter(filter);
                if (!"".equals(filter) && filter.indexOf("A[<-yes->]") != -1) {
                    // 数据表和用户表有关联
                    // 获取当前用户的全表查询Sql
                    String sysUserSql = "select * from cwm_sys_user";

                    newSql.append(",(").append(sysUserSql).append(") sys_user ");
                }
                newSql.append(" where ").append(dataFilter);
                sql.setLength(0);
                sql.append(newSql);
            }
        }

        // 自定义Sql过滤条件
        if (!SqlEngineHelper.isNullString(bm.getReserve_filter())) {
            sql.append(" and ").append(bm.getReserve_filter());
        }

        // 添加自定义过滤类的过滤条件
        sql.append(getCustomerFilter());

        //添加关系过滤条件
        if (null != bm.getPedigreeList() &&
                bm.getPedigreeList().size() > 0) {
            sql.append(" and ").append(QueryHelper.getRelationFilter(bm, dataList));
        }

        /* 共享模型的数据过滤 */
        if (bm.getTableType() == BusinessTableEnum.ShareTable) {
            if (!bm.getShareable()) {
                sql.append(" and ").append(bm.getS_table_name()).append(
                        ".sys_schema='");
                sql.append(bm.getSchema().getId()).append("'");
            }
        }
    }


    private String getCustomerFilter() {
        StringBuilder sb = new StringBuilder();
        // 初始化过滤条件
        for (CustomerFilter filter : bm.getCustFilterList()) {
            filter.setVisited(false);
        }

        for (CustomerFilter filter : bm.getCustFilterList()) {
            if (filter.isVisited()) {
                continue;
            }
            sb.append(" and ");
            sb.append("(").append(filter.toSql(dataList, bm)).append(")");
        }

        return sb.toString();
    }


}
