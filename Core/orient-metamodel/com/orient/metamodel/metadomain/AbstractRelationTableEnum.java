package com.orient.metamodel.metadomain;

/**
 * 数据类枚举约束的关联数据类信息表
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 7, 2012
 */
public abstract class AbstractRelationTableEnum extends BaseMetaBean {

    /**
     * The id.
     */
    private String id;

    /**
     * 对应的数据类枚举约束
     */
    private TableEnum cwmTableEnum;

    /**
     * 枚举值所在的数据类.
     */
    private Table cwmTables;

    /**
     * 关联数据类的前后顺序
     */
    private Long order;

    /**
     * 源数据类  （由那个数据类关联进来的）
     */
    private Table originTable;

    /**
     * 关系属性所属的数据类.
     */
    private Table fromtable;

    /**
     * 关系数据所关联的数据类
     */
    private Table totable;

    /**
     * 关联类型：关联类型决定关联数据类的关联SQL语句该如何写
     */
    private String type;

    public AbstractRelationTableEnum() {
    }

    /**
     * @param cwmTableEnum --对应的数据类枚举约束
     * @param cwmTables    --关联的数据类
     * @throws
     */
    public AbstractRelationTableEnum(TableEnum cwmTableEnum, Table cwmTables) {
        this.cwmTableEnum = cwmTableEnum;
        this.cwmTables = cwmTables;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TableEnum getCwmTableEnum() {
        return cwmTableEnum;
    }

    public void setCwmTableEnum(TableEnum cwmTableEnum) {
        this.cwmTableEnum = cwmTableEnum;
    }

    public Table getCwmTables() {
        return cwmTables;
    }

    public void setCwmTables(Table cwmTables) {
        this.cwmTables = cwmTables;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Table getOriginTable() {
        return originTable;
    }

    public void setOriginTable(Table originTable) {
        this.originTable = originTable;
    }

    public Table getFromtable() {
        return fromtable;
    }

    public void setFromtable(Table fromtable) {
        this.fromtable = fromtable;
    }

    public Table getTotable() {
        return totable;
    }

    public void setTotable(Table totable) {
        this.totable = totable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}