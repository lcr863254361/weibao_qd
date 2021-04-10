package com.orient.sysmodel.domain.file;

import java.util.Date;

import com.orient.sysmodel.domain.user.User;

public class AbstractCwmFileColumn extends
com.orient.sysmodel.domain.BaseBean implements java.io.Serializable{
	private String id;// 主键ID，自增
	private String fileid;// 文件id
	private String colname;// 源文件的显示名称
	private String filecolname;// hbase中存储的名称
	private String order;// 排列顺讯
	
	
	public AbstractCwmFileColumn()
	{
	}
	
	public AbstractCwmFileColumn(String id,String fileid, String colname, String filecolname, String order)
	{
		this.id  = id;
		this.fileid = fileid;
		this.colname = colname;
		this.filecolname = filecolname;
		this.order = order;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public String getColname() {
		return colname;
	}
	public void setColname(String colname) {
		this.colname = colname;
	}
	public String getFilecolname() {
		return filecolname;
	}
	public void setFilecolname(String filecolname) {
		this.filecolname = filecolname;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}

	
	
}
