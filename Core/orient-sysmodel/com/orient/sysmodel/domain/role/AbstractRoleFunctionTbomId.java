package com.orient.sysmodel.domain.role;
// default package



/**
 * AbstractRoleFunctionTbomId entity provides the base persistence definition of the RoleFunctionTbomId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRoleFunctionTbomId extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String roleId;
     private String functionId;
     private String tbomId;//CWM_TBOM


    // Constructors

    /** default constructor */
    public AbstractRoleFunctionTbomId() {
    }

    
    /** full constructor */
    public AbstractRoleFunctionTbomId(String roleId, String functionId, String tbomId) {
        this.roleId = roleId;
        this.functionId = functionId;
        this.tbomId = tbomId;
    }

   
    // Property accessors

    public String getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getFunctionId() {
        return this.functionId;
    }
    
    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getTbomId() {
        return this.tbomId;
    }
    
    public void setTbomId(String tbomId) {
        this.tbomId = tbomId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AbstractRoleFunctionTbomId) ) return false;
		 AbstractRoleFunctionTbomId castOther = ( AbstractRoleFunctionTbomId ) other; 
         
		 return ( (this.getRoleId()==castOther.getRoleId()) || ( this.getRoleId()!=null && castOther.getRoleId()!=null && this.getRoleId().equals(castOther.getRoleId()) ) )
 && ( (this.getFunctionId()==castOther.getFunctionId()) || ( this.getFunctionId()!=null && castOther.getFunctionId()!=null && this.getFunctionId().equals(castOther.getFunctionId()) ) )
 && ( (this.getTbomId()==castOther.getTbomId()) || ( this.getTbomId()!=null && castOther.getTbomId()!=null && this.getTbomId().equals(castOther.getTbomId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoleId() == null ? 0 : this.getRoleId().hashCode() );
         result = 37 * result + ( getFunctionId() == null ? 0 : this.getFunctionId().hashCode() );
         result = 37 * result + ( getTbomId() == null ? 0 : this.getTbomId().hashCode() );
         return result;
   }   





}