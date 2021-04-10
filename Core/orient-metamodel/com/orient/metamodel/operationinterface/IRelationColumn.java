package com.orient.metamodel.operationinterface;

import com.orient.metamodel.metadomain.Table;

/**
 * @author mengbin@cssrc.com.cn
 * @date Mar 17, 2012
 */
public interface IRelationColumn {

    /**
     * @Fields RELATIONTYPE_ONE2ONE : 一对一
     */
    int RELATIONTYPE_ONE2ONE = 1;

    /**
     * @Fields RELATIONTYPE_ONE2MANY : 一对多
     */
    int RELATIONTYPE_ONE2MANY = 2;

    /**
     * @Fields RELATIONTYPE_MANY2ONE : 多对一
     */
    int RELATIONTYPE_MANY2ONE = 3;

    /**
     * @Fields RELATIONTYPE_MANY2MANY : 多对多
     */
    int RELATIONTYPE_MANY2MANY = 4;

    /**
     * @Fields OWNER_RELAX : 松耦合
     */
    int OWNER_RELAX = 1;

    /**
     * @Fields OWNER_SELF : 自有
     */
    int OWNER_SELF = 2;

    /**
     * @Fields OWNER_TIGHTEN : 紧耦合
     */
    int OWNER_TIGHTEN = 3;

    /**
     * @Fields OWNER_NONE : 无所有权
     */
    int OWNER_NONE = 4;

    /**
     * *获取关系类型
     *
     * @return int
     */
    int getRelationType();

    /**
     * 关系属性所关联的数据类
     *
     * @return ITable
     */
    ITable getRefTable();

    /**
     * 关系属性所关联的字段
     *
     * @return IColumn
     */
    IColumn getRefColumn();

    /**
     * 关系属性所属的字段
     *
     * @return IColumn
     */
    IColumn getBelongColumn();

    /**
     * 获取关系属性的所有权
     *
     * @return int
     */
    int getOwner();

    /**
     * 返回是否在该关系属性所属的数据表中建立一个字段，
     * 0表示不建，1表示建
     *
     * @return Long
     */
    Long getIsFK();

    /**
     * 返回字段的所有权
     * 1表示松耦合，2表示自有，3表示紧耦合，4表示无所有权
     *
     * @return Long
     */
    Long getOwnership();

    /**
     * 返回该字段关联的数据类
     *
     * @return Table
     */
    Table getTable();

    String getId();

}

