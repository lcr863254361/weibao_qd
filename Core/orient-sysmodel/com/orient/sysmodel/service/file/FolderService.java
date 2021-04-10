package com.orient.sysmodel.service.file;

import java.util.List;

import com.orient.sysmodel.domain.file.CwmFolder;

/** 系统文件夹 */
public interface FolderService {

	public CwmFolder getRootFolder(String tableId, String dataId);
	
	public boolean checkFolderName(String folderName, String pfodlerId);
	
	public void createFolder(CwmFolder cwmFolder);
	
	public void deleteFolder(String id);
	
	public void updateFolder(String id, String folderName);
	
	public CwmFolder findFolderById(String id);
	
	public List<CwmFolder> findByProperty(String propertyName, Object value);
	
}
