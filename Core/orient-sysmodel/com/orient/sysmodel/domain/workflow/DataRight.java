package com.orient.sysmodel.domain.workflow;
// default package



/**
 * DataRight entity. @author MyEclipse Persistence Tools
 */
public class DataRight extends AbstractDataRight implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public DataRight() {
    }

    
    /** full constructor */
    public DataRight(DataRightId id) {
        super(id);        
    }
    
    public DataRight(DataRightId id, String right) {
        super(id, right);        
    }
   
}
