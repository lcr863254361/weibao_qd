/**
 * EtlService.java
 * com.orient.sysmodel.service.etl
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-5-2 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.service.etl;

import java.util.List;

import com.orient.sysmodel.domain.etl.EtlJob;
import com.orient.sysmodel.domain.etl.EtlLog;
import com.orient.sysmodel.domain.etl.EtlScript;
import com.orient.sysmodel.domain.etl.EtlTranslator;

/**
 * ClassName:EtlService
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-5-2		下午03:09:40
 *
 * @see 	 
 */
public interface EtlService {

	/**
	 * 
	
	 * @Method: createEtlJob 
	
	 * 创建Etl任务
	
	 * @param job
	
	 * @return void
	
	 * @throws
	 */
	public void createEtlJob(EtlJob job);
	
	/**
	 * 
	
	 * @Method: updateEtlJob 
	
	 * 更新Etl任务
	
	 * @param job
	
	 * @return void
	
	 * @throws
	 */
	public void updateEtlJob(EtlJob job);
	
	/**
	 * 
	
	 * @Method: createEtlLog 
	
	 * 创建Etl日志
	
	 * @param log
	
	 * @return void
	
	 * @throws
	 */
	public void createEtlLog(EtlLog log);
	
	/**
	 * 
	
	 * @Method: updateEtlLog 
	
	 * 更新Etl日志
	
	 * @param log
	
	 * @return void
	
	 * @throws
	 */
	public void updateEtlLog(EtlLog log);
	
	/**
	 * 
	
	 * @Method: findAll 
	
	 * 查询所有的Etl日志信息
	
	 * @return
	
	 * @return List<EtlLog>
	
	 * @throws
	 */
	public List<EtlLog> findAll();
	
	/**
	 * 
	
	 * @Method: findEtlLogsOfUserName 
	
	 * 查询用户有效的Etl日志
	
	 * @param userName
	 * @return
	
	 * @return List<EtlLog>
	
	 * @throws
	 */
	public List<EtlLog> findEtlLogsOfUserName(String userName);
	
	/**
	 * 
	
	 * @Method: findEtlLogById 
	
	 * 取得Etl日志
	
	 * @param id
	 * @return
	
	 * @return EtlLog
	
	 * @throws
	 */
	public EtlLog findEtlLogById(String id);
	
	/**
	 * 
	
	 * @Method: findEtlScripts 
	
	 * 取得ETL脚本
	
	 * @param userName
	 * @param fileType
	 * @param importType
	 * @return
	
	 * @return List<EtlScript>
	
	 * @throws
	 */
	public List<EtlScript> findEtlScripts(String userName, String fileType, String importType);
	
	/**
	 * 
	
	 * @Method: findEtlScripts 
	
	 * 取得ETL脚本
	
	 * @param userName
	 * @param scriptname
	 * @return
	
	 * @return List<EtlScript>
	
	 * @throws
	 */
	public List<EtlScript> findEtlScripts(String userName, String scriptname);
	
	/**
	 * 
	
	 * @Method: createEtlScript 
	
	 * 创建Etl导入脚本
	
	 * @param etlScript
	
	 * @return void
	
	 * @throws
	 */
	public void createEtlScript(EtlScript etlScript);
	
	/**
	 * 
	
	 * @Method: updateEtlScript 
	
	 * 更新Etl导入脚本
	
	 * @param etlScript
	
	 * @return void
	
	 * @throws
	 */
	public void updateEtlScript(EtlScript etlScript);
	
	/**
	 * 
	
	 * @Method: findEtlTranslators 
	
	 * 取得Etl导入映射关系
	
	 * @param scriptid
	 * @param tableids 表id的集合,用","分隔
	 * @return
	
	 * @return List<EtlTranslator>
	
	 * @throws
	 */
	public List<EtlTranslator> findEtlTranslators(String scriptid, String tableids);
	
	/**
	 * 
	
	 * @Method: findEtlTranslatorsOfTable 
	
	 * 取得指定表的Etl导入映射关系
	
	 * @param scriptid
	 * @param tableid
	 * @return
	
	 * @return List<EtlTranslator>
	
	 * @throws
	 */
	public List<EtlTranslator> findEtlTranslatorsOfTable(String scriptid, String tableid);
	
	/**
	 * 
	
	 * @Method: createEtlTranslator 
	
	 * 创建Etl导入映射关系
	
	 * @param etlTranslator
	
	 * @return void
	
	 * @throws
	 */
	public void createEtlTranslator(EtlTranslator etlTranslator);
	
	/**
	 * 
	
	 * @Method: updateEtlTranslator 
	
	 * 更新Etl导入映射关系
	
	 * @param etlTranslator
	
	 * @return void
	
	 * @throws
	 */
	public void updateEtlTranslator(EtlTranslator etlTranslator);
}

