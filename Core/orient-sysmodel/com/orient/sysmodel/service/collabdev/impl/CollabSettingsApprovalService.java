package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabSettingsApprovalDao;
import com.orient.sysmodel.domain.collabdev.approval.CollabSettingsApproval;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabSettingsApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 17:05
 * @Version 1.0
 **/
@Service
public class CollabSettingsApprovalService extends BaseService<CollabSettingsApproval> implements ICollabSettingsApprovalService {

    @Autowired
    ICollabSettingsApprovalDao collabSettingsApprovalDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabSettingsApprovalDao;
    }

}
