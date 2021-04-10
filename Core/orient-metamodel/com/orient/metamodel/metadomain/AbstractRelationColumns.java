package com.orient.metamodel.metadomain;

/**
 * 关系属性信息表
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 8, 2012
 */
public abstract class AbstractRelationColumns extends BaseMetaBean {

    /**
     * The id.
     */
    private String id;

    /**
     * 关系属性所属的数据类
     */
    private Table table;

    /**
     * 逆向关系属性名称 (displayName)
     */
    private String refColumnName;

    /**
     * 该关系属性对应的数据字段
     */
    private Column cwmTabColumnsByColumnId;

    /**
     * 关系类型，1表示一对一，2表示一对多，3表示多对一，4表示多对多，4表示多对多.
     */
    private Long relationtype;

    /**
     * 表示为所有权，1表示松耦合，2表示自有，3表示紧耦合，4表示无所有权
     */
    private Long ownership;

    /**
     * 是否在该关系属性所属的数据表中建立一个字段，用来记录其关联表的ID，0表示不建，1表示建
     */
    private Long isFK;

    /**
     * 关联的数据类.
     */
    private Table refTable;

    /**
     * 下属关系属性ID.（级联关系属性的下级关系属性的ID）
     */
    private Column subRelationColumn;

    /**
     * 上级关系属性ID
     */
    private Column supRelationColumn;

    /**
     * 级联关系属性的关系类别
     */
    private String category;

    /**
     * The column name. (貌似没有用)
     */
    private String columnName;

    /**
     * 是否下拉列表显示.
     */
    private String islistdisplay;

    public AbstractRelationColumns() {
    }

    /**
     *
     * @param cwmTables                  --关系属性所属的数据类
     * @param cwmTabColumnsByRefColumnId --该关系属性对应的数据字段
     * @param relationtype               --关系类型，0表示一对一，1表示一对多，2表示多对一，3表示多对多，4表示多对多
     * @param ownership                  --表示为所有权，0表示松耦合，1表示自由，2表示紧耦合，3表示无所有权
     * @param isFK                       --是否在该关系属性所属的数据表中建立一个字段，用来记录其关联表的ID，0表示不建，1表示建
     */
    public AbstractRelationColumns(Table cwmTables, Column cwmTabColumnsByRefColumnId, Long relationtype, Long ownership, Long isFK) {
        this.table = cwmTables;
        this.cwmTabColumnsByColumnId = cwmTabColumnsByRefColumnId;
        this.relationtype = relationtype;
        this.ownership = ownership;
        this.isFK = isFK;
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

    public String getRefColumnName() {
        return refColumnName;
    }

    public void setRefColumnName(String refColumnName) {
        this.refColumnName = refColumnName;
    }

    public Column getCwmTabColumnsByColumnId() {
        return cwmTabColumnsByColumnId;
    }

    public void setCwmTabColumnsByColumnId(Column cwmTabColumnsByColumnId) {
        this.cwmTabColumnsByColumnId = cwmTabColumnsByColumnId;
    }

    public Long getRelationtype() {
        return relationtype;
    }

    public void setRelationtype(Long relationtype) {
        this.relationtype = relationtype;
    }

    public Long getOwnership() {
        return ownership;
    }

    public void setOwnership(Long ownership) {
        this.ownership = ownership;
    }

    public Long getIsFK() {
        return isFK;
    }

    public void setIsFK(Long isFK) {
        this.isFK = isFK;
    }

    public Table getRefTable() {
        return refTable;
    }

    public void setRefTable(Table refTable) {
        this.refTable = refTable;
    }

    public Column getSubRelationColumn() {
        return subRelationColumn;
    }

    public void setSubRelationColumn(Column subRelationColumn) {
        this.subRelationColumn = subRelationColumn;
    }

    public Column getSupRelationColumn() {
        return supRelationColumn;
    }

    public void setSupRelationColumn(Column supRelationColumn) {
        this.supRelationColumn = supRelationColumn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getIslistdisplay() {
        return islistdisplay;
    }

    public void setIslistdisplay(String islistdisplay) {
        this.islistdisplay = islistdisplay;
    }

}