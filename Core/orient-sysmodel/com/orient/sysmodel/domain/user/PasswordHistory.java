package com.orient.sysmodel.domain.user;
// default package

import java.util.Date;


/**
 * PasswordHistory entity. @author MyEclipse Persistence Tools
 */
public class PasswordHistory extends AbstractPasswordHistory implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public PasswordHistory() {
    }

	/** minimal constructor */
    public PasswordHistory(User cwmSysUser) {
        super(cwmSysUser);        
    }
    
    /** full constructor */
    public PasswordHistory(User cwmSysUser, String password, Date passwordSetTime) {
        super(cwmSysUser, password, passwordSetTime);        
    }
   
}
