package com.orient.sqlengine.internal.business.cmd;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.sqlengine.internal.SqlEngineHelper;
import com.orient.utils.CommonTools;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;


/**
 * 校验字段值是否唯一命令
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 12, 2012
 */

public class CheckColumnOnlyCmd<Boolean> extends AbstractCmd {

    private static final Logger log = Logger.getLogger(CheckColumnOnlyCmd.class);

    private static final long serialVersionUID = 1L;

    IBusinessColumn bc;
    String colValue;

    public CheckColumnOnlyCmd(IBusinessColumn bc, String colValue) {
        this.bc = bc;
        this.colValue = colValue;
    }

    /**
     * @param jdbcTemplate
     * @return
     */
    @Override
    protected Object executeInternal(JdbcTemplate jdbcTemplate)
            throws Exception {
        String sql = sql();
        try {
            long count = jdbc.queryForObject(sql, new Object[]{colValue}, Long.class);
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
     * @Method: sql
     */
    @Override
    public String sql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(id) count ");
        sql.append(" from ");
        sql.append(bc.getParentModel().getS_table_name());
        if (!SqlEngineHelper.isNullString(bc.getS_column_name())) {
            sql.append(" where ");
            sql.append(bc.getS_column_name());
            sql.append(" = ");
            sql.append(SqlEngineHelper.columnConvertInsUpSql(bc));
            //spf 2014/2/24
            String extraCondition = CommonTools.Obj2String(bc.getExtraCondition());
            if (!"".equals(extraCondition)) {
                sql.append(" ").append(extraCondition).append(" ");
            }
        }
        return sql.toString();
    }

}
