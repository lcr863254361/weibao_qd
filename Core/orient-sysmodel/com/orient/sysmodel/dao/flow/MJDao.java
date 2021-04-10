package com.orient.sysmodel.dao.flow;

import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ${DESCRIPTION}
 *
 * @author js_liuyangchao@163.com
 * @create 2017-02-17 10:56
 */
@Repository
public class MJDao {

    @Autowired
    protected MetaDAOFactory metaDAOFactory;

    /**
     * 更新已经插入的带有密级字段的表中的系统密级字段
     * @param tableName         更新的表名称
     * @param secrecyValue      密级内容（10级）
     * @param Id                       表的ID
     * @return
     */
    public int CopyMJToSysSecrecy(String tableName, String secrecyValue, String Id){
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE ")
                .append(tableName)
                .append(" SET SYS_SECRECY = '")
                .append(secrecyValue)
                .append("' WHERE ID = ")
                .append(Id);
        int result = metaDAOFactory.getJdbcTemplate().update(sql.toString());
        return result;
    }

}
