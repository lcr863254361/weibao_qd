package com.orient.sysmodel.service.user;

import java.util.List;
import java.util.Map;

import com.orient.sysmodel.domain.role.Function;
import com.orient.sysmodel.domain.user.LightweightUser;
import com.orient.sysmodel.domain.user.User;
import org.springframework.stereotype.Service;


public interface UserService {
	
	/**
	 * 查询用户所在部门信息;
	 * 
	 * @param user_id
	 * @return
	 */
	public List queryUserDept(String user_id);
	/**
	 * 新增用户
	
	 * @Method: createUser 
	
	 * TODO
	
	 * @param user
	
	 * @return void
	
	 * @throws
	 */
	public void createUser(User user);
	
	/**
	 * 更新用户信息
	
	 * @Method: updateUser 
	
	 * TODO
	
	 * @param user
	
	 * @return void
	
	 * @throws
	 */
	public void updateUser(User user);
	
	/**
	 * 删除用户信息
	
	 * @Method: delete 
	
	 * TODO
	
	 * @param user
	
	 * @return void
	
	 * @throws
	 */
	public void delete(User user);
	
	/**
	 * 批量删除用户信息
	
	 * @Method: delete 
	
	 * TODO
	
	 * @param userids 用户的ID集合, 用逗号隔开
	 * @return
	
	 * @return boolean
	
	 * @throws
	 */
	public boolean delete(String userIds);
	
	/**
	 * 根据Id查找用户
	
	 * @Method: findById 
	
	 * TODO
	
	 * @param userId
	 * @return
	
	 * @return User
	
	 * @throws
	 */
	public User findById(String userId);

	/**
	 * 示例查询
	 * @param instance
	 * @param betweens
	 * @return
	 */
	public List findByExampleLike(LightweightUser instance, Map<String, String> betweens);
	
	/**
	 * 根据Id查找用户(只显示用户的基本信息，role等等除外)
	 *  @Enclosing_Method  : findUserById
	 *  @Version           : v1.00
	 */
	public User findUserById(String userId);
	
	/**
	 * 根据用户名查找用户
	
	 * @Method: findByUserName 
	
	 * TODO
	
	 * @param userName
	 * @return
	
	 * @return User
	
	 * @throws
	 */
	public User findByUserName(String userName);
	
	/**
	 * 根据用户名查找用户(只显示用户的基本信息，role等等除外)
	 *  @Enclosing_Method  : findUserByUserName
	 *  @Version           : v1.00
	 */
	public User findUserByUserName(String userName);
	
	
	/**
	 * 查找某个功能的左侧目录树
	
	 * @Method: queryLeftTree 
	
	 * TODO
	
	 * @param functionid
	 * @param roleIds
	 * @return
	
	 * @return List
	
	 * @throws
	 */
	public List<Map> queryLeftTree(String functionid, String roleIds);
	
	/**
	 * 查找所有有效用户
	
	 * @Method: findAllUser 
	
	 * TODO
	
	 * @return
	
	 * @return List<User>
	
	 * @throws
	 */
	public List<User> findAllUser();
	
	/**
	 * 校验用户密码是否正确
	
	 * @Method: validateUserPassword 
	
	 * TODO
	
	 * @param user
	 * @param password 加密的密码串
	 * @return
	
	 * @return boolean
	
	 * @throws
	 */
	public boolean validateUserPassword(User user, String password);
	
	/**
	 * 取得有权限的功能
	
	 * @Method: findFunctionsByCode 
	
	 * TODO
	
	 * @param roleIds
	 * @param functioncode
	 * @return
	
	 * @return List<Function>
	
	 * @throws
	 */
	public List<Function> findFunctionsByCode(String roleIds, String functioncode );
	
	public List getUserListForDisplay();
	
	/**
	 * 取得用户密级
	
	 * @Method: userSecrecyMap 
	
	 * TODO
	
	 * @param userName
	 * @return
	
	 * @return Map(value 加密字符串;display_value 未加密字符串)
	
	 * @throws
	 */
	//public Map userSecrecyMap(String userName);
	
	/*public List<String> queryRolesByUserId(String userNameId) ;

	public List<String> queryRoles(String userName) ;
	
	public Map queryUserInfo(String userName) ;
	
	private Map setUserContainer(List list);*/

	

}
