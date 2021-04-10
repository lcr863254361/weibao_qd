package com.orient.sqlengine.internal.svc;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.orient.sqlengine.api.SqlEngineException;
import com.orient.sqlengine.cmd.api.EDMCommand;
import com.orient.sqlengine.cmd.api.EDMCommandService;

/**
 * @ClassName EDMCommandServiceImpl
 * 业务命令执行类的实现
 * @author zhulc@cssrc.com.cn
 * @date Apr 18, 2012
 */

public class EDMCommandServiceImpl implements EDMCommandService {
	JdbcTemplate jdbcTemplate;
	private static final Logger log = Logger.getLogger(EDMCommandServiceImpl.class);

	@Override
	public <T> T execute(EDMCommand<T> command) {
		try {
			return command.execute(jdbcTemplate);
		} catch (Exception e) {
			log.info("exception while executing command " + command, e);
			throw new SqlEngineException("在执行命令: " + command + "发生异常", e);
		}
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
