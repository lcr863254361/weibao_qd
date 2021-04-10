package com.orient.sysmodel.service.taskdata.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.taskdata.IDataObjectDao;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.taskdata.IDataObjectService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author mengbin
 * @create 2016-07-13 下午4:18
 */
@Service
public class DataObjectService extends BaseService<DataObjectEntity> implements IDataObjectService {

    @Autowired
    IDataObjectDao dataObjectDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.dataObjectDao;
    }

    @Override
    public DataObjectEntity getTopDataObjEntity(DataObjectEntity dataObj){
        return dataObjectDao.getTopDataObjEntity(dataObj);

    }


    @Override
    public List<DataObjectEntity> getFamilyDataObjEntity(DataObjectEntity rootDataObj) {

        List<DataObjectEntity> family =  new ArrayList<>();
        family.add(rootDataObj);
        getChildDataObjEntity(rootDataObj,family);

        return family;
    }

    @Override
    public boolean getChildDataObjEntity(DataObjectEntity parentDataObj,List<DataObjectEntity> family){
        List<Criterion> filters = new ArrayList<>();
        filters.add(Restrictions.eq("parentdataobjectid",parentDataObj.getId()));
        List<DataObjectEntity>  children =  this.list(filters.toArray(new Criterion[0]));
        for (DataObjectEntity loop: children){
            family.add(loop);
            getChildDataObjEntity(loop,family);

        }
        return true;
    }

}
