package com.orient.webservice.base;

public interface IDBOP {
	/** 
	 * SQL插入操作.
	 *
	 * @Method: insert 
	 * @param: username 用户名
	 * @param: schemaName 数据模型名称
	 * @param: version 数据模型版本
	 * @param: tableName 数据表显示名
	 * @param: arg 数据列的键值对,如:"ID:=:123:==:型号:=:RT-61:==:年:=:2013"
	 * @return  String 插入成功返回新增记录编号，插入失败返回错误信息
	 */
	String insert(String username,String schemaName,String version,String tableName,String arg);
	/** 
	 * SQL修改操作.
	 *
	 * @Method: update 
	 * @param: username 用户名
	 * @param: schemaName 数据模型名称
	 * @param: version 数据模型版本
	 * @param: tableName 数据表显示名
	 * @param: arg 数据列的键值对,如:"型号:=:RT-61:==:年:=:2013"
	 * @param dataId  更新记录的主键(只能是单个主键)
	 * @return  String 修改成功返回成功信息，修改失败返回错误信息
	 */
	String update(String username, String schemaName, String version, String tableName, String arg,String dataId);
	/** 
	 * SQL删除操作.
	 *
	 * @Method: delete 
	 * @param: username 用户名
	 * @param: schemaName 数据模型名称
	 * @param: version 数据模型版本
	 * @param: tableName 数据表显示名
	 * @param dataId  删除记录的主键（可以是多个主键，需要以逗号分割）
	 * @return  String 删除成功返回成功信息，删除失败返回错误信息
	 */
	String delete(String username, String schemaName, String version, String tableName,String dataId);
	
	/** 
	 * SQL级联删除操作.
	 *
	 * @Method: deleteCascade 
	 * @param: username 用户名
	 * @param: schemaName 数据模型名称
	 * @param: version 数据模型版本
	 * @param: tableName 数据表显示名
	 * @param dataId  删除记录的主键（可以是多个主键，需要以逗号分割）
	 * @return  String 删除成功返回成功信息，删除失败返回错误信息
	 */
	String deleteCascade(String username, String schemaName, String version, String tableName,String dataId);
}
