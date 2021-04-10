/**
 * EtlServiceImpl.java
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

import java.io.Writer;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.lob.SerializableClob;

import com.orient.sysmodel.domain.etl.EtlJob;
import com.orient.sysmodel.domain.etl.EtlJobDAO;
import com.orient.sysmodel.domain.etl.EtlLog;
import com.orient.sysmodel.domain.etl.EtlLogDAO;
import com.orient.sysmodel.domain.etl.EtlScript;
import com.orient.sysmodel.domain.etl.EtlScriptDAO;
import com.orient.sysmodel.domain.etl.EtlTranslator;
import com.orient.sysmodel.domain.etl.EtlTranslatorDAO;

/**
 * ClassName:EtlServiceImpl
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-5-2		下午03:10:03
 *
 * @see 	 
 */
public class EtlServiceImpl {

	private EtlJobDAO etlJobDao;
	private EtlLogDAO etlLogDao;
	private EtlScriptDAO etlScriptDao;
	private EtlTranslatorDAO etlTranslatorDao;
	
	/**
	 * etlJobDao
	 *
	 * @return  the etlJobDao
	 * @since   CodingExample Ver 1.0
	 */
	
	public EtlJobDAO getEtlJobDao() {
		return etlJobDao;
	}

	/**
	 * etlJobDao
	 *
	 * @param   etlJobDao    the etlJobDao to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setEtlJobDao(EtlJobDAO etlJobDao) {
		this.etlJobDao = etlJobDao;
	}

	/**
	 * etlLogDao
	 *
	 * @return  the etlLogDao
	 * @since   CodingExample Ver 1.0
	 */
	
	public EtlLogDAO getEtlLogDao() {
		return etlLogDao;
	}

	/**
	 * etlLogDao
	 *
	 * @param   etlLogDao    the etlLogDao to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setEtlLogDao(EtlLogDAO etlLogDao) {
		this.etlLogDao = etlLogDao;
	}

	/**
	 * etlScriptDao
	 *
	 * @return  the etlScriptDao
	 * @since   CodingExample Ver 1.0
	 */
	
	public EtlScriptDAO getEtlScriptDao() {
		return etlScriptDao;
	}

	/**
	 * etlScriptDao
	 *
	 * @param   etlScriptDao    the etlScriptDao to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setEtlScriptDao(EtlScriptDAO etlScriptDao) {
		this.etlScriptDao = etlScriptDao;
	}

	/**
	 * etlTranslatorDao
	 *
	 * @return  the etlTranslatorDao
	 * @since   CodingExample Ver 1.0
	 */
	
	public EtlTranslatorDAO getEtlTranslatorDao() {
		return etlTranslatorDao;
	}

	/**
	 * etlTranslatorDao
	 *
	 * @param   etlTranslatorDao    the etlTranslatorDao to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setEtlTranslatorDao(EtlTranslatorDAO etlTranslatorDao) {
		this.etlTranslatorDao = etlTranslatorDao;
	}

	/**
	 * 
	
	 * @Method: createEtlJob 
	
	 * 创建Etl任务
	
	 * @param job
	
	 * @return void
	
	 * @throws
	 */
	public void createEtlJob(EtlJob job){
		SessionFactory sessionFactory = etlJobDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {		
			EtlJob newJob = job;
			newJob.setDdl(Hibernate.createClob(" "));
			newJob.setDml(Hibernate.createClob(" "));
			newJob.setLoadsql(Hibernate.createClob(" "));
			session.save(newJob);
			session.flush();
			session.refresh(newJob, LockMode.UPGRADE);
			SerializableClob clobDdl = (SerializableClob) newJob.getDdl();
			SerializableClob clobDml = (SerializableClob) newJob.getDml();
			SerializableClob clobLoadsql = (SerializableClob) newJob.getLoadsql();
			oracle.sql.CLOB clobDdlOracle = (oracle.sql.CLOB) clobDdl
					.getWrappedClob();
			oracle.sql.CLOB clobDmlOracle = (oracle.sql.CLOB) clobDml
					.getWrappedClob();
			oracle.sql.CLOB clobLoadsqlOracle = (oracle.sql.CLOB) clobLoadsql
					.getWrappedClob();

			Writer wtDdl = clobDdlOracle.getCharacterOutputStream();
			String textDdl = new String(job.getDdlStr());
			wtDdl.write(textDdl);
			wtDdl.close();

			Writer wtDml = clobDmlOracle.getCharacterOutputStream();
			String textDml = new String(job.getDmlStr());
			wtDml.write(textDml);
			wtDml.close();

			Writer wtLoadsql = clobLoadsqlOracle.getCharacterOutputStream();
			String textLoadsql = new String(job.getLoadsqlStr());
			wtLoadsql.write(textLoadsql);
			wtLoadsql.close();

			session.save(newJob);
			session.flush();
			tx.commit();
			session.close();
		} catch (Exception ex) {
			System.out.println(ex);
			if (session.isOpen())
				session.close();
		}
		
		
	}
	
