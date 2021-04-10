package com.orient.sysmodel.domain.role;
// default package



/**
 * AbstractRoleUserId entity provides the base persistence definition of the RoleUserId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRoleUserId extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String roleId;
     private String userId;


    // Constructors

    /** default constructor */
    public AbstractRoleUserId() {
    }

    
    /** full constructor */
    public AbstractRoleUserId(String roleId, String userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

   
    // Property accessors

    public String getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AbstractRoleUserId) ) return false;
		 AbstractRoleUserId castOther = ( AbstractRoleUserId ) other; 
         
		 return ( (this.getRoleId()==castOther.getRoleId()) || ( this.getRoleId()!=null && castOther.getRoleId()!=null && this.getRoleId().equals(castOther.getRoleId()) ) )
 && ( (this.getUserId()==castOther.getUserId()) || ( this.getUserId()!=null && castOther.getUserId()!=null && this.getUserId().equals(castOther.getUserId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoleId() == null ? 0 : this.getRoleId().hashCode() );
         result = 37 * result + ( getUserId() == null ? 0 : this.getUserId().hashCode() );
         return result;
   }   





}