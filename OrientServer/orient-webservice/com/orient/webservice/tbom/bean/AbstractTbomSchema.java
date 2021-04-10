/*
 * Title: AbstractTbomSchema.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:56:59 AM
 * Version: 4.0
 */
package com.orient.webservice.tbom.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * The Class AbstractTbomSchema.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public abstract class AbstractTbomSchema implements java.io.Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2217151974976340895L;
	
	/** The id. */
	private String id;
    
    /** The name. */
    private String name;
    
    /** The version. */
    private String version;
    
    /** The tables. */
    private Set tables = new HashSet(0);
    
    /** The views. */
    private Set views = new HashSet(0);
    
    private Set dynamicTables = new HashSet(0);
    
    /**
	 * Instantiates a new abstract tbom schema.
	 */
    public AbstractTbomSchema() {
	}
    
    
	/**
	 * Instantiates a new abstract tbom schema.
	 * 
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 * @param version
	 *            the version
	 * @param tables
	 *            the tables
	 * @param views
	 *            the views
	 */
	public AbstractTbomSchema(String id, String name, String version,
			Set tables, Set views) {
		super();
		this.id = id;
		this.name = name;
		this.version = version;
		this.tables = tables;
		this.views = views;
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
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * Sets the version.
	 * 
	 * @param version
	 *            the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * Gets the tables.
	 * 
	 * @return the tables
	 */
	public Set getTables() {
		return tables;
	}
	
	/**
	 * Sets the tables.
	 * 
	 * @param tables
	 *            the new tables
	 */
	public void setTables(Set tables) {
		this.tables = tables;
	}
	
	/**
	 * Gets the views.
	 * 
	 * @return the views
	 */
	public Set getViews() {
		return views;
	}
	
	/**
	 * Sets the views.
	 * 
	 * @param views
	 *            the new views
	 */
	public void setViews(Set views) {
		this.views = views;
	}


	public Set getDynamicTables() {
		return dynamicTables;
	}


	public void setDynamicTables(Set dynamicTables) {
		this.dynamicTables = dynamicTables;
	}

}
