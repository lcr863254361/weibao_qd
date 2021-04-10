package com.orient.sysmodel.domain.role;
// default package



/**
 * AbstractRoleArithId entity provides the base persistence definition of the RoleArithId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRoleArithId extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String roleId;
     private String arithId;


    // Constructors

    /** default constructor */
    public AbstractRoleArithId() {
    }

    
    /** full constructor */
    public AbstractRoleArithId(String roleId, String arithId) {
        this.roleId = roleId;
        this.arithId = arithId;
    }

   
    // Property accessors

    public String getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getArithId() {
        return this.arithId;
    }
    
    public void setArithId(String arithId) {
        this.arithId = arithId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AbstractRoleArithId) ) return false;
		 AbstractRoleArithId castOther = ( AbstractRoleArithId ) other; 
         
		 return ( (this.getRoleId()==castOther.getRoleId()) || ( this.getRoleId()!=null && castOther.getRoleId()!=null && this.getRoleId().equals(castOther.getRoleId()) ) )
 && ( (this.getArithId()==castOther.getArithId()) || ( this.getArithId()!=null && castOther.getArithId()!=null && this.getArithId().equals(castOther.getArithId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoleId() == null ? 0 : this.getRoleId().hashCode() );
         result = 37 * result + ( getArithId() == null ? 0 : this.getArithId().hashCode() );
         return result;
   }   





}