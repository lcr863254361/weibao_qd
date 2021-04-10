package com.orient.metamodel.metadomain;

/**
 * 算法参数定义模型抽象类，供hibernate持久化使用.
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 9, 2012
 */
public abstract class AbstractArithAttribute extends BaseMetaBean {

    /**
     * 主键id.
     */
    private String id;

    /**
     * 顺序号.
     */
    private Long order;

    /**
     * 参数定义的数据类型.
     */
    private String type;

    /**
     * 参数所定制的字段.
     */
    private Column column;

    /**
     * 参数定义的常量.
     */
    private String value;

    /**
     * 所属统计属性的字段.
     */
    private Column acolumn;

    public AbstractArithAttribute() {
    }

    /**
     * Instantiates a new abstract arith attribute.
     *
     * @param order   顺序号
     * @param type    数据类型
     * @param column  参数所定义的字段
     * @param value   参数的常量
     * @param acolumn 所属统计属性的字段
     */
    public AbstractArithAttribute(Long order, String type, Column column, String value, Column acolumn) {
        super();
        this.order = order;
        this.type = type;
        this.column = column;
        this.value = value;
        this.acolumn = acolumn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Column getAcolumn() {
        return acolumn;
    }

    public void setAcolumn(Column acolumn) {
        this.acolumn = acolumn;
    }

}