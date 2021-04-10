/*
 * Title: TbomDir.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:57:00 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;

import java.util.Date;
import java.util.Iterator;

import com.orient.sysmodel.domain.role.RoleFunctionTbom;
import com.orient.sysmodel.domain.role.RoleFunctionTbomId;
import com.orient.sysmodel.operationinterface.ITbomDir;

/**
 * The Class TbomDir.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public class TbomDir extends AbstractTbomDir implements java.io.Serializable, ITbomDir {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new tbom dir.
	 */
	public TbomDir() {
		super();
	}

	/**
	 * Instantiates a new tbom dir.
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
	public TbomDir(String id, String name, String schemaid, Long isLock,
			String userid, Date modifiedTime, Date lockModifiedTime,
			Long isdelete, Long type, Long order_sign) {
		super(id, name, schemaid, isLock, userid, modifiedTime, lockModifiedTime,isdelete, type, order_sign);
	}
	
	@SuppressWarnings("unchecked")
	public RoleFunctionTbom findRoleFunTbom(RoleFunctionTbomId roleFunTbomId)
	{
		
		for(Iterator<RoleFunctionTbom> it = this.getRoleFunctionTboms().iterator(); it.hasNext();)
		{
			RoleFunctionTbom roleFunTbom = it.next();
			if(roleFunTbom.getId().toString().equals(roleFunTbomId.toString()))
				return roleFunTbom;
		}
		
		return null;
	}
	
	public RoleFunctionTbom deleteRoleFunTbom(RoleFunctionTbomId roleFunTbomId)
	{
		
		RoleFunctionTbom roleFunTbom = findRoleFunTbom(roleFunTbomId);
		if(roleFunTbom!=null)
			this.getRoleFunctionTboms().remove(roleFunTbom);
		
		return roleFunTbom;
	}

}
