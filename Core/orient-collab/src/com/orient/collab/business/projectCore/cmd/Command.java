package com.orient.collab.business.projectCore.cmd;

/**
 * a project/plan related command
 *
 * @author Seraph
 *         2016-07-21 上午11:37
 */
public interface Command<T> {
    T execute() throws Exception;
}
