/**
 * IRole.java
 * com.orient.sysmodel.roleengine.operationinterface
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-4-5 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.operationinterface;

import com.orient.metamodel.operationinterface.IMatrix;
import com.orient.metamodel.operationinterface.ISchema;

import java.util.List;

/**
 * ClassName:IRole
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-5		上午10:40:48
 *
 * @see 	 
 */
public interface IRole {


	/**
	 *

	 * @Method: getId

	 * 获取角色id

	 * @return

	 * @return String

	 * @throws
	 */
	public String getId();

	/**
	 * 
	
	 * @Method: getAllUsers 
	
	 * 取得角色下的所有用户
	
	 * @return
	
	 * @return List<IUser>
	
	 * @throws
	 */
	public List<IUser> getAllUsers();
	
	/**
	 * 
	
	 * @Method: getAllValidUsers 
	
	 * 取得角色下的所有启用的用户
	
	 * @return
	
	 * @return List<IUser>
	
	 * @throws
	 */
	public List<IUser> getAllValidUsers();
	
	/**
	 * 
	
	 * @Method: getAllValidUsersNotFlag 
	
	 * 取得角色下的所有启用的非固化用户
	
	 * @return
	
	 * @return List<IUser>
	
	 * @throws
	 */
	public List<IUser> getAllValidUsersNotFlag();
	
	/**
	 * 
	
	 * @Method: getUserById 
	
	 * 取得角色下的指定id的用户
	
	 * @param id
	 * @return
	
	 * @return IUser
	
	 * @throws
	 */
	public IUser getUserById(String id);
	
	/**
	 * 
	
	 * @Method: getUserByUserName 
	
	 * 取得角色下的指定用户名的用户
	
	 * @param userName
	 * @return
	
	 * @return IUser
	
	 * @throws
	 */
	public IUser getUserByUserName(String userName);
	
	/**
	 * 
	
	 * @Method: getUserByAllName 
	
	 * 取得角色下的指定真实姓名的用户
	
	 * @param allName
	 * @return
	
	 * @return IUser
	
	 * @throws
	 */
	public IUser getUserByAllName(String allName);
	
	/**
	 * 取得角色下的所有功能点信息
	
	 * @Method: getAllFunctions 
	
	 * TODO
	
	 * @return
	
	 * @return List<IFunction>
	
	 * @throws
	 */
	public List<IFunction> getAllFunctions();
	
	/**
	 * 
	
	 * @Method: getFunctionById 
	
	 * 根据Id取得功能点
	
	 * @param functionId
	 * @return
	
	 * @return IFunction
	
	 * @throws
	 */
	public IFunction getFunctionById(String functionId);
	
	/**
	 * 
	
	 * @Method: getFunctionByName 
	
	 * 根据名称取得功能点
	
	 * @param functionName
	 * @return
	
	 * @return IFunction
	
	 * @throws
	 */
	public IFunction getFunctionByName(String functionName);
	
	/**
	 * 
	
	 * @Method: getFunctionByCode 
	
	 * 根据编码取得功能点
	
	 * @param code
	 * @return
	
	 * @return IFunction
	
	 * @throws
	 */
	public IFunction getFunctionByCode(String code);

	/**
	 * 
	
	 * @Method: getOverAllOperations 
	
	 * 取得角色下的操作权限信息
	
	 * @return
	
	 * @return IOverAllOperations
	
	 * @throws
	 */
	public List<IOverAllOperations> getAllOperations();
	
	/**
	 * 
	
	 * @Method: getPartOperations 
	
	 * 取得角色下的表访问权限信息
	
	 * @return
	
	 * @return List<IPartOperations>
	
	 * @throws
	 */
	public List<IPartOperations> getAllPartOperations();
	
	/**
	 * 
	
	 * @Method: getPartOperationsOfTable 
	
	 * 取得角色下的指定数据源的表访问权限信息
	
	 * @param tableId
	 * @return
	
	 * @return List<IPartOperations>
	
	 * @throws
	 */
	public List<IPartOperations> getPartOperationsOfTable(String tableId);
	
	/**
	 * 
	
	 * @Method: getRightsOfMatrix 
	
	 * 取得角色下的数据源访问权限
	
	 * @param matrixId
	 * @return
	
	 * @return IMatrixRight
	
	 * @throws
	 */
	//private MatrixRight getRightsOfMatrix(String matrixId);
	
	/**
	 * 
	
	 * @Method: getRightsOfMatrix 
	
	 * 取得角色下的数据源访问权限(包括字段访问权限)
	
	 * @param matrix
	 * @return
	
	 * @return IMatrixRight
	
	 * @throws
	 */
	public IMatrixRight getRightsOfMatrix(IMatrix matrix);
	
