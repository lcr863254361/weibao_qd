package com.orient.sqlengine.internal.query;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.sqlengine.api.SqlEngineException;
import com.orient.sqlengine.cmd.api.EDMCommand;
import com.orient.sqlengine.cmd.api.EDMCommandService;
import com.orient.sqlengine.internal.SqlEngineHelper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象查询句柄
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 18, 2012
 */
public abstract class AbstractModelQuery implements EDMCommand<Object> {

    private static final Logger log = Logger.getLogger(AbstractModelQuery.class);
    protected EDMCommandService commandService;
    protected String orderByClause = null;
    protected Page page = null;
    protected boolean count = false;//是否用于查詢记录数
    protected Object[] custParam;

    protected boolean isAsc = false;//默认为以Id为升序

    /**
     * 返回查询记录或查询记录数的sql
     *
     * @return String
     * @Method: sql
     */
    public abstract String sql();

    /**
     * 返回查询记录数
     *
     * @return long
     * @Method: count
     */
    @SuppressWarnings("unchecked")
    public long count() {
        count = true;
        page = null;
        Map<String, Object> map = (Map<String, Object>) untypedUniqueResult();
        return Long.valueOf(String.valueOf(map.get("count")));
    }

    @SuppressWarnings("unchecked")
    public List untypedList() {
        return (List) commandService.execute(this);
    }

    @SuppressWarnings("unchecked")
    protected Object untypedUniqueResult() {
        List list = untypedList();
        if (list.isEmpty()) {
            return null;
        }
        if (list.size() > 1) {
            throw new SqlEngineException("记录数不唯一 " + list.size());
        }
        return list.get(0);
    }

    public Object execute(JdbcTemplate jdbc) throws Exception {
        String sql = sql();
        try {
            return jdbc.queryForList(sql, custParam);
        } catch (Exception e) {
            Throwable t = e;
            while (t != null) {
                if (t instanceof SQLException) {
                    SQLException sqlException = (SQLException) t;
                    SQLException nextException = sqlException
                            .getNextException();
                    if (nextException != null) {
                        log.error("cause of " + nextException + ": ", e);
                    }
                }
                t = t.getCause();
            }
            throw e;
        } finally {
            resetQuery();
        }
    }

    /**
     * 设置业务模型的排序条件
     *
     * @param sql
     */
    protected void appendOrderByClause(StringBuilder sql) {
        if (!SqlEngineHelper.isNullString(orderByClause)) {
            sql.append(" order by ");
            sql.append(orderByClause);
        }
    }

    /**
     * 业务模型设置分页
     *
     * @param sql
     */
    protected void appendPageClause(StringBuilder sql) {
        if (null != page) {
            if (count) {
                throw new SqlEngineException("翻页功能和统计功能不可以同时使用");
            }
            if (page.start >= 0 && page.end > 0 && page.end - page.start >= 0) {
                StringBuilder sqlBuilder = new StringBuilder();
                sqlBuilder.append(" select * from ( ");
                sqlBuilder.append("  select main_sql.*,RowNum rn from ( ");
                sqlBuilder.append(sql).append(") main_sql where RowNum <= ");
                sqlBuilder.append(page.end).append(" ) ");
                sqlBuilder.append(" where rn > ").append(page.start);
                sqlBuilder.append(" ORDER BY rn");
                sql.setLength(0);
                sql.append(sqlBuilder);
            }
        }

    }

    /**
     * 业务模型排序
     *
     * @param clause
     */
    protected void addOrderByClause(String clause) {
        if (orderByClause == null) {
            orderByClause = clause;
        } else {
            orderByClause += ", " + clause;
        }
    }

    /**
     * 刷新查询类
     *
     */
    private void resetQuery() {
        count = false;
        orderByClause = null;
    }

    public void setCommandService(EDMCommandService commandService) {
        this.commandService = commandService;
    }

    protected Map<String, String> findById(String dataId, IBusinessModel bm) {
        String originalReserveFilter = bm.getReserve_filter();
        bm.setReserve_filter(originalReserveFilter + " AND ID = " + dataId);
        Map<String, String> retVal = new HashMap<>();
        List<Map<String, String>> queryList = (List) commandService.execute(this);
        if (queryList.size() > 0) {
            retVal = queryList.get(0);
        }
        bm.setReserve_filter(originalReserveFilter);
        return retVal;
    }

}
