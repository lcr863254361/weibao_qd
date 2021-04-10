package com.orient.sysmodel.service.file.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.file.IODSFileDao;
import com.orient.sysmodel.domain.file.OdsFile;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.file.IODSFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengbin
 * @create 2016-07-15 下午2:14
 */
@Service
public class ODSFileService extends BaseService<OdsFile> implements IODSFileService {

    @Autowired

    IODSFileDao oDSFileDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.oDSFileDao;
    }
}
