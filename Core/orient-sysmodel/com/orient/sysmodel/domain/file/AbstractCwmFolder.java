package com.orient.sysmodel.domain.file;

import com.orient.sysmodel.domain.BaseBean;

/**
 * AbstractCwmFolderId entity provides the base persistence definition of the
 * CwmFolderId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractCwmFolder extends BaseBean implements java.io.Serializable {

	// Fields

	private String id;			// 文件夹ID
	private String delFlag;		// 是否删除  0 不能；1能
	private String addFlag;		// 是否能增加 0 不能；1能
	private String editFlag;	// 是否能编辑 0 不能；1 能
	private String cwmFolderId;	// 父文件夹ID
	private String cwmTablesId;	// 所属表ID
	private String recordId;	// 所属记录ID
	private String folderName;	// 文件夹名称

	// Constructors

	/** default constructor */
	public AbstractCwmFolder() {
	}

	/** full constructor */
	public AbstractCwmFolder(String id, String delFlag, String addFlag,
			String editFlag, String cwmFolderId, String cwmTablesId,
			String recordId, String folderName) {
		this.id = id;
		this.delFlag = delFlag;
		this.addFlag = addFlag;
		this.editFlag = editFlag;
		this.cwmFolderId = cwmFolderId;
		this.cwmTablesId = cwmTablesId;
		this.recordId = recordId;
		this.folderName = folderName;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getAddFlag() {
		return this.addFlag;
	}

	public void setAddFlag(String addFlag) {
		this.addFlag = addFlag;
	}

	public String getEditFlag() {
		return this.editFlag;
	}

	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}

	public String getCwmFolderId() {
		return this.cwmFolderId;
	}

	public void setCwmFolderId(String cwmFolderId) {
		this.cwmFolderId = cwmFolderId;
	}

	public String getCwmTablesId() {
		return this.cwmTablesId;
	}

	public void setCwmTablesId(String cwmTablesId) {
		this.cwmTablesId = cwmTablesId;
	}

	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getFolderName() {
		return this.folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

}