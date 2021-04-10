package com.orient.sysmodel.domain.file;

// default package


/**
 * CwmFile entity. @author MyEclipse Persistence Tools
 */
public class CwmFileGroup extends AbstractCwmFileGroup implements java.io.Serializable {
	
	public CwmFileGroup() {
		
	}
	
	public CwmFileGroup(String id, String groupName, String groupType,
			Long isShow) {
		super(id, groupName, groupType, isShow);
	}
}
