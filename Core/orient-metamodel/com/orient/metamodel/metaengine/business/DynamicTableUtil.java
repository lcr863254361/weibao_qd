package com.orient.metamodel.metaengine.business;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author mengbin
 * @Date Mar 26, 2012		11:00:10 AM
 * @see
 */
public class DynamicTableUtil {

    /**
     * 删除动态表数据.
     *
     * @param jdbcTemplate the jdbc template
     * @param tablename    表名
     * @param clear        是否删除动态表新建的表空间
     */
    @SuppressWarnings("unchecked")
    public static void deleteDynamicData(JdbcTemplate jdbcTemplate, String tablename, boolean clear) {
        try {
            BasicDataSource ds = (BasicDataSource) jdbcTemplate.getDataSource();
            String name = ds.getUsername().toUpperCase();//数据库用户名称
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT S_TABLE_NAME FROM CWM_TABLES WHERE MAP_TABLE IN(")
                    .append("SELECT ID FROM CWM_TABLES WHERE UPPER(S_TABLE_NAME) ='").append(tablename.toUpperCase())
                    .append("') AND SHARE_TYPE ='3' AND IS_VALID =4");
            List<Map<String, Object>> idList = jdbcTemplate.queryForList(sb.toString());
            if (idList != null && !idList.isEmpty()) {
                for (int i = 0; i < idList.size(); i++) {
                    String s_table_name = (idList.get(i)).get("S_TABLE_NAME").toString();    //动态表的S_TABLE_NAME，是父表的S_TABLE_NAME加‘_D’
                    deleteDynamicData(jdbcTemplate, s_table_name, clear);    //递归删除动态列
                    //删除动态表的序列Sequence
                    int a = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ALL_SEQUENCES WHERE SEQUENCE_NAME='SEQ_" + s_table_name.toUpperCase() + "' AND SEQUENCE_OWNER='" + name + "'", Integer.class);
                    if (a != 0) {
                        jdbcTemplate.execute("DROP SEQUENCE SEQ_" + s_table_name.toUpperCase());
                    }
                    //删除分区表
                    a = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME='" + s_table_name.toUpperCase() + "'", Integer.class);
                    if (a != 0) {
                        jdbcTemplate.execute("DROP TABLE " + s_table_name + " CASCADE CONSTRAINTS");
                    }
                    if (clear) {
                        //删除分区表所在表空间
                        a = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM V$TABLESPACE WHERE UPPER(NAME) ='" + s_table_name.toUpperCase() + "'", Integer.class);
                        if (a != 0) {
                            jdbcTemplate.execute("DROP TABLESPACE " + s_table_name + " INCLUDING CONTENTS AND DATAFILES CASCADE CONSTRAINTS");
                        }
                        jdbcTemplate.execute("DELETE FROM CWM_TABLES WHERE UPPER(S_TABLE_NAME) ='" + s_table_name.toUpperCase() + "'");
                    }
                }
                jdbcTemplate.execute("DELETE FROM CWM_PARTITION_REMARK WHERE TABLE_NAME ='" + tablename.toUpperCase() + "'");
                jdbcTemplate.execute("DELETE FROM CWM_DYNAMIC_RELATION WHERE TABLE_NAME ='" + tablename.toUpperCase() + "'");
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

}

