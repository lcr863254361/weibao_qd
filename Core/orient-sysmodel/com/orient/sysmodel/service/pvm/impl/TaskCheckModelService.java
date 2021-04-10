package com.orient.sysmodel.service.pvm.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.pvm.ITaskCheckModelDao;
import com.orient.sysmodel.domain.pvm.TaskCheckModel;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.pvm.ITaskCheckModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengbin
 * @create 2016-07-30 下午2:29
 */
@Service
public class TaskCheckModelService extends BaseService<TaskCheckModel> implements ITaskCheckModelService {

    @Autowired
    ITaskCheckModelDao taskCheckModelDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.taskCheckModelDao;
    }
}
