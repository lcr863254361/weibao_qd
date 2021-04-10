package com.orient.metamodel.metadomain;

/**
 * 数据类枚举约束的关联数据类信息表
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 7, 2012
 */
public class RelationTableEnum extends AbstractRelationTableEnum {


    @Override
    public RelationTableEnum clone() throws CloneNotSupportedException {
        return (RelationTableEnum) super.clone();
    }


    public RelationTableEnum() {
    }

    /**
     * @param cwmTableEnum --对应的数据类枚举约束
     * @param cwmTables    --关联的数据类Id
     * @throws
     */
    public RelationTableEnum(TableEnum cwmTableEnum, Table cwmTables) {
        super(cwmTableEnum, cwmTables);
    }

}
