package com.orient.sysmodel.service.user;

import java.util.List;

import com.orient.sysmodel.domain.user.UserColumn;
import com.orient.sysmodel.domain.user.UserColumnDAO;



public class UserColumnServiceImpl implements UserColumnService {

	private UserColumnDAO dao;

	/**
	 * dao
	 *
	 * @return  the dao
	 * @since   CodingExample Ver 1.0
	 */
	
	public UserColumnDAO getDao() {
		return dao;
	}

	/**
	 * dao
	 *
	 * @param   dao    the dao to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setDao(UserColumnDAO dao) {
		this.dao = dao;
	}

	/**
	
	 * @Method: findAllUserColumn 
	
	 * TODO 查找用户表所有字段信息
	
	 * @return 
	
	 * @see com.orient.sysmodel.service.user.UserColumnService#findAllUserColumn() 
	
	 */
	
	public List<UserColumn> findAllUserColumn() {
		List<UserColumn> tempResult = dao.findAll();
		if(tempResult!=null && tempResult.size()>0){
			
			/*for(int i=0;i<tempResult.size();i++){
				UserColumn userColumn = tempResult.get(i);
				if(userColumn.getEnmuId()!=null && !userColumn.getEnmuId().equalsIgnoreCase("")){
					
					//取得Enmu数据userColumn
				}
				tempResult.set(i, userColumn);
			}*/
			
			return tempResult;
		}else{
			return tempResult;
		}
	}

	/**
	
	 * @Method: findByUserColumnName 
	
	 * TODO
	
	 * @param userColumnName
	 * @return 
	
	 * @see com.orient.sysmodel.service.user.UserColumnService#findByUserColumnName(java.lang.String) 
	
	 */
	
	public UserColumn findByUserColumnName(String userColumnName) {
		
		List<UserColumn> userColumnList = dao.findBySColumnName(userColumnName);
		if(userColumnList!=null && userColumnList.size()>0){
			return userColumnList.get(0);
		}else{
			return null;
		}
		
	}

	/**
	
	 * @Method: findUserColumnForDispalyShow 
	
	 * TODO 查找所有做为列表显示的用户信息字段
	
	 * @return 
	
	 * @see com.orient.sysmodel.service.user.UserColumnService#findUserColumnForDispalyShow() 
	
	 */
	
	public List<UserColumn> findUserColumnForDispalyShow() {
		
		return dao.findByIsDispalyinfoShow("1");
		
	}

	/**
	
	 * @Method: findUserColumnForInfoSearch 
	
	 * TODO 查找所有做为查询条件的用户信息字段
	
	 * @return 
	
	 * @see com.orient.sysmodel.service.user.UserColumnService#findUserColumnForInfoSearch() 
	
	 */
	
	public List<UserColumn> findUserColumnForInfoSearch() {
		
		return dao.findByIsForInfosearch("1");
		
	}

	/**
	
	 * @Method: findUserColumnForSearch 
	
	 * TODO 查找所有做为检索条件的用户信息字段
	
	 * @return 
	
	 * @see com.orient.sysmodel.service.user.UserColumnService#findUserColumnForSearch() 
	
	 */
	
	public List<UserColumn> findUserColumnForSearch() {
		
		return dao.findByIsForSearch("1");
		
	}

	/**
	
	 * @Method: findUserColumnForViewShow 
	
	 * TODO 查找所有做为明细显示的用户信息字段
	
	 * @return 
	
	 * @see com.orient.sysmodel.service.user.UserColumnService#findUserColumnForViewShow() 
	
	 */
	
	public List<UserColumn> findUserColumnForViewShow() {
		
		return dao.findByIsDispalyinfoShow("1");
		
	}
	
	

	

}
