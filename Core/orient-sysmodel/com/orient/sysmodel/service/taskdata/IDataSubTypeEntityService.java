package com.orient.sysmodel.service.taskdata;

import com.orient.sysmodel.domain.taskdata.DataSubTypeEntity;
import com.orient.sysmodel.service.IBaseService;

/**
 * @author mengbin
 * @create 2016-07-04 下午7:25
 */
public interface IDataSubTypeEntityService extends IBaseService<DataSubTypeEntity> {

    public int getNextOrderNum();
}
