/**
 * ArithServiceImpl.java
 * com.sysmodel.service.arith
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-3-20 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.service.arith;

import java.util.List;

import com.orient.sysmodel.domain.arith.Arith;
import com.orient.sysmodel.domain.arith.ArithDAO;

/**
 * ClassName:ArithServiceImpl
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-3-20		上午08:44:46
 *
 * @see 	 
 */
public class ArithServiceImpl implements ArithService {

	private ArithDAO dao;
	
	/**
	 * dao
	 *
	 * @return  the dao
	 * @since   CodingExample Ver 1.0
	 */
	
	public ArithDAO getDao() {
		return dao;
	}

	/**
	 * dao
	 *
	 * @param   dao    the dao to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setDao(ArithDAO dao) {
		this.dao = dao;
	}

	/**
	
	 * @Method: findById 
	
	 * TODO
	
	 * @param arithId
	 * @return 
	
	 * @see com.orient.sysmodel.service.arith.ArithService#findById(java.lang.String) 
	
	 */
	
	public Arith findById(String arithId) {
	
		return dao.findById(arithId);
		
	}
	
	/**
	
	 * @Method: findAll 
	
	 * TODO
	
	 * @param 
	 * @return 算法集合
	
	 * @see com.orient.sysmodel.service.arith.ArithService#findById(java.lang.String) 
	
	 */
	public List findAll() {
		return dao.findAll();
	}
	
	/**
	 * 取得多个算法的名称
	
	 * @Method: getArithNames 
	
	 * TODO
	
	 * @param arithIds
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getArithNames(String arithIds){
		String[] ids = arithIds.split(",");
		String names="";
		for(int i=0;i<ids.length;i++){
			Arith arith = dao.findById(ids[i]);
			names= names+arith.getName()+",";
		}
		if(names.length()>0){
			names = names.substring(0, names.length() - 1);
		}
		return names;
	}

}

