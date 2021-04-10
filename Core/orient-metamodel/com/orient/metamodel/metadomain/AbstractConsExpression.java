package com.orient.metamodel.metadomain;

/**
 * 数据类的数据关联约束
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 8, 2012
 */
public abstract class AbstractConsExpression extends BaseMetaBean {

    /**
     * Id
     */
    private String id;

    /**
     * 所属的数据类..
     */
    private Table table;

    /**
     * 表达式
     */
    private String expression;

    /**
     * 错误的返回结果.
     */
    private String result;

    /**
     * 校验有限级.
     */
    private Long pri;

    /**
     * 顺序.
     */
    private Long order;

    public AbstractConsExpression() {
    }

    /**
     * @param cwmTables  the cwm tables
     * @param expression the expression
     * @param result     the result
     * @param pri        the pri
     */
    public AbstractConsExpression(Table cwmTables, String expression, String result, Long pri) {
        this.table = cwmTables;
        this.expression = expression;
        this.result = result;
        this.pri = pri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getPri() {
        return pri;
    }

    public void setPri(Long pri) {
        this.pri = pri;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

}