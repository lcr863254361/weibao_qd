/*
 * Title: DynamicTbom.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:56:58 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;

import java.util.List;

import com.orient.sysmodel.operationinterface.IDynamicTbom;

/**
 * The Class DynamicTbom.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public class DynamicTbom extends AbstractDynamicTbom implements IDynamicTbom {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new dynamic tbom.
	 */
	public DynamicTbom() {
		super();
	}

	/**
	 * Instantiates a new dynamic tbom.
	 * 
	 * @param id
	 *            the id
	 * @param tbom
	 *            the tbom
	 * @param column
	 *            the column
	 * @param table
	 *            the table
	 * @param view
	 *            the view
	 * @param order
	 *            the order
	 * @param source
	 *            the source
	 */
	public DynamicTbom(String id, Tbom tbom, String column, String table,
			String view, String order, String source, String url, String showType) {
		super(id, tbom, column, table, view, order, source, url, showType);
	}

}
