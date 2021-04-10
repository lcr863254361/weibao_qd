package com.orient.sysmodel.service.file.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.file.IFileGroupItemDao;
import com.orient.sysmodel.domain.file.CwmFileGroupItemEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.file.IFileGroupItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文件分组项Service
 *
 * @author enjoy
 * @creare 2016-04-28 14:39
 */
@Service
public class FileGroupItemService extends BaseService<CwmFileGroupItemEntity> implements IFileGroupItemService {

    @Autowired
    IFileGroupItemDao fileGroupItemDao;

    @Override
    public IBaseDao getBaseDao() {
        return fileGroupItemDao;
    }
}
