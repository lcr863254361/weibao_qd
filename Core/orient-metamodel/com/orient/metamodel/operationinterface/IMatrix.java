package com.orient.metamodel.operationinterface;

import java.util.List;

/**
 * 二维表格的接口函数
 *
 * @author mengbin
 * @Date Mar 16, 2012		9:36:27 AM
 */
public interface IMatrix {

    int TYPE_TABLE = 0;
    int TYPE_VIEW = 1;

    /**
     * 获取DS中用途包含编辑的字段集合
     */
    List<IColumn> getEditColumns();

    List<IColumn> getAddColumns();

    List<IColumn> getDetailColumns();

    List<IColumn> getListColumns();

    int getMatrixType();

    String getId();

    String getName();

    String getDisplayName();

    String getSecrecy();

    boolean isSecrecyEnable();

    String sortType();

    List<IColumn> getSortColumns();

    ISchema getSchema();

    ITable getMainTable();

    IColumn getColumnById(String Id);

    List<IColumn> getSortedColumns();

}

