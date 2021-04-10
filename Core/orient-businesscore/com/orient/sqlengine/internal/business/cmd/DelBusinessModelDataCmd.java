package com.orient.sqlengine.internal.business.cmd;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.sqlengine.internal.SqlEngineHelper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 删除模型中记录命令
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 16, 2012
 */
public class DelBusinessModelDataCmd extends AbstractCmd {

    private static final Logger log = Logger.getLogger(DelBusinessModelDataCmd.class);

    private static final long serialVersionUID = 1L;
    List<String> delSqlList = new ArrayList<>();
    IBusinessModel bm;
    String dataIds;
    boolean cascade = false;
    StringBuffer sql = new StringBuffer();
    StringBuilder subTableDataId = new StringBuilder();

    /**
     * 删除模型中记录命令
     *
     * @param bm      业务模型
     * @param dataIds 待删除记录的主键字符串（可以是多个主键，需要以逗号分割）
     * @param cascade 是否级联删除(True级联删，False不级联删除)
     */
    public DelBusinessModelDataCmd(IBusinessModel bm, String dataIds, boolean cascade) {
        this.bm = bm;
        this.dataIds = dataIds;
        this.cascade = cascade;
    }

    /**
     * @param jdbcTemplate
     * @return
     * @throws Exception
     */
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

    /**
     * @return
     */
    @Override
    public String sql() {
        ITable table =  bm.getMatrix().getMainTable();
        prepareDelSqls(dataIds, table, false);
        return null;
    }

    private void prepareDelSqls(String dataIds, ITable table, boolean calculated) {
        /*删除本类的记录*/
        sql.setLength(0);
        sql.append(" delete from ");
        sql.append(table.getTableName());
        sql.append(" where id = any(");
        sql.append(dataIds);
        sql.append(") ");
        delSqlList.add(sql.toString());

        if (calculated) {
            return;
        }
		/*删除多对多表中的记录*/
        sql.setLength(0);
        sql.append(" delete from cwm_relation_data ");
        sql.append(" where ");
        sql.append(" (main_table_name = '").append(table.getTableName()).append("' ");
        sql.append(" and main_data_id = any(").append(dataIds).append(")) ");
        sql.append(" or ");
        sql.append(" (sub_table_name = '").append(table.getTableName()).append("' ");
        sql.append(" and sub_data_id = any(").append(dataIds).append(")) ");
        delSqlList.add(sql.toString());
        if (!cascade) {
            return;
        }

        for (IColumn bc : table.getRelationColumns()) {
            //如果是多对多关系直接返回不执行查询关联字表
            if (4 == bc.getRelationColumnIF().getRelationType()) {
                continue;
            }

            if (bc.getCategory() == 2
                    && bc.getRelationColumnIF().getIsFK() == 0) {
                /*查询关联子表的记录*/
                subTableDataId.setLength(0);
                ITable subTable = bc.getRelationColumnIF().getRefTable();
                subTableDataId.append(" select id from ");
                subTableDataId.append(subTable.getTableName());
                subTableDataId.append(" where ").append(table.getTableName()).append("_ID");
                subTableDataId.append(" = any(");
                subTableDataId.append(dataIds);
                subTableDataId.append(") ");

                boolean sameTable = false;
                if (subTable.getName().equals(table.getName())) {
                    sameTable = true;
                }

				/*准备关联子表的删除SQL*/
                this.prepareDelSqls(subTableDataId.toString(), subTable, sameTable);
            }
        }
    }

}
