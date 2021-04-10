package com.orient.sqlengine.cmd.api;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;

/**
 * 业务模型命令
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 17, 2012
 */
public interface EDMCommand<T> extends Serializable {

    T execute(JdbcTemplate jdbc) throws Exception;

}
