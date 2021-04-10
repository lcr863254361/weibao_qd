package com.orient.sysmodel.domain.role;
// default package



/**
 * AbstractOverAllOperationsId entity provides the base persistence definition of the OverAllOperationsId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOverAllOperations extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String id;
     private String operationIds;//CWM_SYS_OPERATION表中ID的集合,用","隔开

     private Role role;
    // Constructors

    /** default constructor */
    public AbstractOverAllOperations() {
    }

    
    /** full constructor */
    public AbstractOverAllOperations(String id, String operationIds) {
        this.id = id;
        this.operationIds = operationIds;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getOperationIds() {
        return this.operationIds;
    }
    
    public void setOperationIds(String operationIds) {
        this.operationIds = operationIds;
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


}