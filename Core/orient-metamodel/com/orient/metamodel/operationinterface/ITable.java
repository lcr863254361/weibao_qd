package com.orient.metamodel.operationinterface;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 获取ITable信息的操作接口
 *
 * @author mengbin
 * @Date Mar 5, 2012		3:09:30 PM
 */
public interface ITable extends IMatrix {

    /**
     * 获取所有的数据性(包括父数据类的属性)不包含无效的和已经删除的
     *
     * @return List<IColumn>
     */
    List<IColumn> getColumns();

    /**
     * 获取所有的普通属性(包括父数据类的属性)
     *
     * @return List<IColumn>
     */
    List<IColumn> getCommonColumns();

    /**
     * 获取所有的关系属性(包括父数据类的属性)
     *
     * @return List<IColumn>
     */
    List<IColumn> getRelationColumns();

    /**
     * 获取该Table及其子Table
     *
     * @return List<ITable>
     */
    List<ITable> getAllTables();

    /**
     * 根据Column的id获取Column
     *
     * @param id
     */
    IColumn getColumnById(String id);

    /**
     * 根据Column的名称获取Column
     *
     * @param name
     * @return IColumn
     */
    IColumn getColumnByName(String name);

    /**
     * 返回数据类的父类，不存在则返回Null
     *
     * @return ITable 父表
     */
    ITable getParentTable();

    /**
     * 返回数据类在数据库存储的实际名称
     *
     * @return String 物理名称
     */
    String getTableName();

    /**
     * 返回表的显示名
     *
     * @return
     */
    String getDisplayName();

    /**
     * 返回数据类的类型值
     * 0.不共享 1.系统表共享 2.共享模型共享 3. 动态类数据共享
     *
     * @return String
     */
    String getType();

    /**
     * 返回数据类是否共享
     * True表明数据类共享，False获取Null表明不共享
     *
     * @return String
     */
    String getShareable();

    /**
     * 返回数据类的排序方式
     * ASC  正序
     * DESC 倒序
     *
     * @return String
     */
    String getPaiXu();

    /**
     * 返回数据类分几列展现
     * 默认是2列
     *
     * @return Long
     */
    Long getColSum();

    /**
     * 返回数据类是否启用密级
     * False不启用，True启用
     *
     * @return String
     */
    String getUseSecrecy();

    /**
     * 返回数据类的主键显示值Map
     * key：显示顺序。order属性
     * value:IColumn 字段
     *
     * @return Map
     */
    Map getPkColumns();

    /**
     * 返回类的组合唯一性约束Map
     * key：显示顺序。order属性
     * value:IColumn 字段
     *
     * @return Map
     */
    Map getUkColumns();

    /**
     * 返回数据类约束Set
     * Set元素:ConsExpression
     *
     * @return Set
     */
    Set getCwmConsExpressions();

    /**
     * 返回被视图引用的Set
     * Set元素:ViewRefColumn
     *
     * @return Set
     */
    Set getCwmViewRelationtables();

    /**
     * 表是否可能，新版本ds表都是真删除，该字段不需要了
     *
     * @return
     */
    Long getIsValid();

    Long getOrder();

}

