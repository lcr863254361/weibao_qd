/*
 * Title: AbstractTbomTable.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:57:00 AM
 * Version: 4.0
 */
package com.orient.webservice.tbom.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Class AbstractTbomTable.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public abstract class AbstractTbomTable  implements Serializable {

	/** The id. */
	private String id;
    
    /** The schema. */
    private TbomSchema schema;
    
    /** The name. */
    private String name;
    
    /** The display name. */
    private String displayName;
    
    /** The parent table. */
    private TbomTable parentTable;
    
    /** The child tables. */
    private Set childTables=new HashSet(0);
    
    /** The cwm relation columnses. */
    private List cwmRelationColumnses = new ArrayList();
    
    /** The columns. */
    private List columns=new ArrayList();
    
    /** The schemaid. */
    private String schemaid;
    
    /** The order. */
    private Long order;
    
    /** 数据类类型，0表示普通数据类或者引用数据类，1表示动态数据类. */
    private int type;
    
    /**
	 * Instantiates a new abstract tbom table.
	 */
    public AbstractTbomTable(){
    	
    }
  
	/**
	 * Instantiates a new abstract tbom table.
	 * 
	 * @param id
	 *            the id
	 * @param schema
	 *            the schema
	 * @param name
	 *            the name
	 * @param displayName
	 *            the display name
	 * @param parentTable
	 *            the parent table
	 * @param childTables
	 *            the child tables
	 * @param cwmRelationColumnses
	 *            the cwm relation columnses
	 * @param columns
	 *            the columns
	 * @param schemaid
	 *            the schemaid
	 * @param order
	 *            the order
	 */
	public AbstractTbomTable(String id, TbomSchema schema, String name,
			String displayName, TbomTable parentTable, Set childTables,
			List cwmRelationColumnses, List columns, String schemaid, Long order) {
		super();
		this.id = id;
		this.schema = schema;
		this.name = name;
		this.displayName = displayName;
		this.parentTable = parentTable;
		this.childTables = childTables;
		this.cwmRelationColumnses = cwmRelationColumnses;
		this.columns = columns;
		this.schemaid = schemaid;
		this.order = order;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the schema.
	 * 
	 * @return the schema
	 */
	public TbomSchema getSchema() {
		return schema;
	}
	
	/**
	 * Sets the schema.
	 * 
	 * @param schema2
	 *            the new schema
	 */
	public void setSchema(TbomSchema schema2) {
		this.schema = schema2;
	}
	
	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the display name.
	 * 
	 * @return the display name
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * Sets the display name.
	 * 
	 * @param displayName
	 *            the new display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	/**
	 * Gets the parent table.
	 * 
	 * @return the parent table
	 */
	public TbomTable getParentTable() {
		return parentTable;
	}
	
	/**
	 * Sets the parent table.
	 * 
	 * @param parentTable
	 *            the new parent table
	 */
	public void setParentTable(TbomTable parentTable) {
		this.parentTable = parentTable;
	}
	
	/**
	 * Gets the child tables.
	 * 
	 * @return the child tables
	 */
	public Set getChildTables() {
		return childTables;
	}
	
	/**
	 * Sets the child tables.
	 * 
	 * @param childTables
	 *            the new child tables
	 */
	public void setChildTables(Set childTables) {
		this.childTables = childTables;
	}
	
	/**
	 * Gets the cwm relation columnses.
	 * 
	 * @return the cwm relation columnses
	 */
	public List getCwmRelationColumnses() {
		return cwmRelationColumnses;
	}
	
	/**
	 * Sets the cwm relation columnses.
	 * 
	 * @param cwmRelationColumnses
	 *            the new cwm relation columnses
	 */
	public void setCwmRelationColumnses(List cwmRelationColumnses) {
		this.cwmRelationColumnses = cwmRelationColumnses;
	}

	/**
	 * Gets the schemaid.
	 * 
	 * @return the schemaid
	 */
	public String getSchemaid() {
		return schemaid;
	}

	/**
	 * Sets the schemaid.
	 * 
	 * @param schemaid
	 *            the new schemaid
	 */
	public void setSchemaid(String schemaid) {
		this.schemaid = schemaid;
	}

	/**
	 * Gets the order.
	 * 
	 * @return the order
	 */
	public Long getOrder() {
		return order;
	}

	/**
	 * Sets the order.
	 * 
	 * @param order
	 *            the new order
	 */
	public void setOrder(Long order) {
		this.order = order;
	}

	/**
	 * Gets the columns.
	 * 
	 * @return the columns
	 */
	public List getColumns() {
		return columns;
	}

	/**
	 * Sets the columns.
	 * 
	 * @param columns
	 *            the new columns
	 */
	public void setColumns(List columns) {
		this.columns = columns;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
    

}
