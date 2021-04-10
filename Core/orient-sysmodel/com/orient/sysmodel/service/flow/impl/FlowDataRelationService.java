package com.orient.sysmodel.service.flow.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.flow.IFlowDataRelationDao;
import com.orient.sysmodel.dao.sys.IDataBackDao;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.domain.sys.CwmBackEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.sysmodel.service.sys.IDataBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Flow Data Relation Service's implementation
 *
 * @author Seraph
 *         2016-06-30 下午4:01
 */
@Service
public class FlowDataRelationService extends BaseService<FlowDataRelation> implements IFlowDataRelationService {

    @Autowired
    IFlowDataRelationDao flowDataRelationDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.flowDataRelationDao;
    }
}
