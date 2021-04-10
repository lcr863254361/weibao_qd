package com.orient.sysmodel.domain.workflow;
import com.orient.sysmodel.domain.user.User;

// default package



/**
 * JbpmTaskAssignHistory entity. @author MyEclipse Persistence Tools
 */
public class JbpmTaskAssignHistory extends AbstractJbpmTaskAssignHistory implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public JbpmTaskAssignHistory() {
    }

    
    /** full constructor */
    public JbpmTaskAssignHistory(String piid, String subPiid, String taskname, String username, User assignUser) {
        super(piid, subPiid, taskname, username, assignUser);        
    }
   
}
