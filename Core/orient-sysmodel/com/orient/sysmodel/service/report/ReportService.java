/**
 * ReportService.java
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

import java.util.List;

import com.orient.sysmodel.domain.report.Reports;

/**
 * ClassName:ReportService
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-1		上午08:47:43
 *
 * @see 	 
 */
public interface ReportService {

	/**
	 * 
	
	 * @Method: createReport 
	
	 * 创建报告模板
	
	 * @param report
	
	 * @return void
	
	 * @throws
	 */
	public void createReport(Reports report);
	
	/**
	 * 
	
	 * @Method: updateReport 
	
	 * 更新报告模板
	
	 * @param report
	
	 * @return void
	
	 * @throws
	 */
	public void updateReport(Reports report);
	
	/**
	 * 
	
	 * @Method: deleteReport 
	
	 * 删除报告模板
	
	 * @param reportId
	
	 * @return void
	
	 * @throws
	 */
	public void deleteReport(String reportId);//级联删除ReportsItem表中的数据和物理目录下的文件
	
	/**
	 * 
	
	 * @Method: findReportById 
	
	 * 取得报告
	
	 * @param reportId
	 * @return
	
	 * @return Reports
	
	 * @throws
	 */
	public Reports findReportById(String reportId);
	
	/**
	 * 
	
	 * @Method: findReportByName 
	
	 * 根据报告模板名取得报告
	
	 * @param reportName
	 * @return
	
	 * @return List<Reports>
	
	 * @throws
	 */
	public List<Reports> findReportByName(String reportName);
	
	/**
	 * 
	
	 * @Method: findReportsOfSchema 
	
	 * 取得业务库的报告模板
	
	 * @param schemaId
	 * @return
	
	 * @return List<Reports>
	
	 * @throws
	 */
	public List<Reports> findReportsOfSchema(String schemaId);
	
	/**
	 * 
	
	 * @Method: findReportsOfDataEntry 
	
	 * 取得指定表作为数据入口的报告模板
	
	 * @param tableId
	 * @return
	
	 * @return List<Reports>
	
	 * @throws
	 */
	public List<Reports> findReportsOfDataEntry(String tableId);
	
	/**
	 * 
	
	 * @Method: findSubReports 
	
	 * 取得指定模板的子模板
	
	 * @param reportId
	 * @return
	
	 * @return List<Reports>
	
	 * @throws
	 */
	public List<Reports> findSubReports(String reportId);
	
	/**
	 * 
	
	 * @Method: findAllReports 
	
	 * 取得所有的报告模板
	
	 * @return
	
	 * @return List<Reports>
	
	 * @throws
	 */
	public List<Reports> findAllReports();
	/**
	 * 
	 * @Method: DeleteReportsBySchemaId
	
	 * 删除该schema下的所有报表以及报表记录
	
	 * @param schemaId 
	 * @return
	
	 * @return boolean true:删除成功, false:删除失败
	
	 * @throws
	 */
	public void DeleteReportsBySchemaId(Object schemaId);//区分文件是否删除
}

