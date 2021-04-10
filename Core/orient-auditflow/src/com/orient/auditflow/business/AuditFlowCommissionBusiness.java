package com.orient.auditflow.business;

import com.orient.sysmodel.domain.flow.FlowCommissionEntity;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.operationinterface.IUser;
import com.orient.sysmodel.service.flow.IFlowCommissionService;
import com.orient.web.base.BaseBusiness;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class AuditFlowCommissionBusiness extends BaseBusiness {
    @Autowired
    private IFlowCommissionService flowCommissionService;

    public List<IUser> getSlaveUsers(String pdid, String mainUserName) {
        List<IUser> retVal = new ArrayList<>();
        Set<String> slaveUserNames = flowCommissionService.getSlaveUsers(pdid, mainUserName);
        if(slaveUserNames.size()>0) {
            IRoleModel roleModel = roleEngine.getRoleModel(false);
            for(String slaveUserName : slaveUserNames) {
                IUser user = roleModel.getUserByUserName(slaveUserName);
                if(user != null) {
                    retVal.add(user);
                }
            }
        }
        return retVal;
    }

    public List<String> addSlaveUsers(String pdid, String mainUserName, String[] slaveUserNames) {
        List<String> retVal = new ArrayList<>();
        for(String slaveUserName : slaveUserNames) {
            int cnt = flowCommissionService.count(Restrictions.eq("pdid", pdid), Restrictions.eq("mainUserName", mainUserName), Restrictions.eq("slaveUserName", slaveUserName));
            if(cnt == 0) {
                FlowCommissionEntity entity = new FlowCommissionEntity();
                entity.setPdid(pdid);
                entity.setMainUserName(mainUserName);
                entity.setSlaveUserName(slaveUserName);
                flowCommissionService.save(entity);
                retVal.add(slaveUserName);
            }
        }
        return retVal;
    }

    public List<String> deleteSlaveUsers(String pdid, String mainUserName, String[] slaveUserNames) {
        List<String> retVal = new ArrayList<>();
        for(String slaveUserName : slaveUserNames) {
            FlowCommissionEntity entity = flowCommissionService.get(Restrictions.eq("pdid", pdid), Restrictions.eq("mainUserName", mainUserName), Restrictions.eq("slaveUserName", slaveUserName));
            if(entity != null) {
                flowCommissionService.delete(entity);
                retVal.add(slaveUserName);
            }
        }
        return retVal;
    }
}
