/**
 * RoleDAOFactory.java
 * com.orient.sysmodel.domain
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-4-1 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.domain;

import com.orient.sysmodel.domain.role.FunctionDAO;
import com.orient.sysmodel.domain.role.OperationDAO;
import com.orient.sysmodel.domain.role.OverAllOperationsDAO;
import com.orient.sysmodel.domain.role.PartOperationsDAO;
import com.orient.sysmodel.domain.role.RoleArithDAO;
import com.orient.sysmodel.domain.role.RoleDAO;
import com.orient.sysmodel.domain.role.RoleFunctionTbomDAO;
import com.orient.sysmodel.domain.role.RoleSchemaDAO;
import com.orient.sysmodel.domain.role.RoleUserDAO;

/**
 * ClassName:RoleDAOFactory
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-1		下午04:08:50
 *
 * @see 	 
 */
public class RoleDAOFactory {

	private RoleDAO roleDAO;
	private RoleSchemaDAO roleSchemaDAO;
	private RoleUserDAO roleUserDAO;
	private RoleArithDAO roleArithDAO;
	private FunctionDAO functionDAO;
	private RoleFunctionTbomDAO roleFunctionTbomDAO;
	private OverAllOperationsDAO overAllOperationsDAO;
	private PartOperationsDAO partOperationsDAO;
	private OperationDAO operationDAO;

	/**
	 * roleDAO
	 *
	 * @return  the roleDAO
	 * @since   CodingExample Ver 1.0
	 */
	
	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	/**
	 * roleDAO
	 *
	 * @param   roleDAO    the roleDAO to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	/**
	 * roleSchemaDAO
	 *
	 * @return  the roleSchemaDAO
	 * @since   CodingExample Ver 1.0
	 */
	
	public RoleSchemaDAO getRoleSchemaDAO() {
		return roleSchemaDAO;
	}

	/**
	 * roleSchemaDAO
	 *
	 * @param   roleSchemaDAO    the roleSchemaDAO to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRoleSchemaDAO(RoleSchemaDAO roleSchemaDAO) {
		this.roleSchemaDAO = roleSchemaDAO;
	}

	/**
	 * roleUserDAO
	 *
	 * @return  the roleUserDAO
	 * @since   CodingExample Ver 1.0
	 */
	
	public RoleUserDAO getRoleUserDAO() {
		return roleUserDAO;
	}

	/**
	 * roleUserDAO
	 *
	 * @param   roleUserDAO    the roleUserDAO to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRoleUserDAO(RoleUserDAO roleUserDAO) {
		this.roleUserDAO = roleUserDAO;
	}

	/**
	 * roleArithDAO
	 *
	 * @return  the roleArithDAO
	 * @since   CodingExample Ver 1.0
	 */
	
	public RoleArithDAO getRoleArithDAO() {
		return roleArithDAO;
	}

	/**
	 * roleArithDAO
	 *
	 * @param   roleArithDAO    the roleArithDAO to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRoleArithDAO(RoleArithDAO roleArithDAO) {
		this.roleArithDAO = roleArithDAO;
	}

	/**
	 * functionDAO
	 *
	 * @return  the functionDAO
	 * @since   CodingExample Ver 1.0
	 */
	
	public FunctionDAO getFunctionDAO() {
		return functionDAO;
	}

	/**
	 * functionDAO
	 *
	 * @param   functionDAO    the functionDAO to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setFunctionDAO(FunctionDAO functionDAO) {
		this.functionDAO = functionDAO;
	}


	/**
	 * roleFunctionTbomDAO
	 *
	 * @return  the roleFunctionTbomDAO
	 * @since   CodingExample Ver 1.0
	 */
	
	public RoleFunctionTbomDAO getRoleFunctionTbomDAO() {
		return roleFunctionTbomDAO;
	}

	/**
	 * roleFunctionTbomDAO
	 *
	 * @param   roleFunctionTbomDAO    the roleFunctionTbomDAO to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRoleFunctionTbomDAO(RoleFunctionTbomDAO roleFunctionTbomDAO) {
		this.roleFunctionTbomDAO = roleFunctionTbomDAO;
	}

	/**
	 * overAllOperationsDAO
	 *
	 * @return  the overAllOperationsDAO
	 * @since   CodingExample Ver 1.0
	 */
	
	public OverAllOperationsDAO getOverAllOperationsDAO() {
		return overAllOperationsDAO;
	}

	/**
	 * overAllOperationsDAO
	 *
	 * @param   overAllOperationsDAO    the overAllOperationsDAO to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setOverAllOperationsDAO(OverAllOperationsDAO overAllOperationsDAO) {
		this.overAllOperationsDAO = overAllOperationsDAO;
	}

	/**
	 * partOperationsDAO
	 *
	 * @return  the partOperationsDAO
	 * @since   CodingExample Ver 1.0
	 */
	
	public PartOperationsDAO getPartOperationsDAO() {
		return partOperationsDAO;
	}

	/**
	 * partOperationsDAO
	 *
	 * @param   partOperationsDAO    the partOperationsDAO to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setPartOperationsDAO(PartOperationsDAO partOperationsDAO) {
		this.partOperationsDAO = partOperationsDAO;
	}

	/**
	 * operationDAO
	 *
	 * @return  the operationDAO
	 * @since   CodingExample Ver 1.0
	 */
	
	public OperationDAO getOperationDAO() {
		return operationDAO;
	}

	/**
	 * operationDAO
	 *
	 * @param   operationDAO    the operationDAO to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setOperationDAO(OperationDAO operationDAO) {
		this.operationDAO = operationDAO;
	}
	
	
}

