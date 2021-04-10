package com.orient.metamodel.metadomain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 视图信息表
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 7, 2012
 */
public abstract class AbstractView extends BaseMetaBean {

    /**
     * The id.
     */
    private String id;

    /**
     * 所属Schema
     */
    private Schema schema;

    /**
     * 对应的主数据类.
     */
    private Table table;

    /**
     * 视图名称.
     */
    private String name;

    /**
     * 视图显示名.
     */
    private String displayName;

    /**
     * 物化视图的表名（对应于统计视图）
     */
    private String viewName;

    /**
     * 描述
     */
    private String description;

    /**
     * 关联查询条件
     */
    private String expression;

    /**
     * 视图类型 1.查询视图2.统计视图
     */
    private Long type;

    /**
     * The is valid.
     */
    private Long isValid;

    /**
     * 数据视图的数据记录的排序方向，升序为0，降序为1
     */
    private Long paixuFx;
    /**
     * 视图连接类型 :0为内连接，1为左连接，2为右连接，3为全连接
     */
    private Long joinType;

    /**
     * 查询视图的SQL语句
     */
    private String viewSql;

    /**
     * 数据视图的先后顺序（在DS中的排列顺序）
     */
    private Long order;

    /**
     * 在形成DS是使用的字符串
     */
    private String cite;

    /**
     * 关联物化视图的ID，用于查找关联物化视图实现快速刷新视图
     */
    private String refviewid;

    /**
     * 参数项(使用与统计视图)
     */
    private Set<ArithViewAttribute> canshuxiang = new HashSet<>(0);

    /**
     * 数据字段.
     */
    private Set<Column> columns = new HashSet<>(0);


    /**
     * 排序的数据字段
     */
    private Map cwmPaixuViewColumns = new HashMap<>();

    /**
     * 显示的数据字段
     */
    private Map cwmReturnViewColumns = new HashMap<>();

    /**
     * 关联数据类（Table）
     */
    private Map cwmViewRelationtables = new HashMap<>();

    /**
     * 关联数据类明细（RelationDetail）<Table>
     */
    private Set tabledetailSet = new HashSet(0);

    /**
     * 关联数据类（ViewRefColumn）  用户hibernate mapping
     */
    private Set<ViewRefColumn> viewRelationTables = new HashSet<>(0);


    /**
     * 排序的数据字段 用于hibernate mapping
     */
    private Set<ViewSortColumn> paixuColumns = new HashSet<>();

    /**
     * 显示的数据字段 用于hibernate mapping
     */
    private Set<ViewResultColumn> returnColumns = new HashSet<>();

    /**
     * 引用视图的集合. 用于保存视图引用的视图的集合
     */
    private Set refviewSet = new HashSet<>();

    public static final Long SORT_UP = 0L;
    public static final Long SORT_DOWN = 1L;
    public static final Long TPYE_QUERY = 1L;        //查询视图
    public static final Long TPYE_ARITH = 2L;        //统计视图

    public AbstractView() {
    }

    /**
     * minimal constructor.
     *
     * @param cwmTables   --视图所关联的第一个数据类（主数据类）
     * @param name        --视图名称
     * @param displayName --视图显示名
     * @param type        --视图类型 1.查询视图2.统计视图
     * @param isValid     --是否有效 0.无效 1.有效
     */
    public AbstractView(Table cwmTables, String name, String displayName, Long type, Long isValid) {
        this.table = cwmTables;
        this.name = name;
        this.displayName = displayName;
        this.type = type;
        this.isValid = isValid;
    }

