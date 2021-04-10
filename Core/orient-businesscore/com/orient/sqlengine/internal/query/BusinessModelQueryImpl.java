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
 * 业务模型查询句柄
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 18, 2012
 */
public class BusinessModelQueryImpl extends AbstractModelQuery implements IBusinessModelQuery {

    private static final long serialVersionUID = 1L;
    IBusinessModel bm;
    StringBuilder orderBuffer = new StringBuilder();

    List<String> dataList = new ArrayList<>();

    /**
     * 业务模型查询句柄
     *
     * @param bm 业务模型
     * @throws
     */
    public BusinessModelQueryImpl(IBusinessModel bm) {
        this.bm = bm;
    }


    @SuppressWarnings("unchecked")
    public List list() {
        List retVal = (List) commandService.execute(this);
        return retVal;
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

    public IdQueryCondition getIdQueryCondition() {
        dataList.clear();
        StringBuilder initSql = new StringBuilder();
        initSql.append("select ").append(bm.getMainModel().getS_table_name() + ".ID").append(" from ").append(bm.getS_table_name());
        appendWhereClause(initSql);
        IdQueryCondition idQueryCondition = new IdQueryCondition(initSql.toString(), dataList);
        return idQueryCondition;
    }

    public String sql() {
        dataList.clear();
        StringBuilder sql = new StringBuilder();
        if (bm.getModelType() == BusinessModelEnum.View) {
            IView view = (IView) bm.getMatrix();
            sql.append("select * from (");
            sql.append(view.getViewSql());
            sql.append(")");
        } else {
            sql.append("select ");
            sql.append(getSelectSql());
            sql.append(" from ");
            sql.append(bm.getS_table_name());
        }
        //where过滤
        appendWhereClause(sql);

        if (count) {
            StringBuffer countSql = new StringBuffer();
            if (bm.getModelType() == BusinessModelEnum.View) {
                countSql.append("select ");
                countSql.append("count(ID) count");
                countSql.append(" from( ");
                countSql.append(sql);
                countSql.append(" )");
            } else {
                countSql.append("select ");
                countSql.append("count(").append(bm.getS_table_name()).append(".ID) count");
                countSql.append(" from( ");
                countSql.append(sql);
                countSql.append(" ) ");
                countSql.append(bm.getS_table_name());
            }
            sql.setLength(0);
            sql.append(countSql);
        }
        //增加排序,如果查询记录数,则不用排序
        else {
            if (bm.getModelType() == BusinessModelEnum.Table && SqlEngineHelper.isNullString(orderByClause)) {
                orderByClause = "";
                List<IBusinessColumn> cols = bm.getSortCols();
                if (cols.size() > 0) {
                    for (IBusinessColumn col : cols) {
                        orderByClause = orderByClause + "," + bm.getS_table_name() + "." + col.getS_column_name();
                    }
                    orderByClause = orderByClause.substring(1) + " " + bm.getPaixu_fx();
                    //最后加上按ID升序排序，防止因为排序字段有重复记录导致每次全部记录的排序顺序不一致
                    orderByClause = orderByClause + ", to_number(" + bm.getS_table_name() + ".ID) ASC ";
                } else {
                    //orderByClause =" to_number("+bm.getS_table_name()+".ID) ASC ";
                    if (this.isAsc) {
                        orderByClause = " to_number(" + bm.getS_table_name() + ".ID) ASC ";
                    } else {
                        orderByClause = " to_number(" + bm.getS_table_name() + ".ID) DESC ";
                    }
                }
            }
            appendOrderByClause(sql);
            //增加分页
            appendPageClause(sql);
        }
        custParam = SqlEngineHelper.list2stringArray(dataList);
        return sql.toString();
    }

    public void appendWhereClause(StringBuilder sql) {
        sql.append(" where 1=1 ");
        /* 树节点的过滤 */
       QueryHelper.appendTreeNodeFilter(bm,sql);
        //TODO 加入用户关联过滤
        /*角色过滤*/
        if (null != bm.getMatrixRight()) {
            /*密级过滤*/
            if (bm.getSecrecyable() && !SqlEngineHelper.isNullString(bm.getUserSecrecy())) {
                sql.append("cwm_secrecy_convert(sys_secrecy) <= cwm_secrecy_convert('")
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
            sql.append(" and ").append(QueryHelper.getRelationFilter(bm, dataList));
        }

        /*共享模型的数据过滤*/
        /*共享模型中本身自带的数据和本模型添加进去的数据都可以查出来*/
        if (bm.getTableType() == BusinessTableEnum.ShareTable) {
            if (!bm.getShareable()) {
                sql.append(" and (").append(bm.getS_table_name()).append(".sys_schema='");
                sql.append(bm.getSchema().getId()).append("' or ")
                        .append(bm.getS_table_name()).append(".sys_schema is null)");
            }
        }
    }

    private String getDataFilter(String data_filter) {
        return data_filter;
    }

    private String getSelectSql() {
        StringBuffer sql = new StringBuffer();
        sql.append(bm.getDefaultSelect());//默认查询字段
        for (IBusinessColumn baseColumn : bm.getAllBcCols()) {
            sql.append(",");
            sql.append(SqlEngineHelper.columnConvertSelectSql(baseColumn));
        }
        return sql.toString();
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
            //得到连接操作符,默认是and
            if (null != filter.getConnection()) {
                String connectionType = filter.getConnection().name();
                if ("Or".equals(connectionType)) sb.append(" or ");
                if ("And".equals(connectionType)) sb.append(" and ");
            } else {
                sb.append(" and ");
            }
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
