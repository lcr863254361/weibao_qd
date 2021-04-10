package com.orient.edm.web.validator.service;

/**
 * Created by enjoy on 2016/3/17 0017.
 */
public interface IValidatorService {
    Boolean validateUnique(String columnName, String modelName, String toValidateValue);
}
