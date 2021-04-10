/*
 * Title: TbomSchema.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:56:58 AM
 * Version: 4.0
 */
package com.orient.webservice.tbom.bean;

import java.io.Serializable;
import java.util.Set;

/**
 * The Class TbomSchema.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public class TbomSchema extends AbstractTbomSchema implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new tbom schema.
	 */
	public TbomSchema() {
	}

	/**
	 * Instantiates a new tbom schema.
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
	public TbomSchema(String id, String name, String version, Set tables,
			Set views) {
		super(id, name, version, tables, views);
	}

}