    /**
     * full constructor.
     *
     * @param cwmSchema             --视图所属的schema
     * @param cwmTables             --主数据类
     * @param name                  --视图名称
     * @param displayName           --视图的显示名
     * @param description           --视图描述
     * @param expression            --视图的查询表达式
     * @param type                  --视图类型 1.查询视图2.统计视图
     * @param isValid               --是否有效 0.无效 1.有效
     * @param viewSql               --生成视图的SQL语句
     * @param cwmViewRelationtables --视图相关联的数据类
     * @param cwmViewPaixuColumns   --视图的排序字段
     * @param cwmViewReturnColumns  --视图的显示字段
     */
    public AbstractView(Schema cwmSchema, Table cwmTables, String name, String displayName, String description, String expression, Long type, Long isValid, String viewSql, Set cwmViewRelationtables, Set cwmViewPaixuColumns, Set cwmViewReturnColumns) {
        this.schema = cwmSchema;
        this.table = cwmTables;
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.expression = expression;
        this.type = type;
        this.isValid = isValid;
        this.viewSql = viewSql;
//        this.cwmViewRelationtables = cwmViewRelationtables;
//      this.cwmPaixuViewColumns = cwmViewPaixuColumns;
//      this.cwmReturnViewColumns = cwmViewReturnColumns;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getIsValid() {
        return isValid;
    }

    public void setIsValid(Long isValid) {
        this.isValid = isValid;
    }

    public Long getPaixuFx() {
        return paixuFx;
    }

    public void setPaixuFx(Long paixuFx) {
        this.paixuFx = paixuFx;
    }

    public Long getJoinType() {
        return joinType;
    }

    public void setJoinType(Long joinType) {
        this.joinType = joinType;
    }

    public String getViewSql() {
        return viewSql;
    }

    public void setViewSql(String viewSql) {
        this.viewSql = viewSql;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getCite() {
        return cite;
    }

    public void setCite(String cite) {
        this.cite = cite;
    }

    public String getRefviewid() {
        return refviewid;
    }

    public void setRefviewid(String refviewid) {
        this.refviewid = refviewid;
    }

    public Set<ArithViewAttribute> getCanshuxiang() {
        return canshuxiang;
    }

    public void setCanshuxiang(Set<ArithViewAttribute> canshuxiang) {
        this.canshuxiang = canshuxiang;
    }

    public Set<Column> getColumns() {
        return columns;
    }

    public void setColumns(Set<Column> columns) {
        this.columns = columns;
    }

    public Map getCwmPaixuViewColumns() {
        return cwmPaixuViewColumns;
    }

    public void setCwmPaixuViewColumns(Map cwmPaixuViewColumns) {
        this.cwmPaixuViewColumns = cwmPaixuViewColumns;
    }

    public Map getCwmReturnViewColumns() {
        return cwmReturnViewColumns;
    }

    public void setCwmReturnViewColumns(Map cwmReturnViewColumns) {
        this.cwmReturnViewColumns = cwmReturnViewColumns;
    }

    public Map getCwmViewRelationtables() {
        return cwmViewRelationtables;
    }

    public void setCwmViewRelationtables(Map cwmViewRelationtables) {
        this.cwmViewRelationtables = cwmViewRelationtables;
    }

    public Set getTabledetailSet() {
        return tabledetailSet;
    }

    public void setTabledetailSet(Set tabledetailSet) {
        this.tabledetailSet = tabledetailSet;
    }

    public Set<ViewRefColumn> getViewRelationTables() {
        return viewRelationTables;
    }

    public void setViewRelationTables(Set<ViewRefColumn> viewRelationTables) {
        this.viewRelationTables = viewRelationTables;
    }

    public Set<ViewSortColumn> getPaixuColumns() {
        return paixuColumns;
    }

    public void setPaixuColumns(Set<ViewSortColumn> paixuColumns) {
        this.paixuColumns = paixuColumns;
    }

    public Set<ViewResultColumn> getReturnColumns() {
        return returnColumns;
    }

    public void setReturnColumns(Set<ViewResultColumn> returnColumns) {
        this.returnColumns = returnColumns;
    }

    public Set getRefviewSet() {
        return refviewSet;
    }

    public void setRefviewSet(Set refviewSet) {
        this.refviewSet = refviewSet;
    }
}