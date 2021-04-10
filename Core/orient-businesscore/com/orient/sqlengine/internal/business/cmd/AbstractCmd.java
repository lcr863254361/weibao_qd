package com.orient.sqlengine.internal.business.cmd;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.metamodel.metadomain.ViewRefColumn;
import com.orient.metamodel.operationinterface.IView;
import com.orient.sqlengine.api.SqlEngineException;
import com.orient.sqlengine.cmd.api.EDMCommand;
import com.orient.sqlengine.internal.SqlEngineHelper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhulc@cssrc.com.cn
 * @ClassName AbstractCmd
 * 业务操作命令的父类
 * @date Apr 18, 2012
 */

public abstract class AbstractCmd implements EDMCommand<Object> {

    protected JdbcTemplate jdbc;

    public abstract String sql();

    @Override
    public Object execute(JdbcTemplate jdbcTemplate) throws Exception {
        jdbc = jdbcTemplate;
        Object obj = executeInternal(jdbc);
        return obj;
    }

    protected abstract Object executeInternal(JdbcTemplate jdbcTemplate) throws Exception;

    protected int querySeqNexVal(String seqName) {
        int sequence_num = jdbc.queryForObject("select " + seqName + ".nextval from dual ", Integer.class);
        return sequence_num;
    }

    /**
     * 查询自增生成器的内容
     *
     * @param seqName
     * @return String
     * @Method: querySeqContent
     */
    protected String querySeqContent(String seqName) {
        String sql = "SELECT T.CONTENT AS SEQ_CONTENT FROM CWM_SEQGENERATOR T WHERE T.NAME=?";
        List<Map<String, Object>> seqList = jdbc.queryForList(sql, new Object[]{seqName});
        String seq_content = "";
        if (seqList.size() > 0) {
            seq_content = SqlEngineHelper.Obj2String(seqList.get(0).get("SEQ_CONTENT"));
        }
        return seq_content;
    }

    /**
     * 删除多对多关系字段存储在cwm_relation_data中的记录
     *
     * @param rc         多对多关系字段
     * @param mainDataId 多对多关系字段所在表的主键
     * @Method: deleteManyToManyData
     */
    protected void deleteManyToManyData(IBusinessColumn rc, String mainDataId) {
        StringBuffer delete_sql = new StringBuffer();
        delete_sql.append(" delete cwm_relation_data ");
        delete_sql.append(" where ");
        delete_sql.append(" main_table_name = '").append(rc.getParentModel().getS_table_name()).append("' ");
        delete_sql.append(" and main_data_id = '").append(mainDataId).append("' ");
        delete_sql.append(" and sub_table_name = '").append(rc.getCol().getRelationColumnIF().getRefTable().getTableName()).append("' ");
        jdbc.execute(delete_sql.toString());
    }

    /**
     * 插入多对多关系记录
     *
     * @param m2mData 关系表记录
     * @param c       多对多关系字段
     * @param dataId  多对多关系字段所在表的主键
     * @Method: insertManyToMany
     */
    protected void insertManyToMany(String m2mData, IBusinessColumn c, String dataId) {
        String[] ids = m2mData.split(",");
        StringBuilder relSql = new StringBuilder();
        String[] sqlArr = new String[ids.length];
        int index = 0;
        for (String id : ids) {
            relSql.setLength(0);
            relSql.append(" insert into cwm_relation_data ");
            relSql.append(" (id,main_table_name,main_data_id,sub_table_name,sub_data_id) ");
            relSql.append(" values (seq_cwm_relation_data.NEXTVAL, ");
            relSql.append("'").append(c.getParentModel().getS_table_name()).append("', ");
            relSql.append(dataId).append(", ");
            relSql.append("'").append(c.getCol().getRelationColumnIF().getRefTable().getTableName()).append("', ");
            relSql.append("'").append(id).append("') ");
            sqlArr[index] = relSql.toString();
            index++;
        }
        jdbc.batchUpdate(sqlArr);
    }

    /**
     * 刷新业务模型所关联的物化视图
     *
     * @param bm 业务模型
     * @Method: refreshMView
     */
    @SuppressWarnings("unchecked")
    protected void refreshMView(IBusinessModel bm) {
        Set refViewCol = bm.getMatrix().getMainTable().getCwmViewRelationtables();
        for (Object obj : refViewCol) {
            ViewRefColumn vrc = (ViewRefColumn) obj;
            IView view = vrc.getCwmViews();
            if (view != null && view.getType() == 2) {
                String storedProcName = "DBMS_MVIEW.REFRESH('" + view.getName() + "')";
                jdbc.execute(new ProcCallableStatementCreator(storedProcName),
                        new ProcCallableStatementCallback());
            }
        }
    }

    private class ProcCallableStatementCreator implements CallableStatementCreator {

        /**
         * The stored proc.
         */
        private String storedProc;

        /**
         * 创建存储过程
         *
         * @param storedProc the stored proc
         */
        public ProcCallableStatementCreator(String storedProc) {
            this.storedProc = storedProc;
        }

        public CallableStatement createCallableStatement(Connection conn)
                throws SQLException {
            StringBuffer storedProcName = new StringBuffer("call " + storedProc);
            CallableStatement cs = null;
            try {
                cs = conn.prepareCall(storedProcName.toString());
            } catch (SQLException e) {
                throw new SqlEngineException("创建存储过程对象出错 " + e.getMessage());
            }
            return cs;
        }
    }

    /**
     * The Class ProcCallableStatementCallback.
     */
    private class ProcCallableStatementCallback implements CallableStatementCallback<Object> {

        /**
         * Instantiates a new proc callable statement callback.
         */
        public ProcCallableStatementCallback() {
        }

        /**
         * 存储过程没有返回值，只返回是否执行成功.
         *
         * @param cs the cs
         * @return the object
         */
        public Object doInCallableStatement(CallableStatement cs) {
            try {
                return cs.execute();
            } catch (SQLException e) {
                throw new SqlEngineException(
                        "执行存储过程出错 " + e.getMessage());
            }
        }
    }

}
