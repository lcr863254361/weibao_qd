package com.orient.metamodel.operationinterface;

import java.util.List;

/**
 * Schema的操作接口
 *
 * @author mengbin
 * @Date Mar 6, 2012		1:46:48 PM
 */
public interface ISchema {

    long TYPE_COMMON = 0;
    long TYPE_SHARE = 1;

    /**
     * 获得这个Schema下面的所有Table（包括子Table）
     *
     * @return List<ITable>
     */
    List<ITable> getAllTables();

    List<IView> getAllViews();

    /**
     * 根据id获取Table
     *
     * @param id
     * @return ITable
     */
    ITable getTableById(String id);

    /**
     * 根据name获取Table
     *
     * @param name
     * @return ITable
     */
    ITable getTableByName(String name);

    /**
     * 根据displayName获取Table
     *
     * @param displayName
     * @return ITable
     */
    ITable getTableByDisplayName(String displayName);

    /**
     * 根据name获取View
     *
     * @param name
     * @return IView
     */
    IView getViewByName(String name);

    /**
     * 根据id获取View
     *
     * @param id
     * @return IView
     */
    IView getViewById(String id);

    /**
     * 根据name获取Restriction
     *
     * @return IRestriction
     */
    IRestriction getRestrictionByName(String name);

    /**
     * 根据Id获取Restriction
     *
     * @param id
     * @return IRestriction
     */
    IRestriction getRestrictionById(String id);

    /**
     * 返回Schema的ID
     *
     * @return String
     */
    String getId();

    /**
     * 返回Schema的IsDelete
     *
     * @return Long
     */
    Integer getIsdelete();

    /**
     * 返回Schema的IsLock
     *
     * @return Long
     */
    Integer getIsLock();

    /**
     * 返回Schema的name
     *
     * @return String
     */
    String getName();

    /**
     * 返回Schema的version
     *
     * @return String
     */
    String getVersion();

    /**
     * 返回Schema的type
     *
     * @return String
     */
    String getType();

}

