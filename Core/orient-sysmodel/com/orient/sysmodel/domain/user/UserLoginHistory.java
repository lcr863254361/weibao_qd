package com.orient.sysmodel.domain.user;

import java.util.Date;

/**
 * UserLoginHistory entity.
 */
public class UserLoginHistory extends AbstractUserLoginHistory  {

    public UserLoginHistory() {
    }

    public UserLoginHistory(String userName, String userDispalyname, String userIp, Date loginTime, String opType, String opMessage) {
        super(userName, userDispalyname, userIp, loginTime, opType, opMessage);
    }

    public UserLoginHistory(String userName, String userDispalyname, String userIp, Date loginTime, String opType, String opMessage, String userDeptname, Date logoutTime, String userDeptid) {
        super(userName, userDispalyname, userIp, loginTime, opType, opMessage, userDeptname, logoutTime, userDeptid);
    }

}
