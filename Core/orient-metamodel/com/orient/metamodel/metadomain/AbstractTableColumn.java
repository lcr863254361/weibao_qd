package com.orient.metamodel.metadomain;

/**
 * 数据类的复合属性信息
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 7, 2012
 */
public abstract class AbstractTableColumn extends BaseMetaBean {

    /**
     * The id.
     */
    private String id;

    /**
     * 所属数据类.
     */
    private Table table;

    /**
     * 数据字段
     */
    private Column column;

    /**
     * 复合属性类型
     * 0表示为主键显示值，1表示为唯一性组合约束，2表示为排序属性，3表示为数据表字段前后展现顺序
     */
    private Long type;

    /**
     * 符合属性的顺序.
     */
    private Long order;


    /**
     * Instantiates a new abstract table column.
     */
    public AbstractTableColumn() {
        super();
    }

    /**
     * Instantiates a new abstract table column.
     *
     * @param id     the id
     * @param table  the table
     * @param column the column
     * @param type   the type
     * @param order  the order
     */
    public AbstractTableColumn(String id, Table table, Column column, Long type, Long order) {
        this.id = id;
        this.table = table;
        this.column = column;
        this.type = type;
        this.order = order;
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

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

}
