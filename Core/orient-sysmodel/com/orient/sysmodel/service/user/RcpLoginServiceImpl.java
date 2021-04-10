/**
 * RcpLoginServiceImpl.java
 * com.orient.sysmodel.service.user
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-4-27 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.service.user;

import java.util.List;

import com.orient.metamodel.metadomain.Table;
import com.orient.metamodel.metaengine.dao.TableDAO;
import com.orient.sysmodel.domain.user.RcpLogin;
import com.orient.sysmodel.domain.user.RcpLoginDAO;

/**
 * ClassName:RcpLoginServiceImpl
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-27		下午03:10:53
 *
 * @see 	 
 */
public class RcpLoginServiceImpl implements RcpLoginService {

	private RcpLoginDAO dao;
	private TableDAO tableDao; 
	/**
	 * dao
	 *
	 * @return  the dao
	 * @since   CodingExample Ver 1.0
	 */
	
	public RcpLoginDAO getDao() {
		return dao;
	}


	/**
	 * dao
	 *
	 * @param   dao    the dao to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setDao(RcpLoginDAO dao) {
		this.dao = dao;
	}


	/**
	 * tableDao
	 *
	 * @return  the tableDao
	 * @since   CodingExample Ver 1.0
	 */
	
	public TableDAO getTableDao() {
		return tableDao;
	}


	/**
	 * tableDao
	 *
	 * @param   tableDao    the tableDao to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setTableDao(TableDAO tableDao) {
		this.tableDao = tableDao;
	}


	/**
	 * 
	
	 * @Method: createRcpLogin 
	
	 * 创建客户端登陆记录
	
	 * @param rcpLogin 
	
	 * @see com.orient.sysmodel.service.user.RcpLoginService#createRcpLogin(com.orient.sysmodel.domain.user.RcpLogin)
	 */
	public void createRcpLogin(RcpLogin rcpLogin){
		dao.save(rcpLogin);
	}
	
	/**
	 * 
	
	 * @Method: deleteRCPLogin 
	
	 * 删除客户端登陆记录
	
	 * @param rcpLogin 
	
	 * @see com.orient.sysmodel.service.user.RcpLoginService#deleteRCPLogin(com.orient.sysmodel.domain.user.RcpLogin)
	 */
	public void deleteRCPLogin(RcpLogin rcpLogin){
		dao.delete(rcpLogin);
	}
	
	/**
	 * 
	
	 * @Method: findRcpLoginOfTable 
	
	 * 取得指定表的客户端登陆记录
	
	 * @param tableId
	 * @return 
	
	 * @see com.orient.sysmodel.service.user.RcpLoginService#findRcpLoginOfTable(java.lang.String)
	 */
	public List<RcpLogin> findRcpLoginOfTable(String tableId){
		Table table = tableDao.findById(tableId);
		return dao.findByProperty("id.schemaId", table.getSchema().getId());
	}
	
}

