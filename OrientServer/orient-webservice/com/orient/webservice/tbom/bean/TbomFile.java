/*
 * Title: TbomFile.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:56:58 AM
 * Version: 4.0
 */
package com.orient.webservice.tbom.bean;


/**
 * The Class TbomFile.
 * 
 * @author xuxj
 * @version 创建时间：2009-3-25 下午01:18:47 类说明
 */
public class TbomFile extends AbstractTbomFile {

	/**
	 * Instantiates a new tbom file.
	 * 
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 * @param type
	 *            the type
	 * @param date
	 *            the date
	 * @param size
	 *            the size
	 */
	public TbomFile(String id, String name, String description, String type,
			String date,String size) {
		super(id, name, description, type, date, size);
	}

	/**
	 * Instantiates a new tbom file.
	 */
	public TbomFile() {
		super();
	}

	
}
