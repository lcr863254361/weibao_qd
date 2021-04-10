/**
 * IArith.java
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

import java.util.Set;

import com.orient.sysmodel.domain.arith.Arith;

/**
 * ClassName:IArith
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-5		上午11:02:03
 *
 * @see 	 
 */
public interface IArith {

	public String getId();
	
	/**
	 * 
	
	 * @Method: getName 
	
	 * 算法名称
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getName();
	
	/**
	 * 
	
	 * @Method: getType 
	
	 * 算法类型，分数据库自带算法（0），自定义算法（1），自定义算法jar文件（2），自定义算法类名（3），自定义算法方法名（4）
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getType();
	
	/**
	 * 
	
	 * @Method: getCategory 
	
	 * 算法类别
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getCategory();
	
	/**
	 * 
	
	 * @Method: getDescription 
	
	 * 算法描述
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getDescription();
	
	/**
	 * 
	
	 * @Method: getFileName 
	
	 * 自定义算法的文件名(可以多个)，以‘，’分割
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getFileName();
	
	/**
	 * 
	
	 * @Method: getMethodName 
	
	 * 自定义算法的方法名
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getMethodName();
	
	/**
	 * 
	
	 * @Method: getParaNumber 
	
	 * 算法的参数个数
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getParaNumber();
	
	/**
	 * 
	
	 * @Method: getParaType 
	
	 * 算法的参数类型，以‘，’分割
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getParaType();
	
	/**
	 * 
	
	 * @Method: getRefLib 
	
	 * 引用的lib包名
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getRefLib();
	
	/**
	 * 
	
	 * @Method: getDataType 
	
	 * 算法返回类型
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getDataType();
	
	/**
	 * 
	
	 * @Method: getIsValid 
	
	 * 是否启用，是否有效，1表示有效和启用，0表示无效和删除
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getIsValid();
	
	/**
	 * 
	
	 * @Method: getArithType 
	
	 * 算法所对应的函数是单行函数还是聚集函数，0表示单行函数，1表示聚集函数
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getArithType();
	
	/**
	 * 
	
	 * @Method: getLeastNumber 
	
	 * 取得最少的参数个数
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getLeastNumber();
	
	/**
	 * 
	
	 * @Method: getClassName 
	
	 * 取得自定义算法所在类名
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getClassName();
	
	/**
	 * 
	
	 * @Method: getClassPackage 
	
	 * 取得自定义算法所在类的包名
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getClassPackage();
	
	/**
	 * 
	
	 * @Method: getFileNumber 
	
	 * 取得自定义算法的文件数
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getFileNumber();
	
	/**
	 * 
	
	 * @Method: getArithMethod 
	
	 * 取得数据库内置算法公式
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getArithMethod();
	
	/**
	 * 
	
	 * @Method: getMainJar 
	
	 * 取得算法的主jar包标识
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getMainJar();
	
	/**
	 * 取得父算法
	
	 * @Method: getParentarith 
	
	 * TODO
	
	 * @return
	
	 * @return IArith
	
	 * @throws
	 */
	public IArith getParentarith();
	
	/**
	 * 
	
	 * @Method: getFileLocation 
	
	 * 取得算法文件所在路径
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getFileLocation();
	
	/**
	 * 
	
	 * @Method: getChildrenArith 
	
	 * 取得子算法
	
	 * @return
	
	 * @return Set
	
	 * @throws
	 */
	public Set getChildrenArith();
	
	
	
	
}

