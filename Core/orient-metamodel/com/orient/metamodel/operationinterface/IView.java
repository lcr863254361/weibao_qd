package com.orient.metamodel.operationinterface;

import java.util.List;

/**
 * 对视图的操作
 *
 * @author mengbin@cssrc.com.cn
 * @date Mar 16, 2012
 */
public interface IView extends IMatrix {

    /**
     * 获得所有返回的Column，不管该Column的属性(除是否显示和Valid属性)
     *
     * @return List<IColumn>
     */
    List<IColumn> getReturnColumnList();

    /**
     * 返回视图的主数据类
     *
     * @return ITable
     */
    ITable getTable();

    /**
     * 返回视图的创建Sql语句
     *
     * @return String
     */
    String getViewSql();

    /**
     * 返回视图的类型
     * 1：查询视图 2：统计视图
     *
     * @return Long
     */
    Long getType();

    IColumn getColumnById(String id);

}

