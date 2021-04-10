package com.orient.sqlengine.internal.business.cmd;

import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.BusinessColumnEnum;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.sqlengine.internal.SqlEngineHelper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 更新业务模型的记录命令
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 11, 2012
 */
public class UpdateBusinessModelDataCmd extends AbstractCmd {

    private static final Logger log = Logger.getLogger(UpdateBusinessModelDataCmd.class);

    private static final long serialVersionUID = 1L;

    private IBusinessModel bm;//目标业务模型

    //key是字段名，value 字段值
    private Map<String, String> dataMap;//待插入数据Map

    String dataId = "";
    private Object[] updateDataArr;

    /*多对多关系字段列表*/
    List<IBusinessColumn> manyToManyCollist = new ArrayList<IBusinessColumn>();

    /**
     * 更新业务模型的记录命令
     *
     * @param bm      业务模型
     * @param dataMap 更新的数据记录map结构
     *                Key:插入字段的数据库存储名称
     *                value:该字段的字符串值
     * @param dataId  更新记录的主键
     */
    public UpdateBusinessModelDataCmd(IBusinessModel bm
            , Map<String, String> dataMap, String dataId) {
        this.bm = bm;
        this.dataMap = dataMap;
        this.dataId = dataId;
    }

    protected Object executeInternal(JdbcTemplate jdbcTemplate) throws Exception {
        try {
            String sql = sql();
            if (updateDataArr.length != 0) {
                //判断是否只更新多对多关系
                jdbc.update(sql, updateDataArr);
            }
            /*插入多对多关系*/
            for (IBusinessColumn c : manyToManyCollist) {
                String colName = c.getS_column_name();
                if (SqlEngineHelper.isNullString(colName)) {
                    continue;
                }
                String data = SqlEngineHelper.Obj2String(dataMap.get(colName));
                deleteManyToManyData(c, dataId);
                insertManyToMany(data, c, String.valueOf(dataId));
            }
		    /*刷新表关联的所有物化视图*/
            refreshMView(bm);
            return true;
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
        List<String> dataList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        List<IBusinessColumn> columnList = new ArrayList<>();
        for (IBusinessColumn c : bm.getMainModel().getAllBcCols()) {
            columnList.add(c);
            if (c.getColType() == BusinessColumnEnum.C_Relation &&
                    c.getCol().getRelationColumnIF().getRelationType() == 4) {
                manyToManyCollist.add(c);
                columnList.remove(c);
            }
        }
        sql.append(" update ");
        sql.append(bm.getS_table_name());
        sql.append(" set ");
        for (IBusinessColumn c : columnList) {
            String name = c.getS_column_name();
            if (SqlEngineHelper.isNullString(name) || !dataMap.containsKey(name)) {
                continue;
            }
            String data = SqlEngineHelper.Obj2String(dataMap.get(name)).trim();
            sql.append(name).append("=").append(SqlEngineHelper.columnConvertInsUpSql(c)).append(",");
            dataList.add(data);
        }
        updateDataArr = SqlEngineHelper.list2stringArray(dataList);
        sql.deleteCharAt(sql.length() - 1).append(" where id=").append(dataId);
        return sql.toString();
    }

}
