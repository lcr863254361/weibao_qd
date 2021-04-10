package com.orient.sysmodel.domain.workflow;
// default package



/**
 * DataRightId entity. @author MyEclipse Persistence Tools
 */
public class DataRightId extends AbstractDataRightId implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public DataRightId() {
    }

	/** minimal constructor */
    public DataRightId(String flowId) {
        super(flowId);        
    }
    
    /** full constructor */
    public DataRightId(String flowId, String tableId) {
        super(flowId, tableId);        
    }
   
}
