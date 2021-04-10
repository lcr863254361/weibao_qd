package com.orient.sysmodel.domain.report;
// default package

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.orient.metamodel.metadomain.Schema;


/**
 * AbstractReports entity provides the base persistence definition of the Reports entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractReports extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String id;
     //private String schemaId;//数据源   创建报告模板时所选择的业务库:业务库ID
     private Schema schema;
     private String tableId;//数据类   创建模板时所选择的数据类：数据类ID（用逗号隔开）
     private String viewsId;//数据视图 创建模板时所选择的数据源：数据类ID（用逗号隔开）
     private String columnId;//未使用
     private String content;//存放报告模板中所有标签（用逗号隔开）
     private String filepath;//报告文件相对路径（word文档名称）
     private String pid;//未使用
     private Boolean type;//报告类型（暂时未使用）用于判断数据类和数据视图  0：数据类（TABLE_ID不为空）；1：数据视图（VIEWS_ID不为空）；2：数据类和数据视图 （TABLE_ID和VIEWS_ID都不为空）
     private Boolean orders;//未使用
     private String createUser;//创建人：存放登陆用户名
     private Date createTime;//创建时间
     private String name;//报告名称
     private String filterjson;//生成唯一标签名：word标签要求唯一，当选择相同的字段作为标签时，这里FILTERJSON用于创建唯一标签
     private String dataEntry;//数据入口  作为生成报告时的入口：数据类的ID

     private Set reportItems = new HashSet(0);//报告的过滤条件 

    // Constructors

    /** default constructor */
    public AbstractReports() {
    }

    
    /** full constructor */
    public AbstractReports(Schema schema, String tableId, String viewsId, String columnId, String content, String filepath, String pid, Boolean type, Boolean orders, String createUser, Date createTime, String name, String filterjson, String dataEntry) {
        this.schema = schema;
        this.tableId = tableId;
        this.viewsId = viewsId;
        this.columnId = columnId;
        this.content = content;
        this.filepath = filepath;
        this.pid = pid;
        this.type = type;
        this.orders = orders;
        this.createUser = createUser;
        this.createTime = createTime;
        this.name = name;
        this.filterjson = filterjson;
        this.dataEntry = dataEntry;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    

    /**
	 * schema
	 *
	 * @return  the schema
	 * @since   CodingExample Ver 1.0
	 */
	
	public Schema getSchema() {
		return schema;
	}


	/**
	 * schema
	 *
	 * @param   schema    the schema to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setSchema(Schema schema) {
		this.schema = schema;
	}


	public String getTableId() {
        return this.tableId;
    }
    
    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getViewsId() {
        return this.viewsId;
    }
    
    public void setViewsId(String viewsId) {
        this.viewsId = viewsId;
    }

    public String getColumnId() {
        return this.columnId;
    }
    
    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public String getFilepath() {
        return this.filepath;
    }
    
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getPid() {
        return this.pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }

    public Boolean getType() {
        return this.type;
    }
    
    public void setType(Boolean type) {
        this.type = type;
    }

    public Boolean getOrders() {
        return this.orders;
    }
    
    public void setOrders(Boolean orders) {
        this.orders = orders;
    }

    public String getCreateUser() {
        return this.createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getFilterjson() {
        return this.filterjson;
    }
    
    public void setFilterjson(String filterjson) {
        this.filterjson = filterjson;
    }

    public String getDataEntry() {
        return this.dataEntry;
    }
    
    public void setDataEntry(String dataEntry) {
        this.dataEntry = dataEntry;
    }


	/**
	 * reportItems
	 *
	 * @return  the reportItems
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getReportItems() {
		return reportItems;
	}


	/**
	 * reportItems
	 *
	 * @param   reportItems    the reportItems to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setReportItems(Set reportItems) {
		this.reportItems = reportItems;
	}
   








}