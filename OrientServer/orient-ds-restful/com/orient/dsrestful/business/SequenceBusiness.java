package com.orient.dsrestful.business;

import com.orient.dsrestful.domain.sequence.SequenceRequestBean;
import com.orient.dsrestful.enums.SequenceResponseEnum;
import com.orient.metamodel.metadomain.Column;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.dsbean.CommonDataBean;
import com.orient.web.base.dsbean.CommonResponse;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by GNY on 2018/3/26
 */
@Component
public class SequenceBusiness extends BaseBusiness {

    @Autowired
    MetaDAOFactory metaDaoFactory;

    public CommonResponse updateSequence(SequenceRequestBean sequenceRequestBean) {
        CommonResponse retVal = new CommonResponse();
        CommonDataBean result = new CommonDataBean();
        IMetaModel metaModel = metaEngine.getMeta(false);
        ISchema schemaMap = metaModel.getISchema(sequenceRequestBean.getSchemaName(), sequenceRequestBean.getVersion());
        ITable tableMap = schemaMap.getTableByName(sequenceRequestBean.getTableName());
        try {
            if (tableMap != null) {
                IColumn columnMap = tableMap.getColumnByName(sequenceRequestBean.getColumnName());
                String seqName;
                String columnName = sequenceRequestBean.getColumnName();
                String columnId;
                if (columnMap == null) {
                    result.setStatus(SequenceResponseEnum.TYPE_COLUMN_NOT_EXIST.toString());
                    retVal.setResult(result);
                    retVal.setSuccess(false);
                    retVal.setMsg("字段不存在，无法初始化");
                    return retVal;
                } else {
                    columnId = columnMap.getId();
                    seqName = "SEQ_" + tableMap.getTableName();
                    //获得数据库连接对象
                    String tsn = getTsn();
                    int count = getCount(seqName, tsn);
                    if (count == 0) {
                        result.setStatus(SequenceResponseEnum.TYPE_SEQ_NOT_CREATE.toString());
                        retVal.setResult(result);
                        retVal.setSuccess(false);
                        retVal.setMsg("字段存在，但序列并没有创建");
                        return retVal;
                    }
                }
                String tableId = tableMap.getId();
                //获取该字段在现有数据类中是否有数据
                List tableList = metaDaoFactory.getTableDAO().queryList(tableId);
                if (tableList.isEmpty()) {
                    result.setStatus(SequenceResponseEnum.TYPE_COLUMN_NOT_EXIST.toString());
                    retVal.setResult(result);
                    retVal.setSuccess(false);
                    retVal.setMsg("当前表还没有插入数据，不需要更新序列");
                    return retVal;
                } else {
                    List<String> resultList = new ArrayList<>();
                    Object[] elementData = tableList.toArray();
                    for (int index = 0; index < elementData.length; index++) {
                        Object[] val = (Object[]) elementData[index];
                        if (val[3] != null) {
                            String tname = val[3].toString();
                            int i = metaDaoFactory.getTableDAO().getCount(columnName, tname);
                            if (i != 0) {
                                resultList.add(columnName);
                            }
                        }
                    }
                    if (!resultList.isEmpty()) {
                        result.setStatus(SequenceResponseEnum.TYPE_CAN_NOT_RESET.toString());
                        retVal.setResult(result);
                        retVal.setSuccess(false);
                        retVal.setMsg("字段已有数据存在无法重新初始化值");
                        return retVal;
                    } else {
                        this.dropSequence(seqName);
                        this.createSequence(seqName, sequenceRequestBean.getInitialValue(),sequenceRequestBean.getIntervalValue());
                        Column column = metaDaoFactory.getColumnDAO().findById(columnId);
                        column.setAutoAddDefault(Long.parseLong(String.valueOf(sequenceRequestBean.getInitialValue())));
                        metaDaoFactory.getColumnDAO().attachDirty(column);
                        result.setStatus(SequenceResponseEnum.TYPE_RESET_SUCCESS.toString());
                        retVal.setResult(result);
                        return retVal;
                    }
                }
            } else {
                result.setStatus(SequenceResponseEnum.TYPE_TABLE_NOT_EXIST.toString());
                retVal.setResult(result);
                retVal.setSuccess(false);
                retVal.setMsg("表不存在");
                return retVal;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(SequenceResponseEnum.TYPE_EXCEPTION.toString());
            retVal.setResult(result);
            retVal.setSuccess(false);
            retVal.setMsg("服务器异常");
            return retVal;
        }
    }

    /**
     * 获取数据库当前连接的数据库用户
     */
    private String getTsn() {
        BasicDataSource ds = (BasicDataSource) metaDaoFactory.getJdbcTemplate().getDataSource();
        return ds.getUsername().toUpperCase();//数据库用户名称
    }

    /**
     * 获取sequence的个数
     *
     * @param sequenceName
     * @param sequenceOwner
     * @return
     */
    public int getCount(String sequenceName, String sequenceOwner) {
        int count = 0;
        try {
            String searchSql = "SELECT COUNT(*) count FROM ALL_SEQUENCES WHERE SEQUENCE_NAME='" + sequenceName.toUpperCase() + "' AND SEQUENCE_OWNER='" + sequenceOwner + "'";
            List result = metaDaoFactory.getJdbcTemplate().queryForList(searchSql);
            if (result.size() != 0) {
                Map map = (Map) result.get(0);
                count = Integer.parseInt(map.get("count").toString());
            }
            return count;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return count;
        }
    }

    /**
     * 创建sequence
     *
     * @param seqName
     * @param value
     * @param interval
     */
    public void createSequence(String seqName, int value, int interval) {
        String sql = "CREATE SEQUENCE " + seqName + " INCREMENT BY " + interval + " START WITH " + value + " NOMAXVALUE NOCACHE";
        metaDaoFactory.getJdbcTemplate().execute(sql);
    }

    /**
     * 删除sequence
     */
    public void dropSequence(String seqName) {
        String sql = "DROP SEQUENCE " + seqName;
        metaDaoFactory.getJdbcTemplate().execute(sql);
    }

}
