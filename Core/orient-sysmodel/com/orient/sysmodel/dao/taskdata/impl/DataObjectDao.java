package com.orient.sysmodel.dao.taskdata.impl;

import com.orient.sysmodel.dao.impl.BaseHibernateDaoImpl;
import com.orient.sysmodel.dao.taskdata.IDataObjectDao;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 数据实例
 *
 * @author mengbin
 * @create 2016-07-13 下午4:19
 */
@Repository
public class DataObjectDao extends BaseHibernateDaoImpl implements IDataObjectDao {
    public DataObjectEntity getTopDataObjEntity(DataObjectEntity dataObj) {

        //TODO: 修改查询获取顶部的DataObj的SQL语句
        DataObjectEntity topDataObj = (DataObjectEntity)getSession().getSessionFactory().getCurrentSession().createSQLQuery(
                "select * from CWM_DATAOBJECT where PARENTDATAOBJECTID = 0 START WITH ID ="+dataObj.getId()+" Connect by  ID = prior PARENTDATAOBJECTID").addEntity(DataObjectEntity.class).uniqueResult();

      //  DataObjectEntity topDataObject = new DataObjectEntity();
        return topDataObj;

    }

}
