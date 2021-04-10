/**
 * @Project: OrientEDM
 * @Title: CheckMultiUkCmd.java
 * @Package com.orient.sqlengine.internal.cmd
 * TODO
 * @author zhulc@cssrc.com.cn
 * @date Apr 12, 2012 9:51:37 AM
 * @Copyright: 2012 www.cssrc.com.cn. All rights reserved.
 * @version V1.0
 */


package com.orient.sqlengine.internal.business.cmd;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.sqlengine.internal.SqlEngineHelper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 校验记录是否满足唯一性约束命令
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 12, 2012
 */
public class CheckMultiUkCmd extends AbstractCmd {
    private static final Logger log = Logger.getLogger(CheckMultiUkCmd.class);

    private static final long serialVersionUID = 1L;

    Map<IBusinessColumn, String> multiUkMap = new HashMap<IBusinessColumn, String>();
    IBusinessModel bm;
    Map<String, String> dataMap;
    Object[] queryDataArr = null;

    /**
     * 校验记录是否满足唯一性约束命令
     *
     * @param bm      业务模型
     * @param dataMap 校验数据的Map结构
     *                Key:插入字段的数据库存储名称
     *                value:该字段的字符串值
     */
    public CheckMultiUkCmd(IBusinessModel bm, Map<String, String> dataMap) {
        this.bm = bm;
        this.dataMap = dataMap;
    }

    /**
     * @param jdbcTemplate
     * @return
     * @throws Exception
     */
    @Override
    protected Object executeInternal(JdbcTemplate jdbcTemplate) throws Exception {
        List<IBusinessColumn> ukList = bm.getMultyUkColumns();
        for (IBusinessColumn bc : ukList) {
            if (!dataMap.containsKey(bc.getS_column_name())) {
                return true;
            }
            multiUkMap.put(bc, dataMap.get(bc.getS_column_name()));
        }
        String sql = sql();
        try {
            long count = jdbc.queryForObject(sql, queryDataArr, Long.class);
            return count == 0 ? true : false;
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
        }
    }

    /**
     * @return
     */
    @Override
    public String sql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(id) count ");
        sql.append("from ");
        if (multiUkMap.size() > 0) {
            IBusinessColumn bc = multiUkMap.keySet().iterator().next();
            sql.append(bc.getParentModel().getS_table_name());
        }
        sql.append(" where 1=1 ");
        List<String> dataList = new ArrayList<String>();
        for (Map.Entry<IBusinessColumn, String> entry : multiUkMap.entrySet()) {
            IBusinessColumn bc = entry.getKey();
            if (!SqlEngineHelper.isNullString(bc.getS_column_name())) {
                sql.append(" and ");
                sql.append(bc.getS_column_name());
                sql.append("=");
                sql.append(SqlEngineHelper.columnConvertInsUpSql(bc));
                dataList.add(entry.getValue());
            }
        }
        queryDataArr = SqlEngineHelper.list2stringArray(dataList);
        return sql.toString();
    }

}
