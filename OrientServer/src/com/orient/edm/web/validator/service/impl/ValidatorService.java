package com.orient.edm.web.validator.service.impl;

import com.orient.edm.web.validator.dao.IValidatorDao;
import com.orient.edm.web.validator.service.IValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by enjoy on 2016/3/17 0017.
 */
@Service
public class ValidatorService implements IValidatorService {

    @Autowired
    IValidatorDao validatorDao;

    @Override
    public Boolean validateUnique(String columnName, String modelName, String toValidateValue) {
        String filter = "AND " + columnName + " = '" + toValidateValue + "' ";
        Long count = validatorDao.queryCount(modelName, filter);
        return count==0 ? true : false;
    }
}
