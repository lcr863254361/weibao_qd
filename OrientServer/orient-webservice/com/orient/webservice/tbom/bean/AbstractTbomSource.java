/*
 * Title: AbstractTbomSource.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:57:00 AM
 * Version: 4.0
 */
package com.orient.webservice.tbom.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class AbstractTbomSource.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public abstract class AbstractTbomSource  implements
		Serializable {

	/** The source. */
	private List<TbomSchema> source = new ArrayList<TbomSchema>();

	/** The file list. */
	private List<TbomFile> fileList = new ArrayList<TbomFile>();

	/**
	 * Gets the source.
	 * 
	 * @return the source
	 */
	public List<TbomSchema> getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 * 
	 * @param source
	 *            the new source
	 */
	public void setSource(List<TbomSchema> source) {
		this.source = source;
	}

	/**
	 * Instantiates a new abstract tbom source.
	 * 
	 * @param source
	 *            the source
	 */
	public AbstractTbomSource(List<TbomSchema> source) {
		super();
		this.source = source;
	}

	/**
	 * Instantiates a new abstract tbom source.
	 */
	public AbstractTbomSource() {
	}

	/**
	 * Gets the file list.
	 * 
	 * @return the file list
	 */
	public List<TbomFile> getFileList() {
		return fileList;
	}

	/**
	 * Sets the file list.
	 * 
	 * @param fileList
	 *            the new file list
	 */
	public void setFileList(List<TbomFile> fileList) {
		this.fileList = fileList;
	}
}
