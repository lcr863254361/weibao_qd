/**
 * FileService.java
 * com.orient.sysmodel.service.file
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-4-23 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.service.file;

import java.util.List;



import com.orient.sysmodel.domain.file.CwmFileGroup;

/**
 * ClassName:FileGroupService
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   chang xuekun
 * @version  
 * @since    Ver 1.1
 * @Date	 2014-1-9		上午8:32:39
 *
 * @see 	 
 */
public interface FileGroupService {

	/**
	 * 
	
	 * @Method: createFileGroup 
	
	 * 新增文件分组
	
	 * @param fileGroup
	
	 * @return void
	
	 * @throws
	 */
	public void createFileGroup(CwmFileGroup fileGroup);
	
	/**
	 * 
	
	 * @Method: deleteFileGroup 
	
	 * 删除文件分组
	
	 * @param fileGroup
	
	 * @return void
	
	 * @throws
	 */
	public void deleteFileGroup(String fileGroupId);
	
	/**
	 * 
	
	 * @Method: updateFileGroup 
	
	 * 更新文件分组
	
	 * @param fileGroup
	
	 * @return void
	
	 * @throws
	 */
	public void updateFileGroup(CwmFileGroup fileGroup);
	
	/**
	 * 
	
	 * @Method: getFileGroupById 
	
	 * 根据ID获取文件分组
	
	 * @param id
	
	 * @return void
	
	 * @throws
	 */
	public CwmFileGroup getFileGroupById(String id);
	
	/**
	 * 
	
	 * @Method: getFileGroups 
	
	 * 获取所有文件分组
	
	 * @param id
	
	 * @return void
	
	 * @throws
	 */
	public List<CwmFileGroup> getFileGroups();
	
	/**
	 * 在cwm_codetoname表中查询文件类型信息 
	 * @return
	 */
	public List<String> getSysFileTypes();
}

