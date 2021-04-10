package com.orient.sysmodel.domain.report;

// default package



/**
 * AbstractReportItemsId entity provides the base persistence definition of the ReportItemsId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractReportItems extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String id;
     //private String reportId;
     private Reports report;//报告模板
     private String tableId;//过滤条件所在的数据类：数据类ID
     private String columnName;//未使用
     private String relations;//未使用
     private String type;//未使用

     //CONTENT----------------------过滤条件：显示给用户查看（DisplayName）
     //FILED_CONTENT----------------过滤条件：真实查询条件，直接用于sql查询过滤


    // Constructors

    /** default constructor */
    public AbstractReportItems() {
    }

    
    /** full constructor */
    public AbstractReportItems(String id, Reports report, String tableId, String columnName, String relations, String type) {
        this.id = id;
        this.report = report;
        this.tableId = tableId;
        this.columnName = columnName;
        this.relations = relations;
        this.type = type;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    
    public Reports getReport() {
		return report;
	}


	public void setReport(Reports report) {
		this.report = report;
	}


	public String getTableId() {
        return this.tableId;
    }
    
    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getColumnName() {
        return this.columnName;
    }
    
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getRelations() {
        return this.relations;
    }
    
    public void setRelations(String relations) {
        this.relations = relations;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
   



   




}