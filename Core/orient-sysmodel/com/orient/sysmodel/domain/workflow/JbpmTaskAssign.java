package com.orient.sysmodel.domain.workflow;
import com.orient.sysmodel.domain.user.User;

// default package



/**
 * JbpmTaskAssign entity. @author MyEclipse Persistence Tools
 */
public class JbpmTaskAssign extends AbstractJbpmTaskAssign implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public JbpmTaskAssign() {
    }

    
    /** full constructor */
    public JbpmTaskAssign(String piid, String subPiid, String taskname, String username, User assignUser) {
        super(piid, subPiid, taskname, username, assignUser);        
    }
   
}
