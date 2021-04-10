package com.orient.sysmodel.domain.user;
// default package



/**
 * AbstractUserColumn entity provides the base persistence definition of the UserColumn entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserColumn extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String id;
     private String displayName;//字段显示名
     private String SColumnName;//用户表中的字段名
     private String isForSearch;//是否做为检索条件 1:是 0:不是
     private String isNullable;//是否允许为空 1:为空 0:不为空
     private String isOnly;//是否唯一 1:唯一 0:不唯一
     private String isPk;//是否是主键 1:是 0:不是
     private String enmuId;//枚举类型
     private String colType;//数据类型,用于queryList() 方法中查询条件类型判断
     private String sequenceName;//自增生成器
     private String isAutoincrement;//是否自增 1:自增 
     private Long maxLength;//最大长度
     private Long minLength;//最小长度
     private String isWrap;//是否是多行 1:多行 0单行
     private String checkType;//校验类型 1:是否唯一.2:是否为数字.3:最大长度.4:最小长度.5:是否不为空
     private String isMultiSelected;//枚举类型前提下是否多选 1：多选 0: 单选
     private String defaultValue;//初始值
     private String displayShow;//检索画面显示标志 1:显示 0:不显示
     private String editShow;//新建画面与编辑画面显示标志 1:显示 0:不显示
     private Long shot;//页面表示顺序
     private String inputType;//页面输入类型 1:文本框 2:大文本框 3:下拉列表:4:日期控件 5:复选框 6:单选框 7:弹出窗口 8:密码
     private String isReadonly;//是否只读 1:只读 0:读写
     private String refTable;//关联表
     private String refTableColumnId;//关联表字段
     private String refTableColumnShowname;//关联表字段显示数据
     private String popWindowFunction;//弹出窗口调用的页面js
     private String isForInfosearch;//是否用于用户角色信息查询条件 1:是 0:不是
     private String isDispalyinfoShow;//是否用于用户角色信息列表显示  1:是 0:不是
     private String isViewinfoShow;//是否用于用户角色信息详细页面显示


    // Constructors

    /** default constructor */
    public AbstractUserColumn() {
    }

	/** minimal constructor */
    public AbstractUserColumn(String displayName, String SColumnName, String colType) {
        this.displayName = displayName;
        this.SColumnName = SColumnName;
        this.colType = colType;
    }
    
    /** full constructor */
    public AbstractUserColumn(String displayName, String SColumnName, String isForSearch, String isNullable, String isOnly, String isPk, String enmuId, String colType, String sequenceName, String isAutoincrement, Long maxLength, Long minLength, String isWrap, String checkType, String isMultiSelected, String defaultValue, String displayShow, String editShow, Long shot, String inputType, String isReadonly, String refTable, String refTableColumnId, String refTableColumnShowname, String popWindowFunction, String isForInfosearch, String isDispalyinfoShow, String isViewinfoShow) {
        this.displayName = displayName;
        this.SColumnName = SColumnName;
        this.isForSearch = isForSearch;
        this.isNullable = isNullable;
        this.isOnly = isOnly;
        this.isPk = isPk;
        this.enmuId = enmuId;
        this.colType = colType;
        this.sequenceName = sequenceName;
        this.isAutoincrement = isAutoincrement;
        this.maxLength = maxLength;
        this.minLength = minLength;
        this.isWrap = isWrap;
        this.checkType = checkType;
        this.isMultiSelected = isMultiSelected;
        this.defaultValue = defaultValue;
        this.displayShow = displayShow;
        this.editShow = editShow;
        this.shot = shot;
        this.inputType = inputType;
        this.isReadonly = isReadonly;
        this.refTable = refTable;
        this.refTableColumnId = refTableColumnId;
        this.refTableColumnShowname = refTableColumnShowname;
        this.popWindowFunction = popWindowFunction;
        this.isForInfosearch = isForInfosearch;
        this.isDispalyinfoShow = isDispalyinfoShow;
        this.isViewinfoShow = isViewinfoShow;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return this.displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSColumnName() {
        return this.SColumnName;
    }
    
    public void setSColumnName(String SColumnName) {
        this.SColumnName = SColumnName;
    }

    public String getIsForSearch() {
        return this.isForSearch;
    }
    
    public void setIsForSearch(String isForSearch) {
        this.isForSearch = isForSearch;
    }

    public String getIsNullable() {
        return this.isNullable;
    }
    
    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getIsOnly() {
        return this.isOnly;
    }
    
    public void setIsOnly(String isOnly) {
        this.isOnly = isOnly;
    }

    public String getIsPk() {
        return this.isPk;
    }
    
    public void setIsPk(String isPk) {
        this.isPk = isPk;
    }

    public String getEnmuId() {
        return this.enmuId;
    }
    
    public void setEnmuId(String enmuId) {
        this.enmuId = enmuId;
    }

    public String getColType() {
        return this.colType;
    }
    
    public void setColType(String colType) {
        this.colType = colType;
    }

    public String getSequenceName() {
        return this.sequenceName;
    }
    
    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public String getIsAutoincrement() {
        return this.isAutoincrement;
    }
    
    public void setIsAutoincrement(String isAutoincrement) {
        this.isAutoincrement = isAutoincrement;
    }

    public Long getMaxLength() {
        return this.maxLength;
    }
    
    public void setMaxLength(Long maxLength) {
        this.maxLength = maxLength;
    }

    public Long getMinLength() {
        return this.minLength;
    }
    
    public void setMinLength(Long minLength) {
        this.minLength = minLength;
    }

    public String getIsWrap() {
        return this.isWrap;
    }
    
    public void setIsWrap(String isWrap) {
        this.isWrap = isWrap;
    }

    public String getCheckType() {
        return this.checkType;
    }
    
    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getIsMultiSelected() {
        return this.isMultiSelected;
    }
    
    public void setIsMultiSelected(String isMultiSelected) {
        this.isMultiSelected = isMultiSelected;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }
    
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDisplayShow() {
        return this.displayShow;
    }
    
    public void setDisplayShow(String displayShow) {
        this.displayShow = displayShow;
    }

    public String getEditShow() {
        return this.editShow;
    }
    
    public void setEditShow(String editShow) {
        this.editShow = editShow;
    }

    public Long getShot() {
        return this.shot;
    }
    
    public void setShot(Long shot) {
        this.shot = shot;
    }

    public String getInputType() {
        return this.inputType;
    }
    
    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getIsReadonly() {
        return this.isReadonly;
    }
    
    public void setIsReadonly(String isReadonly) {
        this.isReadonly = isReadonly;
    }

    public String getRefTable() {
        return this.refTable;
    }
    
    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public String getRefTableColumnId() {
        return this.refTableColumnId;
    }
    
    public void setRefTableColumnId(String refTableColumnId) {
        this.refTableColumnId = refTableColumnId;
    }

    public String getRefTableColumnShowname() {
        return this.refTableColumnShowname;
    }
    
    public void setRefTableColumnShowname(String refTableColumnShowname) {
        this.refTableColumnShowname = refTableColumnShowname;
    }

    public String getPopWindowFunction() {
        return this.popWindowFunction;
    }
    
    public void setPopWindowFunction(String popWindowFunction) {
        this.popWindowFunction = popWindowFunction;
    }

    public String getIsForInfosearch() {
        return this.isForInfosearch;
    }
    
    public void setIsForInfosearch(String isForInfosearch) {
        this.isForInfosearch = isForInfosearch;
    }

    public String getIsDispalyinfoShow() {
        return this.isDispalyinfoShow;
    }
    
    public void setIsDispalyinfoShow(String isDispalyinfoShow) {
        this.isDispalyinfoShow = isDispalyinfoShow;
    }

    public String getIsViewinfoShow() {
        return this.isViewinfoShow;
    }
    
    public void setIsViewinfoShow(String isViewinfoShow) {
        this.isViewinfoShow = isViewinfoShow;
    }
   








}