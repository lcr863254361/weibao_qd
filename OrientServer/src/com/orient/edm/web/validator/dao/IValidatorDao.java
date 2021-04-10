package com.orient.edm.web.validator.dao;

/**
 * Created by enjoy on 2016/3/17 0017.
 */
public interface IValidatorDao {

    Long queryCount(String modelName, String filter);
}
