package com.orient.sysmodel.domain.user;
// default package

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.orient.metamodel.operationinterface.IEnum;
import com.orient.sysmodel.domain.role.RoleUser;
import com.orient.sysmodel.domain.role.RoleUserId;
import com.orient.sysmodel.operationinterface.IUser;
import com.orient.sysmodel.operationinterface.IUserColumn;


/**
 * User entity. @author MyEclipse Persistence Tools
 */
public class User extends AbstractUser implements java.io.Serializable, IUser {

    String roleIds;
    String roleNames;
    String overAllOperations;
    List tabList;//功能菜单
    List treeList;//左侧目录树
	// Constructors

    /** default constructor */
    public User() {
    }

	/** minimal constructor */
    public User(String userName, String allName, String password, Date createTime, String createUser, String state) {
        super(userName, allName, password, createTime, createUser, state);        
    }
    
    /** full constructor */
    public User(String userName, String allName, String password, String sex, String phone, String post, String specialty, String grade,
				Date createTime, String createUser, Date updateTime, String updateUser, String notes, String state, Date birthday,
				String mobile, String flg, Department dept, String isDel, String Email, Date passwordSetTime, String lockState, Date lockTime,
				String loginFailures, Date lastFailureTime, Set cwmSysPasswordHistories, String unit,String personClassify,String country,String nation,String identityCardNumber) {
        super(userName, allName, password, sex, phone, post, specialty, grade, createTime, createUser, updateTime, updateUser, notes, state, birthday, mobile, flg, dept, isDel, Email, passwordSetTime, lockState, lockTime, loginFailures, lastFailureTime, cwmSysPasswordHistories, unit,personClassify,country,nation,identityCardNumber);
    }

	/**
	 * roleIds
	 *
	 * @return  the roleIds
	 * @since   CodingExample Ver 1.0
	 */
	
	public String getRoleIds() {
		return roleIds;
	}

	/**
	 * roleIds
	 *
	 * @param   roleIds    the roleIds to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * roleNames
	 *
	 * @return  the roleNames
	 * @since   CodingExample Ver 1.0
	 */
	
	public String getRoleNames() {
		return roleNames;
	}

	/**
	 * roleNames
	 *
	 * @param   roleNames    the roleNames to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	/**
	 * overAllOperations
	 *
	 * @return  the overAllOperations
	 * @since   CodingExample Ver 1.0
	 */
	
	public String getOverAllOperations() {
		return overAllOperations;
	}

	/**
	 * overAllOperations
	 *
	 * @param   overAllOperations    the overAllOperations to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setOverAllOperations(String overAllOperations) {
		this.overAllOperations = overAllOperations;
	}

	/**
	 * tabList
	 *
	 * @return  the tabList
	 * @since   CodingExample Ver 1.0
	 */
	
	public List getTabList() {
		return tabList;
	}

	/**
	 * tabList
	 *
	 * @param   tabList    the tabList to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setTabList(List tabList) {
		this.tabList = tabList;
	}

	/**
	 * treeList
	 *
	 * @return  the treeList
	 * @since   CodingExample Ver 1.0
	 */
	
	public List getTreeList() {
		return treeList;
	}

	/**
	 * treeList
	 *
	 * @param   treeList    the treeList to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setTreeList(List treeList) {
		this.treeList = treeList;
	}

	/**
	 * 
	
	 * @Method: getSexList 
	
	 * 性别列表
	
	 * @return 
	
	 * @see com.orient.sysmodel.operationinterface.IUser#getSexList()
	 */
	public List<IEnum> getSexList(){
		List<UserColumn> userColumnList = this.getUserDAOFactory().getUserColumnDAO().findBySColumnName("SEX");
		if(userColumnList!=null && userColumnList.size()>0){
			List<IEnum> enumList = userColumnList.get(0).getEnumList();
			return enumList;
		}
		return null;
	}
	
	/**
	 * 
	
	 * @Method: getSexValue 
	
	 * 性别显示值
	
	 * @return 
	
	 * @see com.orient.sysmodel.operationinterface.IUser#getSexValue()
	 */
	public String getSexValue(){
		String value="";
		if(this.getSex() ==  null){
			return value;
		}
		List<UserColumn> userColumnList = this.getUserDAOFactory().getUserColumnDAO().findBySColumnName("SEX");
		if(userColumnList!=null && userColumnList.size()>0){
			List<IEnum> enumList = userColumnList.get(0).getEnumList();
			
			if(enumList!=null && enumList.size()>0){
				for(int i=0;i<enumList.size();i++){
					if(this.getSex().equalsIgnoreCase(enumList.get(i).getValue())){
						value = enumList.get(i).getDisplayValue();
					}
				}
			}
		}
		return value;
	}
   
