package com.orient.metamodel.metadomain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 数据类枚举约束信息
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 7, 2012
 */
public abstract class AbstractTableEnum extends BaseMetaBean {

    /**
     * The id.
     */
    private String id;

    /**
     * 对应的约束
     */
    private Restriction cwmRestriction;

    /**
     * 枚举字段所在的table
     */
    private Table table;

    /**
     * 枚举值所在的数据字段
     */
    private Column column;

    /**
     * 约束Id
     */
    private String restrictionId;

    /**
     * 过滤枚举值的表达式
     */
    private String expression;

    /**
     * 表枚举约束的SQL语句
     */
    private String tableEnumSql;

    /**
     * 最大值所属数据类
     */
    private Table maxTable;

    /**
     * 最大值所属数据字段
     */
    private Column maxColumn;

    /**
     * 最大值所属数据类
     */
    private Table minTable;

    /**
     * 最小值所属数据字段 *
     */
    private Column minColumn;


    /**
     * 该数据类枚举约束使用到的关联数据类  (RelationTableEnum") hibernate 的mapping 使用到了
     */
    private Set<RelationTableEnum> relationTableEnums = new HashSet<>(0);

    /**
     * 该数据类枚举约束使用到的关联数据类(Table)
     */
    private Map cwmRelationTableEnums = new HashMap();

    /**
     * map.xml 处用到了
     */
    private Set tabledetailSet = new HashSet(0);


    public AbstractTableEnum() {
    }

    /**
     * minimal constructor.
     *
     * @param cwmRestriction --对应的数据约束
     * @param cwmTables      --枚举值所在的数据类
     * @param cwmTabColumns  --枚举值所在的数据字段
     * @param expression     --过滤表达式
     */
    public AbstractTableEnum(Restriction cwmRestriction, Table cwmTables, Column cwmTabColumns, String expression) {
        this.cwmRestriction = cwmRestriction;
        this.table = cwmTables;
        this.column = cwmTabColumns;
        this.expression = expression;
    }

    /**
     * full constructor.
     *
     * @param cwmRestriction        --对应的数据约束
     * @param cwmTables             --枚举值所在的数据类
     * @param cwmTabColumns         --枚举值所在的数据字段
     * @param expression            --过滤表达式
     * @param tableEnumSql          --枚举值的查询语句
     * @param cwmRelationTableEnums --该数据类枚举约束使用到的关联数据类
     */
    public AbstractTableEnum(Restriction cwmRestriction, Table cwmTables, Column cwmTabColumns, String expression, String tableEnumSql, Set<RelationTableEnum> cwmRelationTableEnums) {
        this.cwmRestriction = cwmRestriction;
        this.table = cwmTables;
        this.column = cwmTabColumns;
        this.expression = expression;
        this.tableEnumSql = tableEnumSql;
        this.relationTableEnums = cwmRelationTableEnums;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Restriction getCwmRestriction() {
        return cwmRestriction;
    }

    public void setCwmRestriction(Restriction cwmRestriction) {
        this.cwmRestriction = cwmRestriction;
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

    public String getRestrictionId() {
        return restrictionId;
    }

    public void setRestrictionId(String restrictionId) {
        this.restrictionId = restrictionId;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getTableEnumSql() {
        return tableEnumSql;
    }

    public void setTableEnumSql(String tableEnumSql) {
        this.tableEnumSql = tableEnumSql;
    }

    public Table getMaxTable() {
        return maxTable;
    }

    public void setMaxTable(Table maxTable) {
        this.maxTable = maxTable;
    }

    public Column getMaxColumn() {
        return maxColumn;
    }

    public void setMaxColumn(Column maxColumn) {
        this.maxColumn = maxColumn;
    }

    public Table getMinTable() {
        return minTable;
    }

    public void setMinTable(Table minTable) {
        this.minTable = minTable;
    }

    public Column getMinColumn() {
        return minColumn;
    }

    public void setMinColumn(Column minColumn) {
        this.minColumn = minColumn;
    }

    public Set<RelationTableEnum> getRelationTableEnums() {
        return relationTableEnums;
    }

    public void setRelationTableEnums(Set<RelationTableEnum> relationTableEnums) {
        this.relationTableEnums = relationTableEnums;
    }

    public Map getCwmRelationTableEnums() {
        return cwmRelationTableEnums;
    }

    public void setCwmRelationTableEnums(Map cwmRelationTableEnums) {
        this.cwmRelationTableEnums = cwmRelationTableEnums;
    }

    public Set getTabledetailSet() {
        return tabledetailSet;
    }

    public void setTabledetailSet(Set tabledetailSet) {
        this.tabledetailSet = tabledetailSet;
    }
}