package com.orient.sysmodel.dao.taskdata;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;

/**
 * @author mengbin
 * @create 2016-07-13 下午4:19
 */
public interface IDataObjectDao extends IBaseDao {
    public DataObjectEntity getTopDataObjEntity(DataObjectEntity dataObj);
}
