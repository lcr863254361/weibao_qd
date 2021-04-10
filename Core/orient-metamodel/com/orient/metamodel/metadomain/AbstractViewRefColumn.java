package com.orient.metamodel.metadomain;

/**
 * 数据视图的关联数据类信息表
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 7, 2012
 */
public abstract class AbstractViewRefColumn extends BaseMetaBean  {

    /**
     * The id.
     */
    private String id;

    /**
     * 视图
     */
    private View cwmViews;

    /**
     * 关联数据类（包含主数据类）
     */
    private Table cwmTables;

    /**
     * 对应视图的Id
     */
    private String viewId;

    /**
     * 关联数据类的前后顺序
     */
    private Long order;

    /**
     * 源数据类：该关联数据类由那个数据类关联进来的
     */
    private Table originTable;

    /**
     * 关系属性所属数据类
     */
    private Table fromTable;

    /**
     * 关系属性的关联数据类
     */
    private Table toTable;

    /**
     * 真正关联的物化视图的ID
     */
    private String ortView;

    /**
     * 关联类型决定关联数据类的关联SQL语句该如何写
     * 关系类型，0表示一对一，1表示一对多，2表示多对一，3表示多对多，4表示多对多
     */
    private String type;

    public AbstractViewRefColumn() {
    }

    /**
     * @param cwmViews  the cwm views
     * @param cwmTables the cwm tables
     */
    public AbstractViewRefColumn(View cwmViews, Table cwmTables) {
        this.cwmViews = cwmViews;
        this.cwmTables = cwmTables;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public View getCwmViews() {
        return cwmViews;
    }

    public void setCwmViews(View cwmViews) {
        this.cwmViews = cwmViews;
    }

    public Table getCwmTables() {
        return cwmTables;
    }

    public void setCwmTables(Table cwmTables) {
        this.cwmTables = cwmTables;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
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

    public String getOrtView() {
        return ortView;
    }

    public void setOrtView(String ortView) {
        this.ortView = ortView;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}