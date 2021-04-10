/**
 * FunctionService.java
 * com.sysmodel.service.role
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-3-20 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.service.role;

import java.util.List;

import com.orient.sysmodel.domain.role.Function;

/**
 * ClassName:FunctionService
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-3-20		上午11:11:26
 *
 * @see 	 
 */
public interface FunctionService {

	/**
	 * 查找功能点
	
	 * @Method: findByCode 
	
	 * TODO
	
	 * @param code
	 * @return
	
	 * @return Function
	
	 * @throws
	 */
	public Function findByCode(String code);
	
	/**根据属性和值查找*/
	public Function findByProperty(String propertyName, Object value);
	
	/**
	 * 查找所有显示的功能点
	
	 * @Method: findAllShowFunction 
	
	 * TODO
	
	 * @return
	
	 * @return List<Function>
	
	 * @throws
	 */
	public List<Function> findAllShowFunction();
	
	/**
	 * 查找所有功能点
	
	 * @Method: findAllFunction 
	
	 * TODO
	
	 * @return
	
	 * @return List<Function>
	
	 * @throws
	 */
	public List<Function> findAllFunction();
	
	/**
	 * 查找功能点
	
	 * @Method: findById 
	
	 * TODO
	
	 * @param functionId
	 * @return
	
	 * @return Function
	
	 * @throws
	 */
	public Function findById(String functionId);

	public List<Function> findByPid(Long pid);
	
	/**
	 * 新增功能点信息
	
	 * @Method: createFunction 
	
	 * TODO
	
	 * @param function
	
	 * @return void
	
	 * @throws
	 */
	public void createFunction(Function function);
	
	/**
	 * 更新功能点信息
	
	 * @Method: updateFunction 
	
	 * TODO
	
	 * @param function
	
	 * @return void
	
	 * @throws
	 */
	public void updateFunction(Function function, boolean isUpdate);
	
	/**
	 * 删除功能点(包括子功能点,相关权限信息)
	
	 * @Method: deleteFunction 
	
	 * TODO
	
	 * @param functionId
	
	 * @return void
	
	 * @throws
	 */
	public void deleteFunction(String functionId);
	
	/**
	 * 删除功能点(包括子功能点,相关权限信息)
	
	 * @Method: deleteFunction 
	
	 * TODO
	
	 * @param function
	
	 * @return void
	
	 * @throws
	 */
	public void deleteFunction(Function function);
}

