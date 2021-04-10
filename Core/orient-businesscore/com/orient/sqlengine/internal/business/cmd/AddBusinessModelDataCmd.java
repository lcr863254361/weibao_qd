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
 * 保存业务模型数据命令
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 11, 2012
 */
public class AddBusinessModelDataCmd extends AbstractCmd {

    /**
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(AddBusinessModelDataCmd.class);

    private IBusinessModel bm;//目标业务模型

    //key是字段名，value 字段值
    private Map<String, String> dataMap;//待插入数据Map


    private Object[] insertDataArr;
    private int dataId;
    /*多对多关系字段列表*/
    List<IBusinessColumn> manyToManyCollist = new ArrayList<>();

    private static final String PK_NAME = "ID";

    /**
     * 保存业务模型数据命令
     *
     * @param bm      业务模型
     * @param dataMap 业务模型数据Map
     *                Key:插入字段的数据库存储名称
     *                value:该字段的字符串值
     */
    public AddBusinessModelDataCmd(IBusinessModel bm, Map<String, String> dataMap) {
        this.bm = bm;
        this.dataMap = dataMap;
    }

    protected Object executeInternal(JdbcTemplate jdbcTemplate) throws Exception {
        try {
            String sql = sql();
            jdbc.update(sql, insertDataArr);
            /*插入多对多关系*/
            for (IBusinessColumn c : manyToManyCollist) {
                String colName = c.getS_column_name();
                if (SqlEngineHelper.isNullString(colName)) {
                    continue;
                }
                String data = SqlEngineHelper.Obj2String(dataMap.get(colName));
                if (SqlEngineHelper.isNullString(data)) {
                    continue;
                }
                insertManyToMany(data, c, String.valueOf(dataId));
            }
            /*刷新表关联的所有物化视图*/
            refreshMView(bm);
            dataMap.put("ID", String.valueOf(dataId));
            return dataId;
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
        StringBuilder values = new StringBuilder();
        List<IBusinessColumn> columnList = new ArrayList<>();
        for (IBusinessColumn c : bm.getMainModel().getAllBcCols()) {
            columnList.add(c);
            if (c.getColType() == BusinessColumnEnum.C_Relation && c.getCol().getRelationColumnIF().getRelationType() == 4) {
                manyToManyCollist.add(c);
                columnList.remove(c);
            }
        }

        String dataIdStr = dataMap.get(PK_NAME);
        if (dataIdStr == null || "".equals(dataIdStr)) {
            dataId = querySeqNexVal("SEQ_" + bm.getS_table_name());
        } else {
            dataId = Integer.valueOf(dataIdStr);
        }
        sql.append("insert into ").append(bm.getS_table_name());
        sql.append("(id");
        values.append(" values ('" + dataId + "'");
        for (IBusinessColumn c : columnList) {
            String s_column_name = c.getS_column_name();
            if (SqlEngineHelper.isNullString(s_column_name)) {
                continue;
            }
            if (c.getColType() == BusinessColumnEnum.C_Simple) {
                String is_autoincrement = c.getCol().getIsAutoIncrement();
                if (is_autoincrement.equals("True")) {
                    String trueSequenceName = s_column_name + "_SEQ";
                    int sequence_num = querySeqNexVal(trueSequenceName);
                    String seqContent = querySeqContent(c.getCol().getSequenceName());
                    String sequence_result = SqlEngineHelper.getAutoIncValue(c.getCol().getSequenceName()
                            , seqContent, sequence_num);
                    sql.append(",").append(s_column_name);
                    values.append(",").append("?");
                    dataList.add(sequence_result);
                    continue;
                }
            }
            String data = SqlEngineHelper.Obj2String(dataMap.get(s_column_name)).trim();
            if (SqlEngineHelper.isNullString(data)) {
                continue;
            }
            sql.append(",");
            sql.append(s_column_name);
            values.append(",").append(SqlEngineHelper.columnConvertInsUpSql(c));
            dataList.add(data);

        }
        sql.append(")").append(values).append(")");
        insertDataArr = SqlEngineHelper.list2stringArray(dataList);
        return sql.toString();
    }


}
