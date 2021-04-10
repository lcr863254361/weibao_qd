package com.orient.sysmodel.domain.role;
// default package



/**
 * RoleUserId entity. @author MyEclipse Persistence Tools
 */
public class RoleUserId extends AbstractRoleUserId implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public RoleUserId() {
    }

    
    /** full constructor */
    public RoleUserId(String roleId, String userId) {
        super(roleId, userId);        
    }
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return getRoleId()+"."+getUserId();
    }
    
    
   
}
