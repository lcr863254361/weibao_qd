package com.orient.sqlengine.internal.business.cmd;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.sqlengine.internal.SqlEngineHelper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 查询记录对应的主键显示值命令
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 18, 2012
 */
public class QueryUkShowValueCmd extends AbstractCmd {

    private static final Logger log = Logger.getLogger(QueryUkShowValueCmd.class);

    private static final long serialVersionUID = 1L;

    private IBusinessModel bm;
    private String pkId;

    /**
     * 查询记录对应的主键显示值命令
     *
     * @param bm   业务模型
     * @param pkId 记录的主键值
     */
    public QueryUkShowValueCmd(IBusinessModel bm, String pkId) {
        this.bm = bm;
        this.pkId = pkId;
    }

    @Override
    protected Object executeInternal(JdbcTemplate jdbcTemplate) throws Exception {
        String sql = sql();
        try {
            Map<String, Object> refColMap = jdbc.queryForMap(sql);
            StringBuilder refValue = new StringBuilder();
            for (Map.Entry<String, Object> entry : refColMap.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("ID")
                        || SqlEngineHelper.Obj2String(entry.getValue()).equals("")) {
                    continue;
                }
                refValue.append(",").append(entry.getValue());
            }
            if (refValue.length() == 0) {
                //主键显示值为空时，采用ID为主键显示值
                refValue.append(refColMap.get("ID"));
                return refValue.toString();
            } else {
                return refValue.substring(1);
            }
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

    @Override
    public String sql() {
        List<IBusinessColumn> bcs = bm.getRefShowColumns();
        StringBuilder sql = new StringBuilder("select id");
        for (IBusinessColumn bc : bcs) {
            if (!SqlEngineHelper.isNullString(bc.getS_column_name())) {
                sql.append(",");
                sql.append(bc.getCol().getColumnName());
            }
        }
        sql.append(" from ").append(bm.getS_table_name());
        sql.append(" where id =").append(pkId).append("");
        return sql.toString();
    }

}
