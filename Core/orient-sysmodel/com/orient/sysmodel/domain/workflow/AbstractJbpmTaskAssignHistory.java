package com.orient.sysmodel.domain.workflow;
// default package


import com.orient.sysmodel.domain.user.User;


/**
 * AbstractJbpmTaskAssignHistory entity provides the base persistence definition of the JbpmTaskAssignHistory entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractJbpmTaskAssignHistory extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private Long id;
     private String piid;//主流程PID
     private String subPiid;//子流程PID
     private String taskname;//任务名称
     private String username;//任务执行者(用户登录名)
     
     //private String assignUserId;
     private User assignUser;//记录修改用户


    // Constructors

    /** default constructor */
    public AbstractJbpmTaskAssignHistory() {
    }

    
    /** full constructor */
    public AbstractJbpmTaskAssignHistory(String piid, String subPiid, String taskname, String username, User assignUser) {
        this.piid = piid;
        this.subPiid = subPiid;
        this.taskname = taskname;
        this.username = username;
        this.assignUser = assignUser;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getPiid() {
        return this.piid;
    }
    
    public void setPiid(String piid) {
        this.piid = piid;
    }

    public String getSubPiid() {
        return this.subPiid;
    }
    
    public void setSubPiid(String subPiid) {
        this.subPiid = subPiid;
    }

    public String getTaskname() {
        return this.taskname;
    }
    
    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }


	public User getAssignUser() {
		return assignUser;
	}


	public void setAssignUser(User assignUser) {
		this.assignUser = assignUser;
	}

    
   








}