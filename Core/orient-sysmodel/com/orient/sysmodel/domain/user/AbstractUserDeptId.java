package com.orient.sysmodel.domain.user;
// default package



/**
 * AbstractUserDeptId entity provides the base persistence definition of the UserDeptId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserDeptId extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String userId;
     private String deptId;


    // Constructors

    /** default constructor */
    public AbstractUserDeptId() {
    }

    
    /** full constructor */
    public AbstractUserDeptId(String userId, String deptId) {
        this.userId = userId;
        this.deptId = deptId;
    }

   
    // Property accessors

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AbstractUserDeptId) ) return false;
		 AbstractUserDeptId castOther = ( AbstractUserDeptId ) other; 
         
		 return ( (this.getUserId()==castOther.getUserId()) || ( this.getUserId()!=null && castOther.getUserId()!=null && this.getUserId().equals(castOther.getUserId()) ) )
 && ( (this.getDeptId()==castOther.getDeptId()) || ( this.getDeptId()!=null && castOther.getDeptId()!=null && this.getDeptId().equals(castOther.getDeptId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserId() == null ? 0 : this.getUserId().hashCode() );
         result = 37 * result + ( getDeptId() == null ? 0 : this.getDeptId().hashCode() );
         return result;
   }   





}