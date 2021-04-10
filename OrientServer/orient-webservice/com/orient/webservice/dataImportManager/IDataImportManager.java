package com.orient.webservice.dataImportManager;

public interface IDataImportManager {
	/** 
	 * 根据schema名称和版本号获取schema的xml文件。
	 *
	 * @Method: getSchema
	 * @param: name 数据模型名称
	 * @param: version 数据模型版本号
	 * @return  String
	 */
	String getSchemaXml(String name,String version);
	/** 
	 * 获取数据库中的所有数据模型的简要信息.
	 *
	 * @Method: getSchema
	 * @return  String 返回所有数据模型的集合字符串
	 */
	String getSchema();
	/**
	 * 验证用户名和密码;
	 * @param name 用户名
	 * @param password 密码
	 * @param type schemaName===schemaVersion
	 * @return  
	 * 			0      "用户名不存在输入错误的用户名"
	 * 			1	   "用户密码错误"
	 * 			type   "用户名和密码均正确"
	 */
    public String check(String name,String password,String type);
}
