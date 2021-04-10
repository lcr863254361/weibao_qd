/**
 * ReportServiceImpl.java
 * com.sysmodel.service.report
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-4-1 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.service.report;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.orient.sysmodel.domain.report.ReportItems;
import com.orient.sysmodel.domain.report.ReportItemsDAO;
import com.orient.sysmodel.domain.report.Reports;
import com.orient.sysmodel.domain.report.ReportsDAO;
import com.orient.utils.PathTools;

/**
 * ClassName:ReportServiceImpl
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-1		上午08:48:59
 *
 * @see 	 
 */
public class ReportServiceImpl implements ReportService {

	private ReportsDAO dao;
	private ReportItemsDAO reportItemsDao;

	/**
	 * dao
	 *
	 * @return  the dao
	 * @since   CodingExample Ver 1.0
	 */
	
	public ReportsDAO getDao() {
		return dao;
	}

	/**
	 * dao
	 *
	 * @param   dao    the dao to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setDao(ReportsDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * reportItemsDao
	 *
	 * @return  the reportItemsDao
	 * @since   CodingExample Ver 1.0
	 */
	
	public ReportItemsDAO getReportItemsDao() {
		return reportItemsDao;
	}

	/**
	 * reportItemsDao
	 *
	 * @param   reportItemsDao    the reportItemsDao to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setReportItemsDao(ReportItemsDAO reportItemsDao) {
		this.reportItemsDao = reportItemsDao;
	}

	/**
	 * 
	
	 * @Method: createReport 
	
	 * 创建报告模板
	
	 * @param report 
	
	 * @see com.orient.sysmodel.service.report.ReportService#createReport(com.orient.sysmodel.domain.report.Reports)
	 */
	public void createReport(Reports report){
		dao.save(report);
	}
	
	/**
	 * 
	
	 * @Method: updateReport 
	
	 * 更新报告模板
	
	 * @param report 
	
	 * @see com.orient.sysmodel.service.report.ReportService#updateReport(com.orient.sysmodel.domain.report.Reports)
	 */
	public void updateReport(Reports report){
		dao.attachDirty(report);
	}
	
	/**
	 * 
	
	 * @Method: deleteReport 
	
	 * 删除报告模板
	
	 * @param reportId 
	
	 * @see com.orient.sysmodel.service.report.ReportService#deleteReport(java.lang.String)
	 */
	public void deleteReport(String reportId){
		Reports report = this.findReportById(reportId);
		String filePath = report.getFilepath();
		
		//级联删除ReportsItem表中的数据
		for(Iterator it = report.getReportItems().iterator(); it.hasNext();){
			ReportItems reportItem = (ReportItems)it.next();
			reportItemsDao.delete(reportItem);
		}
		
		dao.delete(report);
		
		//级联删除物理目录下的文件
	    /*filePath=CommonTools.getRootPath()+"/file/report/"+filePath;
		File file=new File(filePath);
		if(file.exists()){
			file.delete();
		}*/
		
	}
	
	/**
	 * 
	
	 * @Method: findReportById 
	
	 * 取得报告
	
	 * @param reportId
	 * @return 
	
	 * @see com.orient.sysmodel.service.report.ReportService#findReportById(java.lang.String)
	 */
	public Reports findReportById(String reportId){
		return dao.findById(reportId);
	}
	
	/**
	 * 
	
	 * @Method: findReportByName 
	
	 * 根据报告模板名取得报告
	
	 * @param reportName
	 * @return 
	
	 * @see com.orient.sysmodel.service.report.ReportService#findReportByName(java.lang.String)
	 */
	public List<Reports> findReportByName(String reportName){
		return dao.findByName(reportName);
	}
	
	/**
	 * 
	
	 * @Method: findReportsOfSchema 
	
	 * 取得业务库的报告模板
	
	 * @param schemaId
	 * @return 
	
	 * @see com.orient.sysmodel.service.report.ReportService#findReportsOfSchema(java.lang.String)
	 */
	public List<Reports> findReportsOfSchema(String schemaId){
		return dao.findByProperty("schema.id", schemaId);
	}
	
	/**
	 * 
	
	 * @Method: findReportsOfDataEntry 
	
	 * 取得指定表作为数据入口的报告模板
	
	 * @param tableId
	 * @return 
	
	 * @see com.orient.sysmodel.service.report.ReportService#findReportsOfDataEntry(java.lang.String)
	 */
	public List<Reports> findReportsOfDataEntry(String tableId){
		return dao.findByDataEntry(tableId);
	}
	
	/**
	 * 
	
	 * @Method: findSubReports 
	
	 * 取得指定模板的子模板
	
	 * @param reportId
	 * @return 
	
	 * @see com.orient.sysmodel.service.report.ReportService#findSubReports(java.lang.String)
	 */
	public List<Reports> findSubReports(String reportId){
		Reports report = this.findReportById(reportId);
		
		//子模板的数据源和父模板的数据源一致,并且子模板的数据入口是父模板数据入口的子表
		StringBuffer hql = new StringBuffer();
		hql.append(" from Reports ");
		hql.append(" where ");
		hql.append(" dataEntry in (select refTable.id  from RelationColumns  where table.id='").append(report.getDataEntry()).append("' ");
		hql.append(" and IS_FK=").append(0);
		hql.append(" and  refTable.id in (").append(report.getTableId()).append(")) ");
		hql.append(" and tableId='"+report.getTableId()+"' and viewsId='"+report.getViewsId()+"'");
		
		return dao.getHqlResult(hql.toString());		
	}
	
	/**
	 * 
	
	 * @Method: findAllReports 
	
	 * 取得所有的报告模板
	
	 * @return 
	
	 * @see com.orient.sysmodel.service.report.ReportService#findAllReports()
	 */
	public List<Reports> findAllReports(){
		return dao.findAll();
	}

	/* (non-Javadoc)
	 * @see com.orient.sysmodel.service.report.ReportService#DeleteReportsBySchemaId(java.lang.String)
	 */
	@Override
	public void DeleteReportsBySchemaId(Object schemaId) {
		List<Reports> fileList = dao.findBySchemaId(schemaId);
		for(Reports report:fileList){
			String fp = report.getFilepath();
			String filePath = PathTools.getRootPath()+"/file/report/"+fp;
			File file=new File(filePath);
			if(file.exists()){
				file.delete();
			}
			dao.delete(report);
		}
		
	}
}

