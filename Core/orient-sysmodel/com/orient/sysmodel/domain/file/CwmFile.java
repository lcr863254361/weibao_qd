package com.orient.sysmodel.domain.file;

// default package

import java.util.Date;

import com.orient.sysmodel.domain.user.User;

/**
 * CwmFile entity. @author MyEclipse Persistence Tools
 */
public class CwmFile extends AbstractCwmFile implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public CwmFile() {
	}

	/** full constructor */
	public CwmFile(String schemaid, String tableid, String filename,
			String filedescription, String filetype, String filelocation,
			Long filesize, String parseRule, User uploadUser, Date uploadDate,
			User deleteUser, Date deleteDate, String dataid, String finalname,
			String edition, Long isValid, String filesecrecy,
			String uploadUserMac, String uploadStatus, String fileFolder,
			Long isFoldFile, String folderSerial, Long isWholeSearch,
			Long isDataFile, String cwmFolderId, String converState) {
		super(schemaid, tableid, filename, filedescription, filetype,
				filelocation, filesize, parseRule, uploadUser, uploadDate,
				deleteUser, deleteDate, dataid, finalname, edition, isValid,
				filesecrecy, uploadUserMac, uploadStatus, fileFolder,
				isFoldFile, folderSerial, isWholeSearch, isDataFile,
				cwmFolderId, converState);
	}

}
