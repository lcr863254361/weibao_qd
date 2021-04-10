package com.orient.sysmodel.domain.user;

// default package



/**
 * AbstractUserDept entity provides the base persistence definition of the UserDept entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserDept extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private UserDeptId id;

     private User user;
     private Department dept;

    // Constructors

    /** default constructor */
    public AbstractUserDept() {
    }

    
    /** full constructor */
    public AbstractUserDept(UserDeptId id) {
        this.id = id;
    }

   
    // Property accessors

    public UserDeptId getId() {
        return this.id;
    }
    
    public void setId(UserDeptId id) {
        this.id = id;
    }


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Department getDept() {
		return dept;
	}


	public void setDept(Department dept) {
		this.dept = dept;
	}
   








}