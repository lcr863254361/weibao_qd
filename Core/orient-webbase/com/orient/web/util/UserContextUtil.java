package com.orient.web.util;

import com.orient.sysmodel.domain.user.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by Administrator on 2015/12/10 0010.
 */
public class UserContextUtil {

    public static User getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("currentUser");
        return user;
    }

    public static String getUserName() {

        String retVal = null;
        Subject subject = SecurityUtils.getSubject();
        User user_model = (User) subject.getSession().getAttribute("currentUser");
        if (null != user_model) {
            retVal = user_model.getUserName();
        }
        return retVal;
    }

    public static String getUserAllName() {
        String retVal = null;
        Subject subject = SecurityUtils.getSubject();
        User user_model = (User) subject.getSession().getAttribute("currentUser");
        if (null != user_model) {
            retVal = user_model.getAllName();
        }
        return retVal;
    }

    public static String getUserDepName() {
        String retVal = "未分组";
        Subject subject = SecurityUtils.getSubject();
        User user_model = (User) subject.getSession().getAttribute("currentUser");
        if (null != user_model) {
            retVal = null != user_model.getDept() ? user_model.getDept().getName() : "未分组";
        }
        return retVal;
    }

    public static String getUserDepId() {
        String retVal = "";
        Subject subject = SecurityUtils.getSubject();
        User user_model = (User) subject.getSession().getAttribute("currentUser");
        if (null != user_model) {
            retVal = null != user_model.getDept() ? user_model.getDept().getId() : "";
        }
        return retVal;
    }

    public static String getUserId() {
        String retVal = null;
        Subject subject = SecurityUtils.getSubject();
        User user_model = (User) subject.getSession().getAttribute("currentUser");
        if (null != user_model) {
            retVal = user_model.getId().toString();
        }
        return retVal;
    }

    public static String getUserIp() {
        String retVal = null;
        Subject subject = SecurityUtils.getSubject();
        String userIp = (String) subject.getSession().getAttribute("userIp");
        return userIp;
    }
}
