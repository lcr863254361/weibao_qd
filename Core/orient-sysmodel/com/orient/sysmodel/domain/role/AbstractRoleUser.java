package com.orient.sysmodel.domain.role;
import com.orient.sysmodel.domain.user.User;

// default package



/**
 * AbstractRoleUser entity provides the base persistence definition of the RoleUser entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRoleUser extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private RoleUserId id;

     private Role role;
     private User user;

    // Constructors

    /** default constructor */
    public AbstractRoleUser() {
    }

    
    /** full constructor */
    public AbstractRoleUser(RoleUserId id) {
        this.id = id;
    }

   
    // Property accessors

    public RoleUserId getId() {
        return this.id;
    }
    
    public void setId(RoleUserId id) {
        this.id = id;
    }


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
   








}