/**
 * BussinessModelGroupQueryImpl.java
 * com.orient.sqlengine.internal.query
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * Jul 20, 2012 		mengbin
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.sqlengine.internal.query;

import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum;
import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.BusinessTableEnum;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.bean.IdQueryCondition;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.metamodel.operationinterface.IView;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sqlengine.internal.SqlEngineHelper;
import com.orient.sqlengine.util.BusinessDataConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName:BussinessModelGroupQueryImpl
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author mengbin@cssrc.com.cn
 * @Date Jul 20, 2012		3:31:47 PM
 * @see
 * @since Ver 1.1
 */
public class BussinessModelGroupQueryImpl extends AbstractModelQuery implements IBusinessModelQuery {

    private static final long serialVersionUID = 1L;
    IBusinessModel bm;
    StringBuilder orderBuffer = new StringBuilder();
    List<IBusinessColumn> bcList = new ArrayList<IBusinessColumn>();

    List<String> dataList = new ArrayList<String>();


    /**
     * 业务模型查询句柄
     *
     * @param bm 业务模型
     * @throws
     */
    public BussinessModelGroupQueryImpl(IBusinessModel bm, List<IBusinessColumn> bcList) {
        this.bm = bm;
        this.bcList.addAll(bcList);
    }


    @SuppressWarnings("unchecked")
    public List list() {
        return (List) commandService.execute(this);
    }

    @Override
    public List list(Boolean clearCustomFilter) {
        List retVal = (List) commandService.execute(this);
        //查询后 清空过滤条件
        if (clearCustomFilter) {
            bm.clearCustomFilter();
        }
        return retVal;
    }

    @Override
    public <T> List<T> list(Class<T> beanClass, boolean mapUnderscoreToCamelCase) {
        List<Map<String, Object>> dataMaps = list();
        return BusinessDataConverter.convertMapListToBeanList(bm, dataMaps, beanClass, mapUnderscoreToCamelCase);
    }

    public IdQueryCondition getIdQueryCondition() {
        dataList.clear();
        StringBuilder initSql = new StringBuilder();
        initSql.append("select ").append(bm.getMainModel().getS_table_name() + ".ID").append(" from ").append(bm.getS_table_name());
        appendWhereClause(initSql);
        IdQueryCondition idQueryCondition = new IdQueryCondition(initSql.toString(), dataList);
        return idQueryCondition;
    }

    @Override
    public IBusinessModelQuery page(int start, int end) {
        count = false;
        page = new Page(start, end);
        return this;
    }

    @Override
    public IBusinessModelQuery orderAsc(String colName) {
        addOrderByClause(colName + " asc ");
        return this;
    }

    @Override
    public IBusinessModelQuery orderDesc(String colName) {
        addOrderByClause(colName + " desc ");
        return this;
    }


    @Override
    public String sql() {

        // TODO Auto-generated method stub
        dataList.clear();
        StringBuilder sql = new StringBuilder();
        if (bm.getModelType() == BusinessModelEnum.View) {
            IView view = (IView) bm.getMatrix();
            if (count) {
                sql.append("select ");
                sql.append("count(ID) count");
                sql.append(" from( ");
                sql.append(view.getViewSql());
                sql.append(" )");
            } else {
                sql.append("select ").append(getSelectSql());
                sql.append(" from( ");
                sql.append(view.getViewSql());
                sql.append(" )");
            }
        } else {
            sql.append("select ");
            if (count) {
                sql.append("count(").append(bm.getS_table_name()).append(".ID) count");
            } else {

                sql.append(getSelectSql());
            }
            sql.append(" from ");
            sql.append(bm.getS_table_name());
        }
        //where过滤
        appendWhereClause(sql);
        //增加排序
        if (!count) {
            //增加分组
            appendGroup(sql);

            if (SqlEngineHelper.isNullString(orderByClause)) {
                orderByClause = "";
                List<IBusinessColumn> cols = bm.getSortCols();
                if (cols.size() > 0) {
                    for (IBusinessColumn col : cols) {
                        orderByClause = orderByClause + "," + bm.getS_table_name() + "." + col.getS_column_name();
                    }
                    orderByClause = orderByClause.substring(1) + " " + bm.getPaixu_fx();
                } else {

                }
            }
            appendOrderByClause(sql);
        }
        custParam = SqlEngineHelper.list2stringArray(dataList);
        return sql.toString();
    }


