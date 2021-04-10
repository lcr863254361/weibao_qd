package com.orient.metamodel.metadomain;

/**
 * 描述关系的详细情况（没有对应的表），在视图（view）中使用到了。在数据类约束(TableEnum)中使用到了
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 8, 2012
 */
public abstract class AbstractRelationDetail extends BaseMetaBean {

    /**
     * 引入关联数据类的源数据类
     */
    private Table fromTable;

    /**
     * 关联数据类（视图包含的数据类）
     */
    private Table toTable;

    /**
     * 关系属性指向的表
     */
    private Table rtoTable;

    /**
     * 关系属性所在的表.
     */
    private Table rfromTable;

    /**
     * 0,表示数据类A和数据类B之间是A.id=B.A_id;(A是leftTable，B是rightTable)
     * 1,表示数据类A和数据类B之间是A.B_id=B.id;
     * 2,表示两个数据类(A,B)之间的关系是多对多c1.main_table_name = A.NAME and c1.main_data_id=A.id and c1.sub_table_name = B.NAME and c1.sub_data_id = B.ID
     * 3.表示与2相反
     */
    private String type;

    /**
     * The view name.
     */
    private String viewName;

    /**
     * Instantiates a new abstract relation detail.
     */
    public AbstractRelationDetail() {
    }

    /**
     * Instantiates a new relation detail.
     *
     * @param fromTable --引入关联数据类的源数据类
     * @param toTable   --关联数据类（视图包含的数据类）
     * @param type      --关系类型
     */
    public AbstractRelationDetail(Table fromTable, Table toTable, String type) {
        this.fromTable = fromTable;
        this.toTable = toTable;
        this.type = type;
    }

    public Table getFromTable() {
        return fromTable;
    }

    public void setFromTable(Table fromTable) {
        this.fromTable = fromTable;
    }

    public Table getToTable() {
        return toTable;
    }

    public void setToTable(Table toTable) {
        this.toTable = toTable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Table getRtoTable() {
        return rtoTable;
    }

    public void setRtoTable(Table rtoTable) {
        this.rtoTable = rtoTable;
    }

    public Table getRfromTable() {
        return rfromTable;
    }

    public void setRfromTable(Table rfromTable) {
        this.rfromTable = rfromTable;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

}
