/*
 * Title: AbstractTbomDir.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:57:00 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class AbstractTbomDir.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public abstract class AbstractTbomDir extends com.orient.sysmodel.domain.BaseBean implements Serializable {

	/** The id. */
	private String id;

	/** The name. */
	private String name;//TBOM树的名称

	/** The schemaid. */
	private String schemaid;//TBOM树所属的数据模型的ID

	/** The is lock. */
	private Long isLock;//TBOM树上锁标记，0表示未上锁，1表示上锁

	/** The userid. */
	private String userid;//上锁解锁的用户名称(登录名)

	/** The modified time. */
	private Date modifiedTime;//TBOM树的修改时间

	/** The lock modified time. */
	private Date lockModifiedTime;//TBOM树的上锁解锁修改时间

	/** The isdelete. */
	private Long isdelete;//TBOM树是否删除，0表示删除，1表示未删除
	/** The type */
	private Long type;//Tbom类型(0或者空:数据查看,1:文件查看

	//add zhy 2012-2-6 设置TBOM树在Web端的显示顺序
	private Long order_sign;//TBOM树在web端的排序字段
	
	/**
     * Tbom角色功能信息
     */
    private Set roleFunctionTboms = new HashSet(0);
	
    /**
     * 角色Tbom树权限信息
     */
    private Set roleTboms = new HashSet(0);
	
	public Long getOrder_sign() {
		return order_sign;
	}

	public void setOrder_sign(Long orderSign) {
		order_sign = orderSign;
	}
	//end
	/**
	 * Instantiates a new abstract tbom dir.
	 */
	public AbstractTbomDir() {
		super();
	}

	/**
	 * Instantiates a new abstract tbom dir.
	 * 
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 * @param schemaid
	 *            the schemaid
	 * @param isLock
	 *            the is lock
	 * @param userid
	 *            the userid
	 * @param modifiedTime
	 *            the modified time
	 * @param lockModifiedTime
	 *            the lock modified time
	 * @param isdelete
	 *            the isdelete
	 * @param type
	 *            the type
	 */
	public AbstractTbomDir(String id, String name, String schemaid,
			Long isLock, String userid, Date modifiedTime,
			Date lockModifiedTime, Long isdelete, Long type, Long order_sign) {
		super();
		this.id = id;
		this.name = name;
		this.schemaid = schemaid;
		this.isLock = isLock;
		this.userid = userid;
		this.modifiedTime = modifiedTime;
		this.lockModifiedTime = lockModifiedTime;
		this.isdelete = isdelete;
		this.type = type;
		this.order_sign = order_sign;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the schemaid.
	 * 
	 * @return the schemaid
	 */
	public String getSchemaid() {
		return schemaid;
	}

	/**
	 * Sets the schemaid.
	 * 
	 * @param schemaid
	 *            the new schemaid
	 */
	public void setSchemaid(String schemaid) {
		this.schemaid = schemaid;
	}

	/**
	 * Gets the checks if is lock.
	 * 
	 * @return the checks if is lock
	 */
	public Long getIsLock() {
		return isLock;
	}

	/**
	 * Sets the checks if is lock.
	 * 
	 * @param isLock
	 *            the new checks if is lock
	 */
	public void setIsLock(Long isLock) {
		this.isLock = isLock;
	}

	/**
	 * Gets the userid.
	 * 
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * Sets the userid.
	 * 
	 * @param userid
	 *            the new userid
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * Gets the modified time.
	 * 
	 * @return the modified time
	 */
	public Date getModifiedTime() {
		return modifiedTime;
	}

	/**
	 * Sets the modified time.
	 * 
	 * @param modifiedTime
	 *            the new modified time
	 */
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	/**
	 * Gets the lock modified time.
	 * 
	 * @return the lock modified time
	 */
	public Date getLockModifiedTime() {
		return lockModifiedTime;
	}

	/**
	 * Sets the lock modified time.
	 * 
	 * @param lockModifiedTime
	 *            the new lock modified time
	 */
	public void setLockModifiedTime(Date lockModifiedTime) {
		this.lockModifiedTime = lockModifiedTime;
	}

	/**
	 * Gets the isdelete.
	 * 
	 * @return the isdelete
	 */
	public Long getIsdelete() {
		return isdelete;
	}

	/**
	 * Sets the isdelete.
	 * 
	 * @param isdelete
	 *            the new isdelete
	 */
	public void setIsdelete(Long isdelete) {
		this.isdelete = isdelete;
	}

	/**
	 * roleFunctionTboms
	 *
	 * @return  the roleFunctionTboms
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getRoleFunctionTboms() {
		return roleFunctionTboms;
	}

	/**
	 * roleFunctionTboms
	 *
	 * @param   roleFunctionTboms    the roleFunctionTboms to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRoleFunctionTboms(Set roleFunctionTboms) {
		this.roleFunctionTboms = roleFunctionTboms;
	}

	/**
	 * roleTboms
	 *
	 * @return  the roleTboms
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getRoleTboms() {
		return roleTboms;
	}

	/**
	 * roleTboms
	 *
	 * @param   roleTboms    the roleTboms to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRoleTboms(Set roleTboms) {
		this.roleTboms = roleTboms;
	}

	

}