	/**
	 * 
	
	 * @Method: getRightsOfColumn 
	
	 * 取得角色下的数据源中指定字段的访问权限
	
	 * @param matrixId
	 * @param columnId
	 * @return
	
	 * @return IColumnRight
	
	 * @throws
	 */
	public IColumnRight getRightsOfColumn(String matrixId, String columnId);
	
	/**
	 * 
	
	 * @Method: getPartOperationsOfTableColumn 
	
	 * 取得角色下的指定数据源中指定字段的表访问权限信息
	
	 * @param tableId
	 * @param columnId
	 * @return
	
	 * @return IPartOperations
	
	 * @throws
	 */
	public IPartOperations getPartOperationsOfTableColumn(String tableId, String columnId);
	
	/**
	 * 
	
	 * @Method: getAllSchemas 
	
	 * 取得角色下的业务库信息
	
	 * @return
	
	 * @return List<ISchema>
	
	 * @throws
	 */
	public List<ISchema> getAllSchemas();
	
	/**
	 * 
	
	 * @Method: getAllTbomDirs 
	
	 * 取得角色下的Tbom信息
	
	 * @return
	
	 * @return List<ITbomDir>
	
	 * @throws
	 */
	public List<ITbomDir> getAllTbomDirs();
	
	/**
	 * 
	
	 * @Method: getAllTboms 
	
	 * 取得角色下的Tbom树数据信息，该Tbom是指Tbom树的根节点信息
	
	 * @return
	
	 * @return List<ITbom>
	
	 * @throws
	 */
	public List<ITbom> getAllTboms();
	
	/**
	 * 
	
	 * @Method: getTbomDirById 
	
	 * 取得指定的Tbom信息
	
	 * @param tbomDirId
	 * @return
	
	 * @return ITbomDir
	
	 * @throws
	 */
	public ITbomDir getTbomDirById(String tbomDirId);
	
	/**
	 * 
	
	 * @Method: getTbomDirByName 
	
	 * 取得指定的Tbom信息
	
	 * @param tbomDirName
	 * @return
	
	 * @return ITbomDir
	
	 * @throws
	 */
	public ITbomDir getTbomDirByName(String tbomDirName);
	
	/**
	 * 
	
	 * @Method: getAllArith 
	
	 * 取得角色下的算法信息 
	
	 * @return
	
	 * @return List<IArith>
	
	 * @throws
	 */
	public List<IArith> getAllAriths();
	
	/**
	 * 
	
	 * @Method: getAllValidAriths 
	
	 * 取得角色下启用的算法信息 
	
	 * @return
	
	 * @return List<IArith>
	
	 * @throws
	 */
	public List<IArith> getAllValidAriths();
	
	/**
	 * 
	
	 * @Method: getArithsByType 
	
	 * 取得角色下指定算法类型的算法信息(算法类型:数据库自带算法（0），自定义算法（1），自定义算法jar文件（2），自定义算法类名（3），自定义算法方法名（4）)
	
	 * @param type
	 * @return
	
	 * @return List<IArith>
	
	 * @throws
	 */
	public List<IArith> getArithsByType(String type);
	
	/**
	 * 
	
	 * @Method: getArithsByCategory 
	
	 * 取得角色下指定算法类别的算法信息
	
	 * @param category
	 * @return
	
	 * @return List<IArith>
	
	 * @throws
	 */
	public List<IArith> getArithsByCategory(String category);
	
	/**
	 * 
	
	 * @Method: getArithById 
	
	 * 取得角色下指定id的算法
	
	 * @param arithId
	 * @return
	
	 * @return IArith
	
	 * @throws
	 */
	public IArith getArithById(String arithId);
	
	/**
	 * 
	
	 * @Method: getArithByName 
	
	 * 取得角色下指定名称的算法
	
	 * @param arithName
	 * @return
	
	 * @return IArith
	
	 * @throws
	 */
	public IArith getArithByName(String arithName);
	
	/**
	 * 
	
	 * @Method: getAllFunctionTboms 
	
	 * 取得角色下的功能Tbom信息 
	
	 * @return
	
	 * @return List<IFunctionTbom>
	
	 * @throws
	 */
	public List<IRoleFunctionTbom> getAllFunctionTboms();
	
	/**
	 * 
	
	 * @Method: getFunctionTbom 
	
	 * 取得角色下指定的功能Tbom信息 
	
	 * @param functionId
	 * @param tbomDirId
	 * @return
	
	 * @return IRoleFunctionTbom
	
	 * @throws
	 */
	public IRoleFunctionTbom getFunctionTbom(String functionId, String tbomDirId);
	
	
	 /** 
	 * 返回角色名
	 * @Method: getName 
	 * @return 
	 */
	public String getName();
}

