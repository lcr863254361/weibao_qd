/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.sysmodel.service.file.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.file.IFileGroupItemDao;
import com.orient.sysmodel.dao.file.IHadoopFileDao;
import com.orient.sysmodel.domain.file.CwmFileGroupItemEntity;
import com.orient.sysmodel.domain.file.HadoopFileEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.file.IHadoopFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mengbin on 16/5/9.
 * Purpose:
 * Detail:
 */
@Service
public class HadoopFileService extends BaseService<HadoopFileEntity> implements IHadoopFileService {

    @Autowired
    IHadoopFileDao hadoopFileDao;

    @Override
    public IBaseDao getBaseDao() {
        return hadoopFileDao;
    }
}
