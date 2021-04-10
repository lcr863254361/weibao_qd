package com.orient.sysmodel.domain.user;

import java.util.Iterator;

import com.orient.sysmodel.operationinterface.IDepartment;
// default package



/**
 * Department entity. @author MyEclipse Persistence Tools
 */
public class Department extends AbstractDepartment implements java.io.Serializable, IDepartment {

    // Constructors

    /** default constructor */
    public Department() {
    }

	/** minimal constructor */
    public Department(String id, String name) {
        super(id, name);        
    }
    
    /** full constructor */
    public Department(String id, String name, String function, String notes, String addFlg, String delFlg, String editFlg, Long order) {
        super(id, name, function, notes, addFlg, delFlg, editFlg, order);
    }
    
    @SuppressWarnings("unchecked")
	public User deleteUser(User user)
    {
    	for(Iterator<User> it = getUsers().iterator(); it.hasNext();)
    	{
    		User tUser = it.next();
    		if(tUser.getId().equals(user.getId()))
    		{
    			it.remove();
    			return tUser;
    		}
    	}
    	
    	return null;
    }
}
