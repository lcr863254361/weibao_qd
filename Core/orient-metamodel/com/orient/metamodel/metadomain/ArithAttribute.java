package com.orient.metamodel.metadomain;

/**
 * 算法参数
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 9, 2012
 */
public class ArithAttribute extends AbstractArithAttribute {

    public ArithAttribute() {
    }

    /**
     * @param order   the order
     * @param type    the type
     * @param column  the column
     * @param value   the value
     * @param acolumn the acolumn
     */
    public ArithAttribute(Long order, String type, Column column, String value, Column acolumn) {
        super(order, type, column, value, acolumn);
    }

}
