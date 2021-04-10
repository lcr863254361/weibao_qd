package com.orient.sysmodel.domain.role;
// default package



/**
 * AbstractRoleSchemaId entity provides the base persistence definition of the RoleSchemaId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRoleSchemaId extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String roleId;
     private String schemaId;


    // Constructors

    /** default constructor */
    public AbstractRoleSchemaId() {
    }

    
    /** full constructor */
    public AbstractRoleSchemaId(String roleId, String schemaId) {
        this.roleId = roleId;
        this.schemaId = schemaId;
    }

   
    // Property accessors

    public String getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSchemaId() {
        return this.schemaId;
    }
    
    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AbstractRoleSchemaId) ) return false;
		 AbstractRoleSchemaId castOther = ( AbstractRoleSchemaId ) other; 
         
		 return ( (this.getRoleId()==castOther.getRoleId()) || ( this.getRoleId()!=null && castOther.getRoleId()!=null && this.getRoleId().equals(castOther.getRoleId()) ) )
 && ( (this.getSchemaId()==castOther.getSchemaId()) || ( this.getSchemaId()!=null && castOther.getSchemaId()!=null && this.getSchemaId().equals(castOther.getSchemaId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoleId() == null ? 0 : this.getRoleId().hashCode() );
         result = 37 * result + ( getSchemaId() == null ? 0 : this.getSchemaId().hashCode() );
         return result;
   }   





}