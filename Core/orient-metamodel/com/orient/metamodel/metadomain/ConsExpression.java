package com.orient.metamodel.metadomain;

/**
 * 数据关联约束
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 8, 2012
 */
public class ConsExpression extends AbstractConsExpression {

    public ConsExpression() {
    }

    /**
     * full constructor.
     *
     * @param cwmTables  the cwm tables
     * @param expression the expression
     * @param result     the result
     * @param pri        the pri
     */
    public ConsExpression(Table cwmTables, String expression, String result, Long pri) {
        super(cwmTables, expression, result, pri);
    }

    @Override
    public ConsExpression clone() throws CloneNotSupportedException {
        return (ConsExpression) super.clone();
    }

}
