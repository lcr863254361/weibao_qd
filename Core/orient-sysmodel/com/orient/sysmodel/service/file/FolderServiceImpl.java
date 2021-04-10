package com.orient.sysmodel.service.file;

import java.util.List;

import com.orient.sysmodel.domain.file.CwmFolder;
import com.orient.sysmodel.domain.file.CwmFolderDAO;

/** 文件夹处理 */
public class FolderServiceImpl implements FolderService {

	private CwmFolderDAO dao;

	/** 获取记录的根文件夹 */
	public CwmFolder getRootFolder(String tableId, String dataId) {
		
		List<CwmFolder> folders = dao.findByProperty(CwmFolderDAO.CWMFOLDERID, "-1");
		CwmFolder rootFolder = null;
		for(CwmFolder folder : folders) {
			
			if(folder.getCwmTablesId().equals(tableId) &&
					folder.getRecordId().equals(dataId)) {
				rootFolder = folder;
				break;
			}
		}
		return rootFolder;
	}
	
	/** 检查文件夹是否存在 */
	public boolean checkFolderName(String folderName, String pfodlerId) {
		
		List<CwmFolder> list = dao.findByProperty(CwmFolderDAO.CWMFOLDERID, pfodlerId);
		boolean flag = false;
		for(CwmFolder folder : list) {
			if(folder.getFolderName().equals(folderName)) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	
	/** 新增根文件夹信息 */
	public void createFolder(CwmFolder cwmFolder) {
		
		dao.save(cwmFolder);
	}
	
	/** 删除文件夹 */
	public void deleteFolder(String id) {
		
		CwmFolder folder = dao.findById(id);
		dao.delete(folder);
	}
	
	/** 更新文件夹信息 */
	public void updateFolder(String id, String folderName) {
		
		CwmFolder folder = dao.findById(id);
		folder.setFolderName(folderName);
		dao.attachDirty(folder);
	}
	
	/** 根据属性查找 */
	public List<CwmFolder> findByProperty(String propertyName, Object value) {
		
		return dao.findByProperty(propertyName, value);
	}
	
	/** 根据ID查找 */
	public CwmFolder findFolderById(String id) {
		
		return dao.findById(id);
	}
	
	
	public CwmFolderDAO getDao() {
		return dao;
	}

	public void setDao(CwmFolderDAO dao) {
		this.dao = dao;
	}
	
}
