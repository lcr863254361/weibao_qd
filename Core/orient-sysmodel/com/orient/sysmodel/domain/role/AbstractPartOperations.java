package com.orient.sysmodel.domain.role;
// default package


/**
 * AbstractPartOperations entity provides the base persistence definition of the PartOperations entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractPartOperations implements java.io.Serializable {


    // Fields    

    private Long id;
    //private String roleId;
    private Role role;//角色
    private String tableId;//数据类或视图ID
    private String columnId;//字段ID
    private String operationsId;//操作权限, CWM_SYS_OPERATION表中ID的集合,用","隔开
    private String filter;//过滤条件
    private String userFilter;//过滤条件
    private String isTable;//是否是数据类, 1:是数据类2:不是数据类

    private String addColumnIds;
    private String detailColumnIds;
    private String modifyColumnIds;
    private String exportColumnIds;

    public String getUserFilter() {
        return userFilter;
    }

    public void setUserFilter(String userFilter) {
        this.userFilter = userFilter;
    }

    // Constructors

    /**
     * default constructor
     */
    public AbstractPartOperations() {
    }

    /**
     * minimal constructor
     */
    public AbstractPartOperations(String isTable) {
        this.isTable = isTable;
    }

    /**
     * full constructor
     */
    public AbstractPartOperations(Role role, String tableId, String columnId, String operationsId, String filter, String isTable) {
        this.role = role;
        this.tableId = tableId;
        this.columnId = columnId;
        this.operationsId = operationsId;
        this.filter = filter;
        this.isTable = isTable;
    }


    // Property accessors

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @com.fasterxml.jackson.annotation.JsonIgnore
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getTableId() {
        return this.tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getColumnId() {
        return this.columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getOperationsId() {
        return this.operationsId;
    }

    public void setOperationsId(String operationsId) {
        this.operationsId = operationsId;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getIsTable() {
        return this.isTable;
    }

    public void setIsTable(String isTable) {
        this.isTable = isTable;
    }

    public String getAddColumnIds() {
        return addColumnIds;
    }

    public void setAddColumnIds(String addColumnIds) {
        this.addColumnIds = addColumnIds;
    }

    public String getDetailColumnIds() {
        return detailColumnIds;
    }

    public void setDetailColumnIds(String detailColumnIds) {
        this.detailColumnIds = detailColumnIds;
    }

    public String getModifyColumnIds() {
        return modifyColumnIds;
    }

    public void setModifyColumnIds(String modifyColumnIds) {
        this.modifyColumnIds = modifyColumnIds;
    }

    public String getExportColumnIds() {
        return exportColumnIds;
    }

    public void setExportColumnIds(String exportColumnIds) {
        this.exportColumnIds = exportColumnIds;
    }
}