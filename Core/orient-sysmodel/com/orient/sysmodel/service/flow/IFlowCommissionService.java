package com.orient.sysmodel.service.flow;

import com.orient.sysmodel.domain.flow.FlowCommissionEntity;
import com.orient.sysmodel.service.IBaseService;

import java.util.List;
import java.util.Set;

public interface IFlowCommissionService extends IBaseService<FlowCommissionEntity> {
    Set<String> getSlaveUsers(String pdid, String mainUserName);
}
