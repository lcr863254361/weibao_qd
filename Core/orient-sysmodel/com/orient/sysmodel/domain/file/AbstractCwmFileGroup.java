package com.orient.sysmodel.domain.file;

// default package

import java.util.Date;

import com.orient.sysmodel.domain.user.User;

/**
 * AbstractCwmFile entity provides the base persistence definition of the
 * CwmFile entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractCwmFileGroup extends
		com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {

	// Fields

	private String id;// 主键ID，自增
	private String groupName;// 分组名称
	private String groupType;// 分组类型
	private Long isShow;
	
	public AbstractCwmFileGroup() {
		
	}
	
	public AbstractCwmFileGroup(String id, String groupName, String groupType,
			Long isShow) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.groupType = groupType;
		this.isShow = isShow;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public Long getIsShow() {
		return isShow;
	}

	public void setIsShow(Long isShow) {
		this.isShow = isShow;
	}
}