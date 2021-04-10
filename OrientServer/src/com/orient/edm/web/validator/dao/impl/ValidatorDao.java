package com.orient.edm.web.validator.dao.impl;

import com.orient.edm.web.validator.dao.IValidatorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by enjoy on 2016/3/17 0017.
 */
@Repository
public class ValidatorDao implements IValidatorDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Long queryCount(String modelName,String filter) {
        StringBuffer querySql = new StringBuffer("SELECT COUNT(*) FROM ").append(modelName);
        querySql.append(" WHERE 1=1 ");
        querySql.append(filter);
        return this.jdbcTemplate.queryForObject(querySql.toString(),Long.class);
    }
}
