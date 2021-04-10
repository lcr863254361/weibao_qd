package com.orient.sysmodel.service.file;

import java.util.List;

import com.orient.sysmodel.domain.file.CwmFileGroup;
import com.orient.sysmodel.domain.file.CwmFileGroupDAO;

public class FileGroupServiceImpl implements FileGroupService{
	private CwmFileGroupDAO dao;
	
	public void createFileGroup(CwmFileGroup fileGroup) {
		dao.save(fileGroup);
	}

	public void deleteFileGroup(String fileGroupId) {
		CwmFileGroup fileGroup = dao.findById(fileGroupId);
		dao.delete(fileGroup);
	}

	public void updateFileGroup(CwmFileGroup fileGroup) {
		dao.merge(fileGroup);
	}

	public CwmFileGroup getFileGroupById(String id) {
		return dao.findById(id);
	}

	public List<CwmFileGroup> getFileGroups() {
		return dao.findAll();
	}

	public CwmFileGroupDAO getDao() {
		return dao;
	}

	public void setDao(CwmFileGroupDAO dao) {
		this.dao = dao;
	}

	public List<String> getSysFileTypes() {
		return dao.getFileTypes();
	}
}
