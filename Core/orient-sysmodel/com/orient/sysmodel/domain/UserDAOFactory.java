/**
 * UserDAOFactory.java
 * com.orient.sysmodel.domain
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-4-11 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.domain;

import com.orient.metamodel.metaengine.dao.EnumDAO;
import com.orient.sysmodel.domain.user.DepartmentDAO;
import com.orient.sysmodel.domain.user.UserColumnDAO;
import com.orient.sysmodel.domain.user.UserDAO;

/**
 * ClassName:UserDAOFactory
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-11		上午09:32:23
 *
 * @see 	 
 */
public class UserDAOFactory {
	private UserDAO userDAO;
	private UserColumnDAO userColumnDAO;
	private EnumDAO enumDAO;
	private DepartmentDAO departDAO;
	/**
	 * userDAO
	 *
	 * @return  the userDAO
	 * @since   CodingExample Ver 1.0
	 */
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	/**
	 * userDAO
	 *
	 * @param   userDAO    the userDAO to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	/**
	 * userColumnDAO
	 *
	 * @return  the userColumnDAO
	 * @since   CodingExample Ver 1.0
	 */
	
	public UserColumnDAO getUserColumnDAO() {
		return userColumnDAO;
	}
	/**
	 * userColumnDAO
	 *
	 * @param   userColumnDAO    the userColumnDAO to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setUserColumnDAO(UserColumnDAO userColumnDAO) {
		this.userColumnDAO = userColumnDAO;
	}
	/**
	 * enumDAO
	 *
	 * @return  the enumDAO
	 * @since   CodingExample Ver 1.0
	 */
	
	public EnumDAO getEnumDAO() {
		return enumDAO;
	}
	/**
	 * enumDAO
	 *
	 * @param   enumDAO    the enumDAO to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setEnumDAO(EnumDAO enumDAO) {
		this.enumDAO = enumDAO;
	}
	
	/**
	 * @return the departDAO
	 */
	public DepartmentDAO getDepartDAO() {
		return departDAO;
	}
	/**
	 * @param departDAO the departDAO to set
	 */
	public void setDepartDAO(DepartmentDAO departDAO) {
		this.departDAO = departDAO;
	}
	
	
}

