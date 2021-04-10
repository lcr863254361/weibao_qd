package com.orient.sqlengine.internal.business.cmd;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.sqlengine.internal.SqlEngineHelper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClearBusinessModelDataCmd extends AbstractCmd {

    private static final long serialVersionUID = 8052292543049490364L;
    List<String> delSqlList = new ArrayList<>();
    IBusinessModel bm = null;

    StringBuffer sql = new StringBuffer();
    private static final Logger log = Logger.getLogger(DelBusinessModelDataCmd.class);

    public ClearBusinessModelDataCmd(IBusinessModel bm) {
        this.bm = bm;
    }

    @Override
    protected Object executeInternal(JdbcTemplate jdbcTemplate) throws Exception {
        sql();
        try {
            /*获取倒序的sql,先删子表*/
            String[] delSqlArr = SqlEngineHelper.list2StringArrayDesc(delSqlList);
            return jdbcTemplate.batchUpdate(delSqlArr);
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
        ITable table = bm.getMatrix().getMainTable();
        prepareDelSqls(table);
        return null;
    }

    private void prepareDelSqls(ITable table) {
		/*删除本类的记录*/
        sql.setLength(0);
        sql.append(" delete  from ");
        sql.append(table.getTableName());
        delSqlList.add(sql.toString());
		
		/*删除多对多表中的记录*/
        sql.setLength(0);
        sql.append(" delete from cwm_relation_data ");
        sql.append(" where ");
        sql.append(" (main_table_name = '").append(table.getTableName()).append("' )");
        sql.append(" or ");
        sql.append(" (sub_table_name = '").append(table.getTableName()).append("' )");
        delSqlList.add(sql.toString());
    }

}
