/**
 * LoginBussiness.java
 * com.orient.login
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * Jul 6, 2012 		mengbin
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.login.business;

import com.orient.edm.init.IContextLoadRun;
import com.orient.login.util.Check;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.domain.user.UserLoginHistory;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.sysmodel.service.user.UserLoginHistoryService;
import com.orient.sysmodel.service.user.UserService;
import com.orient.utils.PasswordUtil;
import com.orient.utils.PathTools;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class LoginBusiness implements IContextLoadRun {

    private UserLoginHistoryService userLoginHistoryService;

    private UserService userService;

    private IRoleUtil roleEngine;

    /**
     * 多级反向代理下，获取客户端实际IP
     *
     * @param request HttpServletRequest
     * @return 客户端实际IP
     */
    public static String getRealIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                //获取本机的IP
                InetAddress ia = null;
                try {
                    ia = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = ia.getHostAddress();
            }
        }

        //通过多个代理情况的特殊处理
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }

        return ip;
    }

    /**
     * @param errorInfo :错误信息
     * @param username
     * @param password
     * @param IpAddress
     * @return boolean
     * @Method: login
     */
    public boolean login(StringBuffer errorInfo, String username, String password, String IpAddress) {

        //check license
//		String licensrPath = PathTools.getRootPath() + File.separator + "WEB-INF" + File.separator;
//		if(checkLicesne(licensrPath,IpAddress,errorInfo)==false)
//		{
//			return false;
//		}


        IRoleModel sysmodel = roleEngine.getRoleModel(false);
        User dbUser = (User) sysmodel.getUserByUserName(username);//userService.findByUserName(username);

        if ((User) sysmodel.getUserByUserName(username) == null || !((User) sysmodel.getUserByUserName(username)).getState().equalsIgnoreCase("1")) {
            errorInfo.append("User is not exist!");
            return false;
        }

        String dbPassword = ((User) sysmodel.getUserByUserName(username)).getPassword();
        String inputPassword = PasswordUtil.generatePassword(password);
        if (!dbPassword.equalsIgnoreCase(inputPassword)) {
            errorInfo.append("Password is not correct!");
            int failueTime = Integer.valueOf(dbUser.getLoginFailures());
            //设置登录错误次数
            dbUser.setLockState(String.valueOf(failueTime++));
            ((User) sysmodel.getUserByUserName(username)).setLoginFailures(String.valueOf(failueTime));
            userService.updateUser(dbUser);
            return false;
        }
        //设置登录成功历史
        UserLoginHistory loginHistory = new UserLoginHistory();
        //loginHistory.setDept(((User)sysmodel.getUserByUserName(username)).getDept());
        loginHistory.setOpMessage("登录成功");
        loginHistory.setUserName((sysmodel.getUserByUserName(username)).getUserName());
        loginHistory.setUserDisplayName((sysmodel.getUserByUserName(username)).getAllName());
        loginHistory.setUserIp(IpAddress);
        loginHistory.setOpType("1");            //登录
        Date loginTime = new Date();
        loginHistory.setLoginTime(loginTime);
        userLoginHistoryService.createLoginHistory(loginHistory);
        dbUser.setLockState(String.valueOf(0));
        ((User) sysmodel.getUserByUserName(username)).setLoginFailures(String.valueOf(0));
        userService.updateUser(dbUser);
        return true;
    }


    public boolean checkLicesne(String licensePath, String IPAddress, StringBuffer errorInfo) {
        Boolean retVal = false;
        try {
            retVal = Check.checkLicense(licensePath, IPAddress, 1, errorInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    /**
     * userLoginHistoryService
     *
     * @return the userLoginHistoryService
     * @since CodingExample Ver 1.0
     */
    public UserLoginHistoryService getUserLoginHistoryService() {
        return userLoginHistoryService;
    }

    /**
     * userLoginHistoryService
     *
     * @param userLoginHistoryService the userLoginHistoryService to set
     * @since CodingExample Ver 1.0
     */
    public void setUserLoginHistoryService(UserLoginHistoryService userLoginHistoryService) {
        this.userLoginHistoryService = userLoginHistoryService;
    }

    /**
     * userService
     *
     * @return the userService
     * @since CodingExample Ver 1.0
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * userService
     *
     * @param userService the userService to set
     * @since CodingExample Ver 1.0
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * roleEngine
     *
     * @return the roleEngine
     * @since CodingExample Ver 1.0
     */
    public IRoleUtil getRoleEngine() {
        return roleEngine;
    }

    /**
     * roleEngine
     *
     * @param roleEngine the roleEngine to set
     * @since CodingExample Ver 1.0
     */
    public void setRoleEngine(IRoleUtil roleEngine) {
        this.roleEngine = roleEngine;
    }

    @Override
    public boolean modelLoadRun(WebApplicationContext contextLoad) {
        String licensePath = PathTools.getRootPath() + "/WEB-INF/";
        StringBuffer errorInfo = new StringBuffer();
        boolean bSuc = this.checkLicesne(licensePath, "127.0.0.1", errorInfo);
        if (bSuc == false) {
            System.out.print(errorInfo.toString());
        }
        return bSuc;
    }
}