	/**
	 * 
	
	 * @Method: getGradeList 
	
	 * 密级列表
	
	 * @return 
	
	 * @see com.orient.sysmodel.operationinterface.IUser#getGradeList()
	 */
    public List<IEnum> getGradeList(){
    	List<IUserColumn> userColumnList = this.getUserDAOFactory().getUserColumnDAO().findBySColumnName("GRADE");
		if(userColumnList!=null && userColumnList.size()>0){
			List<IEnum> enumList =userColumnList.get(0).getEnumList() ;
			return enumList;
		}
		return null;
    }
	
    /**
     * 
    
     * @Method: getGradeValue 
    
     * 密级显示值
    
     * @return 
    
     * @see com.orient.sysmodel.operationinterface.IUser#getGradeValue()
     */
	public String getGradeValue(){
		String value="";
		if(this.getGrade() == null){
			return value;
		}
		List<UserColumn> userColumnList = this.getUserDAOFactory().getUserColumnDAO().findBySColumnName("GRADE");
		if(userColumnList!=null && userColumnList.size()>0){
			List<IEnum> enumList = userColumnList.get(0).getEnumList();
			
			if(enumList!=null && enumList.size()>0){
				for(int i=0;i<enumList.size();i++){
					if(this.getGrade().equalsIgnoreCase(enumList.get(i).getValue())){
						value = enumList.get(i).getDisplayValue();
					}
				}
			}
		}
		return value;
	}


	/**
	 * 
	 * @Method: getStateList 
	 * 启用标志列表
	 * @return 
	 * @see com.orient.sysmodel.operationinterface.IUser#getStateList()
	 */
	public List<IEnum> getStateList(){
		List<IUserColumn> userColumnList = this.getUserDAOFactory().getUserColumnDAO().findBySColumnName("STATE");
		if(userColumnList!=null && userColumnList.size()>0){
			List<IEnum> enumList =userColumnList.get(0).getEnumList() ;
			return enumList;
		}
		return null;
	}	
		
	/**
     * 
     * @Method: getStateValue 
     * 启用标志显示值
     * @return 
     * @see com.orient.sysmodel.operationinterface.IUser#getStateValue()
     */
	public String getStateValue(){
		String value="";
		if(this.getState() == null){
			return value;
		}
		List<UserColumn> userColumnList = this.getUserDAOFactory().getUserColumnDAO().findBySColumnName("STATE");
		if(userColumnList!=null && userColumnList.size()>0){
			List<IEnum> enumList = userColumnList.get(0).getEnumList();
			
			if(enumList!=null && enumList.size()>0){
				for(int i=0;i<enumList.size();i++){
					if(this.getState().equalsIgnoreCase(enumList.get(i).getValue())){
						value = enumList.get(i).getDisplayValue();
					}
				}
			}
		}
		return value;
	}
	
	/**
	 * 
	 * @Method: getPostList 
	 * 职务列表
	 * @return 
	 * @see com.orient.sysmodel.operationinterface.IUser#getPostList()
	 */
	public List<IEnum> getPostList(){
		List<IUserColumn> userColumnList = this.getUserDAOFactory().getUserColumnDAO().findBySColumnName("POST");
		if(userColumnList!=null && userColumnList.size()>0){
			List<IEnum> enumList =userColumnList.get(0).getEnumList() ;
			return enumList;
		}
		return null;
	}
	
	/**
     * 
     * @Method: getPostValue 
     * 职务显示值
     * @return 
     * @see com.orient.sysmodel.operationinterface.IUser#getPostValue()
     */
	public String getPostValue(){
		String value="";
		if(this.getPost() == null){
			return value;
		}
		List<UserColumn> userColumnList = this.getUserDAOFactory().getUserColumnDAO().findBySColumnName("POST");
		if(userColumnList!=null && userColumnList.size()>0){
			List<IEnum> enumList = userColumnList.get(0).getEnumList();
			
			if(enumList!=null && enumList.size()>0){
				for(int i=0;i<enumList.size();i++){
					if(this.getPost().equalsIgnoreCase(enumList.get(i).getValue())){
						value = enumList.get(i).getDisplayValue();
					}
				}
			}
		}
		return value;
	}
	
