/**
 * EDM_UserContainer_Async.java
 * com.orient.edm.application.client.asyncmodel
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * Jul 6, 2012 		mengbin
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.edm.asyncbean;

/**
 * 存储与用户相关的信息
 *
 * @author mengbin@cssrc.com.cn
 * @date Jul 6, 2012
 */
public class EDM_UserContainer_Async extends EDM_Object_Async {


    /**
     * @Fields userId : 用户Id
     */
    private String userId;

    /**
     * @Fields displayName : 显示的中文名
     */
    private String displayName;
    /**
     * @Fields loginName : 登录名
     */
    private String loginName;
    /**
     * @Fields userIP : 用户的Ip地址
     */
    private String userIP;
    /**
     * @Fields departName : 部门名称
     */
    private String departName;

    /**
     * @Fields departId : 部门Id
     */
    private String departId;


    public EDM_UserContainer_Async() {

    }

    /**
     * userId
     *
     * @return the userId
     * @since CodingExample Ver 1.0
     */

    public String getUserId() {
        return userId;
    }

    /**
     * userId
     *
     * @param userId the userId to set
     * @since CodingExample Ver 1.0
     */

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * displayName
     *
     * @return the displayName
     * @since CodingExample Ver 1.0
     */

    public String getDisplayName() {
        return displayName;
    }

    /**
     * displayName
     *
     * @param displayName the displayName to set
     * @since CodingExample Ver 1.0
     */

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * loginName
     *
     * @return the loginName
     * @since CodingExample Ver 1.0
     */

    public String getLoginName() {
        return loginName;
    }

    /**
     * loginName
     *
     * @param loginName the loginName to set
     * @since CodingExample Ver 1.0
     */

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * userIP
     *
     * @return the userIP
     * @since CodingExample Ver 1.0
     */

    public String getUserIP() {
        return userIP;
    }

    /**
     * userIP
     *
     * @param userIP the userIP to set
     * @since CodingExample Ver 1.0
     */

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    /**
     * departName
     *
     * @return the departName
     * @since CodingExample Ver 1.0
     */

    public String getDepartName() {
        return departName;
    }

    /**
     * departName
     *
     * @param departName the departName to set
     * @since CodingExample Ver 1.0
     */

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    /**
     * departId
     *
     * @return the departId
     * @since CodingExample Ver 1.0
     */

    public String getDepartId() {
        return departId;
    }

    /**
     * departId
     *
     * @param departId the departId to set
     * @since CodingExample Ver 1.0
     */

    public void setDepartId(String departId) {
        this.departId = departId;
    }


}

