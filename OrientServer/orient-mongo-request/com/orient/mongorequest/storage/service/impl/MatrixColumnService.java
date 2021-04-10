package com.orient.mongorequest.storage.service.impl;

import com.orient.mongorequest.domain.MatrixColumn;
import com.orient.mongorequest.storage.dao.IMatrixColumnDao;
import com.orient.mongorequest.storage.service.IMatrixColumnService;
import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-06-04 14:44
 */
@Service
public class MatrixColumnService extends BaseService<MatrixColumn> implements IMatrixColumnService {

    @Autowired
    IMatrixColumnDao matrixColumnDao;


    @Override
    public IBaseDao getBaseDao() {
        return matrixColumnDao;
    }

}