	/**
	 * 
	
	 * @Method: updateEtlJob 
	
	 * 更新Etl任务
	
	 * @param job
	
	 * @return void
	
	 * @throws
	 */
	public void updateEtlJob(EtlJob job){
		SessionFactory sessionFactory = etlJobDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {		
			EtlJob newJob = job;
			newJob.setDdl(Hibernate.createClob(" "));
			newJob.setDml(Hibernate.createClob(" "));
			newJob.setLoadsql(Hibernate.createClob(" "));
			session.saveOrUpdate(newJob);
			session.flush();
			session.refresh(newJob, LockMode.UPGRADE);
			SerializableClob clobDdl = (SerializableClob) newJob.getDdl();
			SerializableClob clobDml = (SerializableClob) newJob.getDml();
			SerializableClob clobLoadsql = (SerializableClob) newJob.getLoadsql();
			oracle.sql.CLOB clobDdlOracle = (oracle.sql.CLOB) clobDdl
					.getWrappedClob();
			oracle.sql.CLOB clobDmlOracle = (oracle.sql.CLOB) clobDml
					.getWrappedClob();
			oracle.sql.CLOB clobLoadsqlOracle = (oracle.sql.CLOB) clobLoadsql
					.getWrappedClob();

			Writer wtDdl = clobDdlOracle.getCharacterOutputStream();
			String textDdl = new String(job.getDdlStr());
			wtDdl.write(textDdl);
			wtDdl.close();

			Writer wtDml = clobDmlOracle.getCharacterOutputStream();
			String textDml = new String(job.getDmlStr());
			wtDml.write(textDml);
			wtDml.close();

			Writer wtLoadsql = clobLoadsqlOracle.getCharacterOutputStream();
			String textLoadsql = new String(job.getLoadsqlStr());
			wtLoadsql.write(textLoadsql);
			wtLoadsql.close();

			session.saveOrUpdate(newJob);
			session.flush();
			tx.commit();
			session.close();
		} catch (Exception ex) {
			System.out.println(ex);
			if (session.isOpen())
				session.close();
		}
	}
	
	/**
	 * 
	
	 * @Method: createEtlLog 
	
	 * 创建Etl日志
	
	 * @param log
	
	 * @return void
	
	 * @throws
	 */
	public void createEtlLog(EtlLog log){
		SessionFactory sessionFactory = etlLogDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {		
			EtlLog newLog = log;
			newLog.setLogddl(Hibernate.createClob(" "));
			session.save(newLog);
			session.flush();
			session.refresh(newLog, LockMode.UPGRADE);
			SerializableClob clobLoadsql = (SerializableClob) newLog.getLogddl();
			oracle.sql.CLOB clobLoadsqlOracle = (oracle.sql.CLOB) clobLoadsql
					.getWrappedClob();

			Writer wtLoadsql = clobLoadsqlOracle.getCharacterOutputStream();
			String textLoadsql = new String(log.getLogddlStr());
			wtLoadsql.write(textLoadsql);
			wtLoadsql.close();

			session.save(newLog);
			session.flush();
			tx.commit();
			session.close();
		} catch (Exception ex) {
			System.out.println(ex);
			if (session.isOpen())
				session.close();
		}
	}
	
	/**
	 * 
	
	 * @Method: updateEtlLog 
	
	 * 更新Etl日志
	
	 * @param log
	
	 * @return void
	
	 * @throws
	 */
	public void updateEtlLog(EtlLog log){
		SessionFactory sessionFactory = etlLogDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {		
			EtlLog newLog = log;
			newLog.setLogddl(Hibernate.createClob(" "));
			session.saveOrUpdate(newLog);
			session.flush();
			session.refresh(newLog, LockMode.UPGRADE);
			SerializableClob clobLoadsql = (SerializableClob) newLog.getLogddl();
			oracle.sql.CLOB clobLoadsqlOracle = (oracle.sql.CLOB) clobLoadsql
					.getWrappedClob();

			Writer wtLoadsql = clobLoadsqlOracle.getCharacterOutputStream();
			String textLoadsql = new String(log.getLogddlStr());
			wtLoadsql.write(textLoadsql);
			wtLoadsql.close();

			session.saveOrUpdate(newLog);
			session.flush();
			tx.commit();
			session.close();
		} catch (Exception ex) {
			System.out.println(ex);
			if (session.isOpen())
				session.close();
		}
	}
	
	/**
	 * 
	
	 * @Method: findAll 
	
	 * 查询所有的Etl日志信息
	
	 * @return
	
	 * @return List<EtlLog>
	
	 * @throws
	 */
	public List<EtlLog> findAll(){
		return etlLogDao.findAll();
	}
	
	/**
	 * 
	
	 * @Method: findEtlLogsOfUserName 
	
	 * 查询用户有效的Etl日志
	
	 * @param userName
	 * @return
	
	 * @return List<EtlLog>
	
	 * @throws
	 */
	public List<EtlLog> findEtlLogsOfUserName(String userName){
		String hql = " from EtlLog where isDelete=1 and userId='"+userName+"'";
		return etlLogDao.getHqlResult(hql);
	}
	
