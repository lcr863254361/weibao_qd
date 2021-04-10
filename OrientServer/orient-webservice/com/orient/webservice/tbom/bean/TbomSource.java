/*
 * Title: TbomSource.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:57:00 AM
 * Version: 4.0
 */
package com.orient.webservice.tbom.bean;

import java.io.Serializable;
import java.util.List;

/**
 * The Class TbomSource.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public class TbomSource extends AbstractTbomSource implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new tbom source.
	 * 
	 * @param source
	 *            the source
	 */
	public TbomSource(List<TbomSchema> source) {
		super(source);
	}

	/**
	 * Instantiates a new tbom source.
	 */
	public TbomSource() {
	}

}
