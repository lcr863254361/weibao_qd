package com.orient.sysmodel.service.flow.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.flow.ITaskDataRelationDao;
import com.orient.sysmodel.domain.flow.TaskDataRelation;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.flow.ITaskDataRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Task Data Relation Service's implementation
 *
 * @author Seraph
 *         2016-06-30 下午5:00
 */
@Service
public class TaskDataRelationService  extends BaseService<TaskDataRelation> implements ITaskDataRelationService {

    @Autowired
    ITaskDataRelationDao taskDataRelationDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.taskDataRelationDao;
    }
}
