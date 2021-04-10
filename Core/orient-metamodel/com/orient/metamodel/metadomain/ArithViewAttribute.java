package com.orient.metamodel.metadomain;

import java.util.Set;

/**
 * 统计视图统计参数项信息表
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 9, 2012
 */
public class ArithViewAttribute extends AbstractArithViewAttribute {

    public ArithViewAttribute() {
    }

    /**
     * Instantiates a new arith view attribute.
     *
     * @param isForArith     the is for arith
     * @param name           the name
     * @param arithId        the arith id
     * @param arithName      the arith name
     * @param column         the column
     * @param arithAttribute the arith attribute
     * @param view           the view
     */
    public ArithViewAttribute(String isForArith, String name, String arithId, String arithName, Column column, Set<ArithAttribute> arithAttribute, View view) {
        super(isForArith, name, arithId, arithName, column, arithAttribute, view);
    }

}
