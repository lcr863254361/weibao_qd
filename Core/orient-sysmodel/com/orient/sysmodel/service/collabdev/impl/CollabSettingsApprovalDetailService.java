package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabSettingsApprovalDetailDao;
import com.orient.sysmodel.domain.collabdev.approval.CollabSettingsApprovalDetail;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabSettingsApprovalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 17:03
 * @Version 1.0
 **/
@Service
public class CollabSettingsApprovalDetailService extends BaseService<CollabSettingsApprovalDetail> implements ICollabSettingsApprovalDetailService {

    @Autowired
    ICollabSettingsApprovalDetailDao collabSettingsApprovalDetailDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabSettingsApprovalDetailDao;
    }

}