    /**
     * @return String
     * @throws
     * @Method: getSelectSql
     * <p>
     * 获取select 的字段
     */

    private String getSelectSql() {
        StringBuffer sql = new StringBuffer();
        if (bcList.size() == 0) {
            sql.append(bm.getDefaultSelect());//默认查询字段
            appendOrderSelectSql(sql);
            return sql.toString();
        } else {
            for (IBusinessColumn baseColumn : bcList) {
                sql.append(",");
                sql.append(SqlEngineHelper.columnConvertSelectSql(baseColumn));
            }
            appendOrderSelectSql(sql);
            return sql.substring(1);
        }
    }

    private void appendGroup(StringBuilder sql) {
        sql.append(" group by ").append(getSelectSql());
    }

    /**
     * 如果设置的排序字段不在分组字段中，则增加排序字段作为查询字段
     *
     * @param sql
     */
    private void appendOrderSelectSql(StringBuffer sql) {
        List<IBusinessColumn> cols = bm.getSortCols();
        cols.forEach(baseColumn -> {
            if (sql.indexOf(baseColumn.getS_column_name()) == -1) {
                sql.append(",");
                sql.append(SqlEngineHelper.columnConvertSelectSql(baseColumn));
            }
        });
    }


    public void appendWhereClause(StringBuilder sql) {
        /*
        if (bm.getModelType() == BusinessModelEnum.View) {
			IView view = (IView) bm.getMatrix();
			//当视图中已含有where过滤条件时，则不添加where条件
			if(view.getViewSql().indexOf(" where ")<0){
				sql.append(" where 1=1 ");
			}
		}else{
			sql.append(" where 1=1 ");
		}
		*/

        sql.append(" where 1=1");
        /* 树节点的过滤 */
        QueryHelper.appendTreeNodeFilter(bm, sql);
        /*角色过滤*/
        if (null != bm.getMatrixRight()) {
            /*密级过滤*/
            if (bm.getSecrecyable() && !SqlEngineHelper.isNullString(bm.getUserSecrecy())) {
                sql.append("cwm_secrecy_convert(sys_secrecy) >= cwm_secrecy_convert('")
                        .append(bm.getUserSecrecy())
                        .append("') or sys_secrecy is null ");
            }

            /*表访问权限过滤*/
            String filter = bm.getMatrixRight().getFilter();
            String userFilter = bm.getMatrixRight().getUserFilter();
            if (!SqlEngineHelper.isNullString(filter)) {
                sql.append(" AND (").append(filter).append(") ");
            }
        }

        //保留的Sql过滤条件
        if (!SqlEngineHelper.isNullString(bm.getReserve_filter())) {
            sql.append(" ").append(bm.getReserve_filter());
        }

        //添加自定义过滤类的过滤条件
        sql.append(getCustomerFilter());

        //添加关系过滤条件
        if (null != bm.getPedigreeList() &&
                bm.getPedigreeList().size() > 0) {
            String relSql = QueryHelper.getRelationFilter(bm, dataList);
            if (!relSql.equals("")) {
                sql.append(" and ").append(relSql);
            }
        }

        /*共享模型的数据过滤*/
        if (bm.getTableType() == BusinessTableEnum.ShareTable) {
            if (!bm.getShareable()) {
                sql.append(" and ").append(bm.getS_table_name()).append(".sys_schema='");
                sql.append(bm.getSchema().getId()).append("'");
            }
        }
    }


    /**
     * 返回自定义过滤条件
     *
     * @return String
     * @Method: getCustomerFilter
     */
    private String getCustomerFilter() {
        StringBuilder sb = new StringBuilder();

        //初始化过滤条件
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


    public IBusinessModelQuery setDefaultIdSort(boolean isAsc) {
        this.isAsc = isAsc;
        return this;
    }

    @Override
    public Map<String, String> findById(String dataId) {
        return super.findById(dataId, bm);
    }
}