	/**
	 * 
	
	 * @Method: getUserColumnBySTName 
	
	 * 根据字段名查找字段信息
	
	 * @param stName
	 * @return 
	
	 * @see com.orient.sysmodel.operationinterface.IUser#getUserColumnBySTName(java.lang.String)
	 */
	public IUserColumn getUserColumnBySTName(String stName){
		List<IUserColumn> userColumnList = this.getUserDAOFactory().getUserColumnDAO().findBySColumnName(stName);
		if(userColumnList!=null && userColumnList.size()>0){
			return userColumnList.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 
	
	 * @Method: getAllUserColumns 
	
	 * 取得所有的用户字段信息
	
	 * @return 
	
	 * @see com.orient.sysmodel.operationinterface.IUser#getAllUserColumns()
	 */
	public List<IUserColumn> getAllUserColumns(){
		return this.getUserDAOFactory().getUserColumnDAO().findAll();
	}
	
	/**
	 * 
	
	 * @Method: getUserColumnsForSearch 
	
	 * 取得用于用户信息页面查询的字段信息
	
	 * @return 
	
	 * @see com.orient.sysmodel.operationinterface.IUser#getUserColumnsForSearch()
	 */
	public List<IUserColumn> getUserColumnsForSearch(){
		return this.getUserDAOFactory().getUserColumnDAO().findByIsForSearch("1");
	}
	
	/**
	 * 
	
	 * @Method: getUserColumnsForDisplayShow 
	
	 * 取得用于用户信息列表页面的字段信息
	
	 * @return 
	
	 * @see com.orient.sysmodel.operationinterface.IUser#getUserColumnsForDisplayShow()
	 */
	public List<IUserColumn> getUserColumnsForDisplayShow(){
		return this.getUserDAOFactory().getUserColumnDAO().findByDisplayShow("1");
	}
	
	/**
	 * 
	
	 * @Method: getUserColumnsForEditShow 
	
	 * 取得用于用户信息明细和修改页面的字段信息
	
	 * @return 
	
	 * @see com.orient.sysmodel.operationinterface.IUser#getUserColumnsForEditShow()
	 */
	public List<IUserColumn> getUserColumnsForEditShow(){
		return this.getUserDAOFactory().getUserColumnDAO().findByEditShow("1");
	}
	
	/**
	 * 
	
	 * @Method: getUserColumnsForUserRoleSearch 
	
	 * 取得用于用户角色信息页面查询的字段信息
	
	 * @return 
	
	 * @see com.orient.sysmodel.operationinterface.IUser#getUserColumnsForUserRoleSearch()
	 */
	public List<IUserColumn> getUserColumnsForUserRoleSearch(){
		return this.getUserDAOFactory().getUserColumnDAO().findByIsForInfosearch("1");
	}
	
	/**
	 * 
	
	 * @Method: getUserColumnsForUserRoleDisplayShow 
	
	 * 取得用于用户角色信息列表页面的字段信息
	
	 * @return 
	
	 * @see com.orient.sysmodel.operationinterface.IUser#getUserColumnsForUserRoleDisplayShow()
	 */
	public List<IUserColumn> getUserColumnsForUserRoleDisplayShow(){
		return this.getUserDAOFactory().getUserColumnDAO().findByIsDispalyinfoShow("1");
	}
	
	/**
	 * 
	
	 * @Method: getUserColumnsForUserRoleEditShow 
	
	 * 取得用于用户角色信息明细页面的字段信息
	
	 * @return 
	
	 * @see com.orient.sysmodel.operationinterface.IUser#getUserColumnsForUserRoleEditShow()
	 */
	public List<IUserColumn> getUserColumnsForUserRoleEditShow(){
		return this.getUserDAOFactory().getUserColumnDAO().findByIsViewinfoShow("1");
	}
	

	
	@SuppressWarnings("unchecked")
	public RoleUser findRoleUser(RoleUserId roleUserId)
	{
		for(Iterator<RoleUser> it = this.getRoleUsers().iterator(); it.hasNext();)
		{
			RoleUser roleUser = it.next();
			if(roleUser.getId().toString().equals(roleUserId.toString()))
				return roleUser;
		}
		
		return null;
	}
	
	public RoleUser deleteRoleUser(RoleUserId roleUserId)
	{
		RoleUser roleUser = findRoleUser(roleUserId);
		if(roleUser!=null)
			this.getRoleUsers().remove(roleUser);
		
		return roleUser;
	}
}
