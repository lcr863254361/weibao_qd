/*
 * Title: AbstractTbomView.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:56:58 AM
 * Version: 4.0
 */
package com.orient.webservice.tbom.bean;

import java.io.Serializable;

/**
 * The Class AbstractTbomView.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public abstract class AbstractTbomView  implements Serializable {

	/** The id. */
	private String id;
	
	/** The schema. */
	private TbomSchema schema;
	
	/** The table. */
	private TbomTable table;
	
	/** The name. */
	private String name;
	
	/** The display name. */
	private String displayName;
	
	/** The schemaid. */
	private String schemaid;
	
	/** The order. */
	private Long order;
	
	/** The type. */
	private String type;

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
	 * Instantiates a new abstract tbom view.
	 */
	public AbstractTbomView() {
	}

	/**
	 * Instantiates a new abstract tbom view.
	 * 
	 * @param id
	 *            the id
	 * @param schema
	 *            the schema
	 * @param table
	 *            the table
	 * @param name
	 *            the name
	 * @param displayName
	 *            the display name
	 * @param order
	 *            the order
	 * @param type
	 *            the type
	 */
	public AbstractTbomView(String id, TbomSchema schema,TbomTable table,String name,
			String displayName,Long order,String type) {
		super();
		this.id = id;
		this.schema = schema;
		this.table = table;
		this.name = name;
		this.displayName = displayName;
		this.order=order;
		this.type=type;
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
	 * @param schema
	 *            the new schema
	 */
	public void setSchema(TbomSchema schema) {
		this.schema = schema;
	}

	/**
	 * Gets the table.
	 * 
	 * @return the table
	 */
	public TbomTable getTable() {
		return table;
	}

	/**
	 * Sets the table.
	 * 
	 * @param table
	 *            the new table
	 */
	public void setTable(TbomTable table) {
		this.table = table;
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
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

}
