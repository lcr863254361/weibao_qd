/*
 * Title: RelationFile.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:57:00 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;

/**
 * The Class RelationFile.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public class RelationFile extends AbstractRelationFile {

	/**
	 * Instantiates a new relation file.
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
	public RelationFile(String id, Tbom tbom, String fileid, Long order) {
		super(id, tbom, fileid, order);
	}

	/**
	 * Instantiates a new relation file.
	 */
	public RelationFile() {
		super();
	}

}
