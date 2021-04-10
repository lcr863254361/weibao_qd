package com.orient.sysmodel.service.pvm.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.pvm.ITaskCheckRelationDao;
import com.orient.sysmodel.domain.pvm.TaskCheckRelation;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.pvm.ITaskCheckRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengbin
 * @create 2016-07-30 下午2:37
 */
@Service
public class TaskCheckRelationService extends BaseService<TaskCheckRelation> implements ITaskCheckRelationService {

    @Autowired
    ITaskCheckRelationDao taskCheckRelationDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.taskCheckRelationDao;
    }
}
