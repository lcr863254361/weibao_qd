package com.orient.metamodel.metadomain;

/**
 * 数据类复合属性信息
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 7, 2012
 */
public class TableColumn extends AbstractTableColumn {

    public TableColumn() {
    }

    /**
     * Instantiates a new table column
     *
     * @param id     --ID
     * @param table  --所属的数据类
     * @param column --数据字段
     * @param type   --复合属性的类型0表示为主键显示值，1表示为唯一性组合约束，2表示为排序属性，3表示为数据表字段前后展现顺序
     * @param order  --用于标记复合属性的前后顺序
     */
    public TableColumn(String id, Table table, Column column, Long type, Long order) {
        super(id, table, column, type, order);
    }

}
