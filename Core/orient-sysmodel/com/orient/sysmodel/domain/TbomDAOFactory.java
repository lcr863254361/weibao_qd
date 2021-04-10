/**
 * TbomDAOFactory.java
 * com.orient.sysmodel.domain
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-4-19 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.domain;

import com.orient.sysmodel.domain.tbom.TbomDAO;
import com.orient.sysmodel.domain.tbom.TbomDirDAO;

/**
 * ClassName:TbomDAOFactory
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-19		上午11:39:39
 *
 * @see 	 
 */
public class TbomDAOFactory {

	private TbomDAO tbomDAO;
	
	private TbomDirDAO tbomDirDAO;

	/**
	 * tbomDAO
	 *
	 * @return  the tbomDAO
	 * @since   CodingExample Ver 1.0
	 */
	
	public TbomDAO getTbomDAO() {
		return tbomDAO;
	}

	/**
	 * tbomDAO
	 *
	 * @param   tbomDAO    the tbomDAO to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setTbomDAO(TbomDAO tbomDAO) {
		this.tbomDAO = tbomDAO;
	}

	public TbomDirDAO getTbomDirDAO() {
		return tbomDirDAO;
	}

	public void setTbomDirDAO(TbomDirDAO tbomDirDAO) {
		this.tbomDirDAO = tbomDirDAO;
	}
	
}

