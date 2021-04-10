package com.orient.login.controller;

import com.orient.log.annotion.Action;
import com.orient.login.business.LoginBusiness;
import com.orient.sysmodel.domain.user.Department;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.sysmodel.service.mq.impl.MsgService;
import com.orient.sysmodel.service.user.UserService;
import com.orient.utils.CommonTools;
import com.orient.utils.JsonUtil;
import com.orient.utils.Log.LogThreadLocalHolder;
import com.orient.utils.StringUtil;
import com.orient.web.util.TDMParamter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2015/9/18 0018.
 */
@Controller
@Scope("prototype")
public class LoginController {
    @Autowired
    LoginBusiness loginService;

    @Autowired
    protected IRoleUtil roleEngine;

    @Autowired
    TDMParamter tdmParamter;

    @Resource(name = "UserService")
    UserService userService;

    @Autowired
    MsgService msgService;

    @Action(ownermodel = "系统登录管理", detail = "登录系统")
    @RequestMapping("doLogin")
    public ModelAndView doLogin(String userName, String password, HttpServletRequest request) {
        ModelAndView retVal = new ModelAndView();
        String viewName = "/ExtModel.jsp";

        /*   localhost:8080/OrientEDM/doLogin.rdm?username=?&password=?&flag=true
            数据分析客户端登录时，无需经过登录界面，直接进入系统
         */
        String flag = request.getParameter("flag");
        if ("true".equals(flag)) {
            Subject subject = SecurityUtils.getSubject();
            userName = request.getParameter("username");
            password = request.getParameter("password");
            //解密用户名和密码
            java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
            try {
                userName = new String(decoder.decode(userName),"UTF-8");
                password = new String(decoder.decode(password),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        Subject subject = SecurityUtils.getSubject();
        //avoid refresh after clear cache
        if ((StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) && !subject.isAuthenticated()) {
            viewName = "/index.jsp";
            subject.getSession().setAttribute("errorMsg", "用户名或者密码错误");
            LogThreadLocalHolder.putParamerter("success", false);
            LogThreadLocalHolder.putParamerter("userName", userName);
        } else {
            try {
                if (subject.isAuthenticated()) {
                    userName = (String) subject.getPrincipal();
                } else {
                    UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
                    token.setHost(loginService.getRealIP(request));
                    //token.setRememberMe(true);
                    subject.login(token);
                    //保存会话
                    IRoleModel sysmodel = roleEngine.getRoleModel(false);
                    User currentUser = (User) sysmodel.getUserByUserName(userName);
                    subject.getSession().setAttribute("currentUser", currentUser);
                    //保存當前用戶IP地址
                    subject.getSession().setAttribute("userIp", request.getRemoteAddr());
                }
            } catch (AuthenticationException e) {
                viewName = "/index.jsp";
                subject.getSession().setAttribute("errorMsg", "用户名或者密码错误");
                LogThreadLocalHolder.putParamerter("success", false);
                LogThreadLocalHolder.putParamerter("userName", userName);
                retVal.setViewName(viewName);
                return retVal;
            }
            //保存系统初始化信息
            User currentUser = (User) subject.getSession().getAttribute("currentUser");
            Department currentDept = currentUser.getDept();
            if (null != currentUser) {
                retVal.addObject("TDM_SERVER_CONFIG", JsonUtil.toJson(tdmParamter.getConfigMap()));
                retVal.addObject("date", CommonTools.getDateStr());
                retVal.addObject("week", CommonTools.getCurrentWeek());
                //用户信息
                retVal.addObject("userId", currentUser.getId());
                retVal.addObject("username", currentUser.getUserName());
                retVal.addObject("userAllName", currentUser.getAllName());
                if (currentDept != null) {
                    retVal.addObject("deptId", currentDept.getId());
                    retVal.addObject("deptName", currentDept.getName());
                }
                //用户消息
                int msgCnt = msgService.getMsgCntByUserId(Long.valueOf(currentUser.getId()), false);
                retVal.addObject("msgCnt", msgCnt);

                retVal.addObject("globalPageSize", TDMParamter.getPageSize());
            } else {
                viewName = "/index.jsp";
                subject.getSession().setAttribute("errorMsg", "用户名或者密码错误");
                LogThreadLocalHolder.putParamerter("success", false);
                LogThreadLocalHolder.putParamerter("userName", userName);
            }
        }
        retVal.setViewName(viewName);
        return retVal;
    }

    @RequestMapping("loginError")
    public String loginError() {
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("errorMsg", "用户名或者密码错误");
        return "/index.jsp";
    }

    @RequestMapping("doLogout")
    public ModelAndView doLogout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        ModelAndView retVal = new ModelAndView();
        String viewName = "/index.jsp";
        retVal.setViewName(viewName);
        return retVal;
    }

    @RequestMapping("/")
    public String index() {
        return "/index.jsp";
    }
}
