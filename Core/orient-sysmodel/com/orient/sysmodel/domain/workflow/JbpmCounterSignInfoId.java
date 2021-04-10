package com.orient.sysmodel.domain.workflow;




/**
 * JbpmCounterSignInfoId entity. @author MyEclipse Persistence Tools
 */

public class JbpmCounterSignInfoId  implements java.io.Serializable {


    // Fields    

     private String executionId;
     private String taskname;
     private String usercount;
     private String strategy;
     private String strategyValue;
     private String remark;


    // Constructors

    /** default constructor */
    public JbpmCounterSignInfoId() {
    }

    
    /** full constructor */
    public JbpmCounterSignInfoId(String executionId, String taskname, String usercount, String strategy, String strategyValue, String remark) {
        this.executionId = executionId;
        this.taskname = taskname;
        this.usercount = usercount;
        this.strategy = strategy;
        this.strategyValue = strategyValue;
        this.remark = remark;
    }

   
    // Property accessors

    public String getExecutionId() {
        return this.executionId;
    }
    
    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getTaskname() {
        return this.taskname;
    }
    
    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getUsercount() {
        return this.usercount;
    }
    
    public void setUsercount(String usercount) {
        this.usercount = usercount;
    }

    public String getStrategy() {
        return this.strategy;
    }
    
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getStrategyValue() {
        return this.strategyValue;
    }
    
    public void setStrategyValue(String strategyValue) {
        this.strategyValue = strategyValue;
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
		 if ( !(other instanceof JbpmCounterSignInfoId) ) return false;
		 JbpmCounterSignInfoId castOther = ( JbpmCounterSignInfoId ) other; 
         
		 return ( (this.getExecutionId()==castOther.getExecutionId()) || ( this.getExecutionId()!=null && castOther.getExecutionId()!=null && this.getExecutionId().equals(castOther.getExecutionId()) ) )
 && ( (this.getTaskname()==castOther.getTaskname()) || ( this.getTaskname()!=null && castOther.getTaskname()!=null && this.getTaskname().equals(castOther.getTaskname()) ) )
 && ( (this.getUsercount()==castOther.getUsercount()) || ( this.getUsercount()!=null && castOther.getUsercount()!=null && this.getUsercount().equals(castOther.getUsercount()) ) )
 && ( (this.getStrategy()==castOther.getStrategy()) || ( this.getStrategy()!=null && castOther.getStrategy()!=null && this.getStrategy().equals(castOther.getStrategy()) ) )
 && ( (this.getStrategyValue()==castOther.getStrategyValue()) || ( this.getStrategyValue()!=null && castOther.getStrategyValue()!=null && this.getStrategyValue().equals(castOther.getStrategyValue()) ) )
 && ( (this.getRemark()==castOther.getRemark()) || ( this.getRemark()!=null && castOther.getRemark()!=null && this.getRemark().equals(castOther.getRemark()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getExecutionId() == null ? 0 : this.getExecutionId().hashCode() );
         result = 37 * result + ( getTaskname() == null ? 0 : this.getTaskname().hashCode() );
         result = 37 * result + ( getUsercount() == null ? 0 : this.getUsercount().hashCode() );
         result = 37 * result + ( getStrategy() == null ? 0 : this.getStrategy().hashCode() );
         result = 37 * result + ( getStrategyValue() == null ? 0 : this.getStrategyValue().hashCode() );
         result = 37 * result + ( getRemark() == null ? 0 : this.getRemark().hashCode() );
         return result;
   }   





}