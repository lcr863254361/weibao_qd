package com.orient.sysmodel.domain.file;

/**
 * CwmFolderId entity. @author MyEclipse Persistence Tools
 */
public class CwmFolder extends AbstractCwmFolder implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public CwmFolder() {
	}

	/** full constructor */
	public CwmFolder(String id, String delFlag, String addFlag,
			String editFlag, String cwmFolderId, String cwmTablesId,
			String recordId, String folderName) {
		super(id, delFlag, addFlag, editFlag, cwmFolderId, cwmTablesId,
				recordId, folderName);
	}

}
