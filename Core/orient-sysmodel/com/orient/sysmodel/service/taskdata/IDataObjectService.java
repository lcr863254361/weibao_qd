package com.orient.sysmodel.service.taskdata;

import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.sysmodel.service.IBaseService;

import java.util.List;

/**
 * 数据实例
 *
 * @author mengbin
 * @create 2016-07-13 下午4:17
 */
public interface IDataObjectService extends IBaseService<DataObjectEntity> {


    public DataObjectEntity getTopDataObjEntity(DataObjectEntity dataObj);

    public List<DataObjectEntity> getFamilyDataObjEntity(DataObjectEntity rootDataObj);
    public boolean getChildDataObjEntity(DataObjectEntity parentDataObj,List<DataObjectEntity> family);
}
