package com.orient.sqlengine.cmd.api;

/**
 * 业务模型的业务命令执行者
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 18, 2012
 */
public interface EDMCommandService {

    <T> T execute(EDMCommand<T> command);

}
