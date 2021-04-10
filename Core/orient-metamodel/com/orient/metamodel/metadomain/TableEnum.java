package com.orient.metamodel.metadomain;

import java.util.Set;

/**
 * 数据类枚举约束信息表
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 7, 2012
 */
public class TableEnum extends AbstractTableEnum {

    public TableEnum() {
    }

    /**
     * minimal constructor.
     *
     * @param cwmRestriction --对应的数据约束
     * @param cwmTables      --枚举值所在的数据类
     * @param cwmTabColumns  --枚举值所在的数据字段
     * @param expression     --过滤表达式
     */
    public TableEnum(Restriction cwmRestriction, Table cwmTables, Column cwmTabColumns, String expression) {
        super(cwmRestriction, cwmTables, cwmTabColumns, expression);
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
    public TableEnum(Restriction cwmRestriction, Table cwmTables, Column cwmTabColumns, String expression, String tableEnumSql, Set cwmRelationTableEnums) {
        super(cwmRestriction, cwmTables, cwmTabColumns, expression, tableEnumSql, cwmRelationTableEnums);
    }

}
