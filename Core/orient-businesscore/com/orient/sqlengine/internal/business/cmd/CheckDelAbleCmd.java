package com.orient.sqlengine.internal.business.cmd;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IRelationColumn;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 校验业务模型的记录是否可以删除
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 18, 2012
 */
public class CheckDelAbleCmd extends AbstractCmd {

    private static final long serialVersionUID = 1L;

    private IBusinessModel bm;
    private String dataIds;//可以是单个id,也可以是多个id以‘,’分割
    List<IRelationColumn> relCols = new ArrayList<>();

    /**
     * 校验业务模型的记录是否可以删除
     *
     * @param bm      业务模型
     * @param dataIds 待删除的记录主键字符串（可以是多个主键，需要以逗号分割）
     */
    public CheckDelAbleCmd(IBusinessModel bm, String dataIds) {
        this.bm = bm;
        this.dataIds = dataIds.replace(",", "','");

    }

    @Override
    protected Object executeInternal(JdbcTemplate jdbcTemplate)
            throws Exception {
        List<IColumn> cols = bm.getMatrix().getMainTable().getRelationColumns();
        boolean hasRestriction = false;

        for (IColumn col : cols) {
            if (col.getRelationColumnIF().getIsFK() == 0) {
                if (col.getRelationColumnIF().getOwnership() == 2 ||
                        col.getRelationColumnIF().getOwnership() == 3) {
                    hasRestriction = true;
                    relCols.add(col.getRelationColumnIF());
                }
            }
        }
        if (!hasRestriction) {
            return 0;
        }
        String sql = sql();

        int delType = 0;
        List<Map<String, Object>> countList = jdbc.queryForList(sql);
        for (int i = 1; i < countList.size(); i++) {
            Map<String, Object> map = countList.get(i);
            int count = Integer.valueOf(String.valueOf(map.get("count")));
            IRelationColumn bc = relCols.get(i - 1);
            if (bc.getOwnership() == 3 && count > 0) {
                return 2;
            }
            if (bc.getOwnership() == 2 && count > 0) {
                delType = 1;
            }
        }
        return delType;
    }

    @Override
    public String sql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select 0 count from dual ");
        for (IRelationColumn relCol : relCols) {
            if (relCol.getRelationType() == 4) {
                sql.append(" union all ");
                sql.append(" select  count(*) count ");
                sql.append(" from CWM_RELATION_DATA ");
                sql.append(" where MAIN_TABLE_NAME = '");
                sql.append(relCol.getTable().getTableName()).append("'");
                sql.append(" and SUB_TABLE_NAME = '");
                sql.append(relCol.getRefTable().getTableName()).append("'");
                sql.append(" and SUB_DATA_ID in ('").append(dataIds).append("') ");
            } else {
                sql.append(" union all ");
                sql.append(" select count(id) count ");
                sql.append(" from ").append(relCol.getRefTable().getTableName());
                sql.append(" where ").append(relCol.getTable().getTableName()).append("_ID");
                sql.append(" in ('").append(dataIds).append("') ");
            }
        }
        return sql.toString();
    }

}
