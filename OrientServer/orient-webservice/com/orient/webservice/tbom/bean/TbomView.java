/*
 * Title: TbomView.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:57:01 AM
 * Version: 4.0
 */
package com.orient.webservice.tbom.bean;

import java.io.Serializable;

/**
 * The Class TbomView.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public class TbomView extends AbstractTbomView implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new tbom view.
	 */
	public TbomView() {
	}

	/**
	 * Instantiates a new tbom view.
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
	public TbomView(String id, TbomSchema schema,TbomTable table, String name,
			String displayName,Long order,String type) {
		super(id, schema, table,name, displayName,order,type);
	}

}
