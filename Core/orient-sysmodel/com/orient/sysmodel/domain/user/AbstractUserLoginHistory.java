package com.orient.sysmodel.domain.user;

import com.orient.sysmodel.domain.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * AbstractUserLoginHistory entity provides the base persistence definition of the UserLoginHistory entity.
 */
public abstract class AbstractUserLoginHistory extends BaseBean implements Serializable {

    /*
     * id
     */
    private String id;
    /*
     * 用户登录名
     */
    private String userName;
    /*
     * 用户显示名
     */
    private String userDisplayName;
    /*
     * 用户ip
     */
    private String userIp;
    /*
     * 登陆时间
     */
    private Date loginTime;
    /*
     * 操作类别
     */
    private String opType;
    /*
     * 操作信息
     */
    private String opMessage;
    /*
     * 部门名称
     */
    private String userDeptName;
    /*
     * 注销时间
     */
    private Date logoutTime;
    /*
     * 部门ID
     */
    private String userDeptid;
    /*
     * 部门
     */
    private Department dept;

    // private String OP_TIME = ""; /*登陆次数*/
    //private String LOGIN_TIMES = ""; /*登陆次数*/
    private String START_TIME = ""; /*查询起始时间*/
    private String END_TIME = "";   /*查询结束时间*/
    //private String ROWID = "";   /*数据库行号*/
    // Constructors

    /**
     * default constructor
     */
    public AbstractUserLoginHistory() {
    }

    /**
     * minimal constructor
     */
    public AbstractUserLoginHistory(String userName, String userDisplayName, String userIp, Date loginTime, String opType, String opMessage) {
        this.userName = userName;
        this.userDisplayName = userDisplayName;
        this.userIp = userIp;
        this.loginTime = loginTime;
        this.opType = opType;
        this.opMessage = opMessage;
    }

    /**
     * full constructor
     */
    public AbstractUserLoginHistory(String userName, String userDisplayName, String userIp, Date loginTime, String opType, String opMessage, String userDeptName, Date logoutTime, String userDeptid) {
        this.userName = userName;
        this.userDisplayName = userDisplayName;
        this.userIp = userIp;
        this.loginTime = loginTime;
        this.opType = opType;
        this.opMessage = opMessage;
        this.userDeptName = userDeptName;
        this.logoutTime = logoutTime;
        this.userDeptid = userDeptid;
    }


    // Property accessors

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getUserIp() {
        return this.userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public Date getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getOpType() {
        return this.opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getOpMessage() {
        return this.opMessage;
    }

    public void setOpMessage(String opMessage) {
        this.opMessage = opMessage;
    }

    public String getUserDeptName() {
        return userDeptName;
    }

    public void setUserDeptName(String userDeptName) {
        this.userDeptName = userDeptName;
    }

    public Date getLogoutTime() {
        return this.logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getUserDeptid() {
        return this.userDeptid;
    }

    public void setUserDeptid(String userDeptid) {
        this.userDeptid = userDeptid;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public String getSTART_TIME() {
        return START_TIME;
    }

    public void setSTART_TIME(String sTARTTIME) {
        START_TIME = sTARTTIME;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(String eNDTIME) {
        END_TIME = eNDTIME;
    }


}