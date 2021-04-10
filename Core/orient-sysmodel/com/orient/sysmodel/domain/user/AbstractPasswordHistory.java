package com.orient.sysmodel.domain.user;
// default package

import com.orient.sysmodel.domain.user.User;

import java.util.Date;


/**
 * AbstractPasswordHistory entity provides the base persistence definition of the PasswordHistory entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractPasswordHistory extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    
     /**
      * 密码历史记录ID
      */
     private Long id;
     
     /**
      * 用户
      */
     private User user;
     
     /**
      * 设置的密码
      */
     private String password;
     
     /**
      * 设置时间
      */
     private Date passwordSetTime;


    // Constructors

    /** default constructor */
    public AbstractPasswordHistory() {
    }

	/** minimal constructor */
    public AbstractPasswordHistory(User cwmSysUser) {
        this.user = cwmSysUser;
    }
    
    /** full constructor */
    public AbstractPasswordHistory(User cwmSysUser, String password, Date passwordSetTime) {
        this.user = cwmSysUser;
        this.password = password;
        this.passwordSetTime = passwordSetTime;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public Date getPasswordSetTime() {
        return this.passwordSetTime;
    }
    
    public void setPasswordSetTime(Date passwordSetTime) {
        this.passwordSetTime = passwordSetTime;
    }
   








}