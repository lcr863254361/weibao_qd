package com.orient.sysmodel.domain.role;
// default package



/**
 * RoleFunctionTbomId entity. @author MyEclipse Persistence Tools
 */
public class RoleFunctionTbomId extends AbstractRoleFunctionTbomId implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public RoleFunctionTbomId() {
    }

    
    /** full constructor */
    public RoleFunctionTbomId(String roleId, String functionId, String tbomId) {
        super(roleId, functionId, tbomId);        
    }
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return this.getRoleId()+"."+this.getFunctionId()+"."+this.getTbomId();
    }
   
}
