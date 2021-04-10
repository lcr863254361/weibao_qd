package com.orient.sysmodel.domain.workflow;
// default package



/**
 * AbstractDataRightId entity provides the base persistence definition of the DataRightId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractDataRightId extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String flowId;//流程ID
     private String tableId;//数据类ID
     

    // Constructors

    /** default constructor */
    public AbstractDataRightId() {
    }

	/** minimal constructor */
    public AbstractDataRightId(String flowId) {
        this.flowId = flowId;
    }
    
    /** full constructor */
    public AbstractDataRightId(String flowId, String tableId) {
        this.flowId = flowId;
        this.tableId = tableId;
    }

   
    // Property accessors

    public String getFlowId() {
        return this.flowId;
    }
    
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getTableId() {
        return this.tableId;
    }
    
    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AbstractDataRightId) ) return false;
		 AbstractDataRightId castOther = ( AbstractDataRightId ) other; 
         
		 return ( (this.getFlowId()==castOther.getFlowId()) || ( this.getFlowId()!=null && castOther.getFlowId()!=null && this.getFlowId().equals(castOther.getFlowId()) ) )
 && ( (this.getTableId()==castOther.getTableId()) || ( this.getTableId()!=null && castOther.getTableId()!=null && this.getTableId().equals(castOther.getTableId()) ) )
 ;
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getFlowId() == null ? 0 : this.getFlowId().hashCode() );
         result = 37 * result + ( getTableId() == null ? 0 : this.getTableId().hashCode() );
         return result;
   }   





}