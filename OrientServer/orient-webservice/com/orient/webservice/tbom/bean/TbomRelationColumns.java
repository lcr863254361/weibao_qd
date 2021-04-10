/*
 * Title: TbomRelationColumns.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:56:59 AM
 * Version: 4.0
 */
package com.orient.webservice.tbom.bean;

import java.io.Serializable;

/**
 * The Class TbomRelationColumns.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public class TbomRelationColumns extends AbstractTbomRelationColumns implements
		Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new tbom relation columns.
	 */
	public TbomRelationColumns() {
	}

	/**
	 * Instantiates a new tbom relation columns.
	 * 
	 * @param id
	 *            the id
	 * @param table
	 *            the table
	 * @param relationtype
	 *            the relationtype
	 * @param ownership
	 *            the ownership
	 * @param isFK
	 *            the is fk
	 * @param refTable
	 *            the ref table
	 */
	public TbomRelationColumns(String id, TbomTable table, Long relationtype,
			Long ownership, Long isFK, TbomTable refTable) {
		super(id, table, relationtype, ownership, isFK, refTable);
	}

}
