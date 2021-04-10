package com.orient.sysmodel.domain.role;
import com.orient.sysmodel.domain.arith.Arith;

// default package



/**
 * AbstractRoleArith entity provides the base persistence definition of the RoleArith entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRoleArith extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private RoleArithId id;

     private Role role;
     private Arith arith;

    // Constructors

    /** default constructor */
    public AbstractRoleArith() {
    }

    
    /** full constructor */
    public AbstractRoleArith(RoleArithId id) {
        this.id = id;
    }

   
    // Property accessors

    public RoleArithId getId() {
        return this.id;
    }
    
    public void setId(RoleArithId id) {
        this.id = id;
    }


	/**
	 * role
	 *
	 * @return  the role
	 * @since   CodingExample Ver 1.0
	 */
	
	public Role getRole() {
		return role;
	}


	/**
	 * role
	 *
	 * @param   role    the role to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRole(Role role) {
		this.role = role;
	}


	/**
	 * arith
	 *
	 * @return  the arith
	 * @since   CodingExample Ver 1.0
	 */
	
	public Arith getArith() {
		return arith;
	}


	/**
	 * arith
	 *
	 * @param   arith    the arith to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setArith(Arith arith) {
		this.arith = arith;
	}
   








}