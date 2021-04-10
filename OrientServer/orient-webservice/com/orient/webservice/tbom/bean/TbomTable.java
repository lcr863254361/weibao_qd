/*
 * Title: TbomTable.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:56:59 AM
 * Version: 4.0
 */
package com.orient.webservice.tbom.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * The Class TbomTable.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public class TbomTable extends AbstractTbomTable implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new tbom table.
	 */
	public TbomTable() {
		super();
	}

	/**
	 * Instantiates a new tbom table.
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
	public TbomTable(String id, TbomSchema schema, String name,
			String displayName, TbomTable parentTable, Set childTables,
			List cwmRelationColumnses, List columns, String schemaid, Long order) {
		super(id, schema, name, displayName, parentTable, childTables,
				cwmRelationColumnses, columns, schemaid, order);
	}

}
