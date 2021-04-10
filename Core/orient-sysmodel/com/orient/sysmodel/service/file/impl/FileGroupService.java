package com.orient.sysmodel.service.file.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.file.IFileGroupDao;
import com.orient.sysmodel.domain.file.CwmFileGroupEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.file.IFileGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文件分组操作service
 *
 * @author enjoy
 * @creare 2016-04-28 14:37
 */
@Service
public class FileGroupService extends BaseService<CwmFileGroupEntity> implements IFileGroupService {

    @Autowired
    IFileGroupDao fileGroupDao;

    @Override
    public IBaseDao getBaseDao() {
        return fileGroupDao;
    }
}
