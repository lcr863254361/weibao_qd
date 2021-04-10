package com.orient.sysmodel.dao.taskdata.impl;

import com.orient.sysmodel.dao.impl.BaseHibernateDaoImpl;
import com.orient.sysmodel.dao.taskdata.IDataSubTypeEntityDao;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 子研发数据类型
 *
 * @author mengbin
 * @create 2016-07-04 下午7:25
 */
@Repository
public class DataSubTypeEntityDao extends BaseHibernateDaoImpl implements IDataSubTypeEntityDao {


    /**
     * 获取最新的orderNumber,通过sequence [seq_order]获取
     * @return
     */
    public int getNextOrderNum() {


        BigDecimal od = (BigDecimal)getSession().getSessionFactory().getCurrentSession().createSQLQuery(
                "select seq_order.nextval from dual").uniqueResult();
        return od.intValue();

    }
}
