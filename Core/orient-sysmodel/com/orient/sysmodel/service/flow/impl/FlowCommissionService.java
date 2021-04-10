package com.orient.sysmodel.service.flow.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.flow.IFlowCommissionDao;
import com.orient.sysmodel.domain.flow.FlowCommissionEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.flow.IFlowCommissionService;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FlowCommissionService extends BaseService<FlowCommissionEntity> implements IFlowCommissionService {

    @Autowired
    IFlowCommissionDao flowCommissionDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.flowCommissionDao;
    }

    @Override
    public Set<String> getSlaveUsers(String pdid, String mainUserName) {
        Set<String> retVal = new HashSet<>();
        List<FlowCommissionEntity> flowCommissions = list(Restrictions.eq("pdid", pdid), Restrictions.eq("mainUserName", mainUserName));
        if(flowCommissions!=null && flowCommissions.size()>0) {
            for(FlowCommissionEntity flowCommission : flowCommissions) {
                retVal.add(flowCommission.getSlaveUserName());
            }
        }
        return retVal;
    }
}