	/**
	 * 
	
	 * @Method: findEtlLogById 
	
	 * 取得Etl日志
	
	 * @param id
	 * @return
	
	 * @return EtlLog
	
	 * @throws
	 */
	public EtlLog findEtlLogById(String id){
		return etlLogDao.findById(id);
	}
	
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
	public List<EtlScript> findEtlScripts(String userName, String fileType, String importType){
		String hql = " from EtlScript where username='"+userName+"' and filetype='"+fileType+"' and importType='"+importType+"' and scriptname is not null";
		return etlScriptDao.getHqlResult(hql);
	}
	
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
	public List<EtlScript> findEtlScripts(String userName, String scriptname){
		String hql = " from EtlScript where username='"+userName+"' and scriptname='"+scriptname+"' ";
		return etlScriptDao.getHqlResult(hql);
	}
	
	/**
	 * 
	
	 * @Method: createEtlScript 
	
	 * 创建Etl导入脚本
	
	 * @param etlScript
	
	 * @return void
	
	 * @throws
	 */
	public void createEtlScript(EtlScript etlScript){
		etlScriptDao.save(etlScript);
	}
	
	/**
	 * 
	
	 * @Method: updateEtlScript 
	
	 * 更新Etl导入脚本
	
	 * @param etlScript
	
	 * @return void
	
	 * @throws
	 */
	public void updateEtlScript(EtlScript etlScript){
		etlScriptDao.attachDirty(etlScript);
	}
	
	/**
	 * 
	
	 * @Method: findEtlTranslators 
	
	 * 取得Etl导入映射关系
	
	 * @param scriptid
	 * @param tableids
	 * @return
	
	 * @return List<EtlTranslator>
	
	 * @throws
	 */
	public List<EtlTranslator> findEtlTranslators(String scriptid, String tableids){
		String hql = " from EtlTranslator where script.id='"+scriptid+"' and tableid in("+tableids+")";
		return etlTranslatorDao.getHqlResult(hql);
	}
	
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
	public List<EtlTranslator> findEtlTranslatorsOfTable(String scriptid, String tableid){
		String hql = " from EtlTranslator where script.id='"+scriptid+"' and tableid ='"+tableid+"'";
		return etlTranslatorDao.getHqlResult(hql);
	}
	
	/**
	 * 
	
	 * @Method: createEtlTranslator 
	
	 * 创建Etl导入映射关系
	
	 * @param etlTranslator
	
	 * @return void
	
	 * @throws
	 */
	public void createEtlTranslator(EtlTranslator etlTranslator){
		SessionFactory sessionFactory = etlJobDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {		
			EtlTranslator newTranslator = etlTranslator;
			newTranslator.setTranslator(Hibernate.createClob(" "));
			session.save(newTranslator);
			session.flush();
			session.refresh(newTranslator, LockMode.UPGRADE);
			SerializableClob clobTranslator = (SerializableClob) newTranslator.getTranslator();
			oracle.sql.CLOB clobTranslatorOracle = (oracle.sql.CLOB) clobTranslator
					.getWrappedClob();

			Writer wtTranslator = clobTranslatorOracle.getCharacterOutputStream();
			String textTranslator = new String(etlTranslator.getTranslatorStr());
			wtTranslator.write(textTranslator);
			wtTranslator.close();

			session.save(newTranslator);
			session.flush();
			tx.commit();
			session.close();
		} catch (Exception ex) {
			System.out.println(ex);
			if (session.isOpen())
				session.close();
		}
		
	}
	
	/**
	 * 
	
	 * @Method: updateEtlTranslator 
	
	 * 更新Etl导入映射关系
	
	 * @param etlTranslator
	
	 * @return void
	
	 * @throws
	 */
	public void updateEtlTranslator(EtlTranslator etlTranslator){
		SessionFactory sessionFactory = etlJobDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {		
			EtlTranslator newTranslator = etlTranslator;
			newTranslator.setTranslator(Hibernate.createClob(" "));
			session.saveOrUpdate(newTranslator);
			session.flush();
			session.refresh(newTranslator, LockMode.UPGRADE);
			SerializableClob clobTranslator = (SerializableClob) newTranslator.getTranslator();
			oracle.sql.CLOB clobTranslatorOracle = (oracle.sql.CLOB) clobTranslator
					.getWrappedClob();

			Writer wtTranslator = clobTranslatorOracle.getCharacterOutputStream();
			String textTranslator = new String(etlTranslator.getTranslatorStr());
			wtTranslator.write(textTranslator);
			wtTranslator.close();

			session.saveOrUpdate(newTranslator);
			session.flush();
			tx.commit();
			session.close();
		} catch (Exception ex) {
			System.out.println(ex);
			if (session.isOpen())
				session.close();
		}
	}
}

