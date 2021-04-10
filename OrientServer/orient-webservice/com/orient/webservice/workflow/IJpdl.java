package com.orient.webservice.workflow;

import java.util.Map;

/**
 * JBPM工作流信息操作的webservice接口
 * 
 * @author XIUJUN XU
 * @version 4.0
 * @since May 14, 2010
 */
public interface IJpdl {

	/**
	 * 获取业务库信息，读取数据库中所有业务库的数据类，数据视图，以及数据类的关系属性.
	 * 
	 * @return 业务库信息字符串
	 */
	public String getSource();
	
	/**
	 * 获取数据类的有关信息.
	 * 
	 * @param tableId
	 *            数据类的ID
	 * @param type
	 *            获取信息类型，如column表示获取其字段信息，reftable获取关联数据类信息等
	 * 
	 * @return 关联数据类信息字符串
	 */
	public String getTableDetail(String tableId,String type);
	
	/**
	 * 修改JPDL的基本属性，如上锁、解锁等.
	 * 
	 * @param name
	 *            JPDL名称
	 * @param version
	 *            JPDL版本
	 * @param username
	 *            用户名
	 * @param type
	 *            设置JPDL属性类型，如给JPDL上锁解锁等
	 * @param info
	 *            设置属性的信息
	 * 
	 * @return 完成操作后返回的信息（成功、失败等信息）
	 */
	public String setJpdl(String name,String version,String username,String type,String info);
	
	/**
	 * 数据库中所有的JPDL信息.
	 * 
	 * @return JPDL信息字符串
	 */
	public String getJpdlInfo();
	
	/**
	 * 获取指定JPDL信息.
	 * 
	 * @param name
	 *            JPDL名称
	 * @param version
	 *            JPDL版本号
	 * @param type
	 *            信息类型，是否上锁，是否正在被使用等
	 * 
	 * @return JPDL信息字符串
	 */
	public String getJpdl(String name,String version,String type);
	
	/**
	 * 删除数据库中的JPDL信息.
	 * 
	 * @param name
	 *            JPDL名称
	 * @param version
	 *            JPDL版本号
	 * @param username
	 *            用户名
	 * 
	 * @return 删除成功与否信息
	 */
	public String deleteJpdl(String name,String version,String username);
	
	/**
	 * 获取工作流文件.
	 * 
	 * @param id
	 *            工作流ID
	 * 
	 * @return key为文件名，value为字符数组
	 */
	public Map<String,byte[]> getJdpl(String id);
	
	/**
	 *@Function Name:  getAssignee
	 *@Description: @param type
	 *@Description: @return 得到用户 部门 角色信息
	 *@Date Created:  2013-1-5 上午09:37:22
	 *@Author:  Pan Duan Duan
	 *@Last Modified:     ,  Date Modified: 
	 */
	public String getAssignee(int type);
	
}
