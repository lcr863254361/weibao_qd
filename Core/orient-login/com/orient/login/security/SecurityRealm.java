package com.orient.login.security;


import com.orient.login.business.LoginBusiness;
import com.orient.utils.StringUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


/**
 * 用户身份验证,授权 Realm 组件
 * 
 * @author StarZou
 * @since 2014年6月11日 上午11:35:28
 **/
@Component(value = "securityRealm")
public class SecurityRealm extends AuthorizingRealm {

    @Autowired
    LoginBusiness loginService;

    /**
     * 权限检查
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = String.valueOf(principals.getPrimaryPrincipal());

        authorizationInfo.addRole("");

        Set<String> permissions = new HashSet<>();
        authorizationInfo.addStringPermissions(permissions);

        return authorizationInfo;
    }

    /**
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = String.valueOf(token.getPrincipal());
        String password = new String((char[]) token.getCredentials());
        String ip = "unknown";
        if(token instanceof UsernamePasswordToken) {
            String userIp = ((UsernamePasswordToken) token).getHost();
            if(!StringUtil.isEmpty(userIp)) {
                ip = userIp;
            }
        }

        StringBuffer errorInfo = new StringBuffer();
        if(!loginService.login(errorInfo, username, password, ip)){
            throw new AuthenticationException(errorInfo.toString());
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
        return authenticationInfo;
    }

}
