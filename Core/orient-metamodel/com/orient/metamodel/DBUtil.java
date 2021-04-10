package com.orient.metamodel;

import com.alibaba.druid.pool.DruidDataSource;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by GNY on 2018/3/28
 */
@Component
public class DBUtil {

    @Autowired
    protected MetaDAOFactory metaDAOFactory;

    protected String tsn;

    /**
     * 获取数据库当前连接的数据库用户
     *
     * @return
     */
    public String getTsn() {
        if (tsn == null) {
            DruidDataSource ds = (DruidDataSource) metaDAOFactory.getJdbcTemplate().getDataSource();
            tsn = ds.getUsername().toUpperCase();//数据库用户名称
        }
        return tsn;
    }

}
