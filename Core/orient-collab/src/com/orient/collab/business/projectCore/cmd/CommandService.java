package com.orient.collab.business.projectCore.cmd;

/**
 * a command service to execute command
 *
 * @author Seraph
 *         2016-07-21 上午11:37
 */
public interface CommandService {

    <T> T execute(Command<T> cmd) throws Exception;
}
