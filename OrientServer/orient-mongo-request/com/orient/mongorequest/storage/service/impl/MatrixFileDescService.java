package com.orient.mongorequest.storage.service.impl;

import com.orient.mongorequest.domain.MatrixFileDesc;
import com.orient.mongorequest.storage.dao.IMatrixFileDescDao;
import com.orient.mongorequest.storage.service.IMatrixFileDescService;
import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatrixFileDescService extends BaseService<MatrixFileDesc> implements IMatrixFileDescService {

    @Autowired
    IMatrixFileDescDao matrixFileDescDao;

    @Override
    public IBaseDao getBaseDao() {
        return matrixFileDescDao;
    }

}

