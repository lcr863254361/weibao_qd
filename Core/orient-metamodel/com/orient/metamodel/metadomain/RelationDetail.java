package com.orient.metamodel.metadomain;

/**
 * 描述关系的详细情况（没有对应的表），在视图（view）中使用到了。在数据类约束(TableEnum)中使用到了
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 8, 2012
 */
public class RelationDetail extends AbstractRelationDetail {

    public RelationDetail() {
    }

    /**
     * @param fromTable --引入关联数据类的源数据类
     * @param toTable   --关联数据类（视图包含的数据类）
     * @param type      --关系类型
     */

    public RelationDetail(Table fromTable, Table toTable, String type) {
        super(fromTable, toTable, type);
    }

}
