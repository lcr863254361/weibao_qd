package com.orient.sysmodel.domain.workflow;

 



/**
 * JbpmConfigUserId entity. @author MyEclipse Persistence Tools
 */

public class JbpmConfigUserId  implements java.io.Serializable {


    // Fields    

     private String userid;
     private String configUserid;
     private String type;
     private String executionid;
     private String remark;


    // Constructors

    /** default constructor */
    public JbpmConfigUserId() {
    }

    
    /** full constructor */
    public JbpmConfigUserId(String userid, String configUserid, String type, String executionid, String remark) {
        this.userid = userid;
        this.configUserid = configUserid;
        this.type = type;
        this.executionid = executionid;
        this.remark = remark;
    }

   
    // Property accessors

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getConfigUserid() {
        return this.configUserid;
    }
    
    public void setConfigUserid(String configUserid) {
        this.configUserid = configUserid;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getExecutionid() {
        return this.executionid;
    }
    
    public void setExecutionid(String executionid) {
        this.executionid = executionid;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof JbpmConfigUserId) ) return false;
		 JbpmConfigUserId castOther = ( JbpmConfigUserId ) other; 
         
		 return ( (this.getUserid()==castOther.getUserid()) || ( this.getUserid()!=null && castOther.getUserid()!=null && this.getUserid().equals(castOther.getUserid()) ) )
 && ( (this.getConfigUserid()==castOther.getConfigUserid()) || ( this.getConfigUserid()!=null && castOther.getConfigUserid()!=null && this.getConfigUserid().equals(castOther.getConfigUserid()) ) )
 && ( (this.getType()==castOther.getType()) || ( this.getType()!=null && castOther.getType()!=null && this.getType().equals(castOther.getType()) ) )
 && ( (this.getExecutionid()==castOther.getExecutionid()) || ( this.getExecutionid()!=null && castOther.getExecutionid()!=null && this.getExecutionid().equals(castOther.getExecutionid()) ) )
 && ( (this.getRemark()==castOther.getRemark()) || ( this.getRemark()!=null && castOther.getRemark()!=null && this.getRemark().equals(castOther.getRemark()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserid() == null ? 0 : this.getUserid().hashCode() );
         result = 37 * result + ( getConfigUserid() == null ? 0 : this.getConfigUserid().hashCode() );
         result = 37 * result + ( getType() == null ? 0 : this.getType().hashCode() );
         result = 37 * result + ( getExecutionid() == null ? 0 : this.getExecutionid().hashCode() );
         result = 37 * result + ( getRemark() == null ? 0 : this.getRemark().hashCode() );
         return result;
   }   





}