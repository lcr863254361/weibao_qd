/*
 * Title: AbstractRelationFile.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:56:58 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;

/**
 * The Class AbstractRelationFile.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public abstract class AbstractRelationFile extends com.orient.sysmodel.domain.BaseBean {

	/** The id. */
	private String id;
    
    /** The tbom. */
    private Tbom tbom;
    
    /** The fileid. */
    private String fileid;
    
    /** The order. */
    private Long order;
    
	/**
	 * Instantiates a new abstract relation file.
	 * 
	 * @param id
	 *            the id
	 * @param tbom
	 *            the tbom
	 * @param fileid
	 *            the fileid
	 * @param order
	 *            the order
	 */
	public AbstractRelationFile(String id, Tbom tbom, String fileid, Long order) {
		super();
		this.id = id;
		this.tbom = tbom;
		this.fileid = fileid;
		this.order = order;
	}
	
	/**
	 * Instantiates a new abstract relation file.
	 */
	public AbstractRelationFile() {
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
	 * Gets the tbom.
	 * 
	 * @return the tbom
	 */
	public Tbom getTbom() {
		return tbom;
	}
	
	/**
	 * Sets the tbom.
	 * 
	 * @param tbom
	 *            the new tbom
	 */
	public void setTbom(Tbom tbom) {
		this.tbom = tbom;
	}
	
	/**
	 * Gets the fileid.
	 * 
	 * @return the fileid
	 */
	public String getFileid() {
		return fileid;
	}
	
	/**
	 * Sets the fileid.
	 * 
	 * @param fileid
	 *            the new fileid
	 */
	public void setFileid(String fileid) {
		this.fileid = fileid;
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
}
