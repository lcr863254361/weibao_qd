/**
 * DepartmentService.java
 * com.sysmodel.service.user
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2012-3-13 		zhang yan
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.sysmodel.service.user;

import com.orient.sysmodel.domain.user.UserLoginHistory;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyan@cssrc.com.cn
 * @ClassName UserLoginHistoryService
 * <p>
 * TODO
 * @date 2012-3-15
 */
public interface UserLoginHistoryService {

    /**
     * 新增用户登录信息
     *
     * @param loginHistory
     * @return void
     * @throws
     * @Method: createLoginHistory
     * <p>
     * TODO
     */
    void createLoginHistory(UserLoginHistory loginHistory);

    /**
     * 统计Web端用户登陆次数
     *
     * @param userName
     * @return String
     * @throws
     * @Method: queryWebLoginTimes
     * <p>
     * TODO
     */
    String queryWebLoginTimes(String userName);

    /**
     * 统计某操作类型的用户登陆次数
     *
     * @param userName
     * @param optype
     * @return String
     * @throws
     * @Method: queryLoginTimesByOptype
     * <p>
     * TODO
     */
    String queryLoginTimesByOptype(String userName, String optype);

    /**
     * 查询登陆信息
     *
     * @param queryInfo
     * @return List<UserLoginHistory>
     * @throws
     * @Method: queryUserLoginHistory
     * <p>
     * TODO
     */
    List<UserLoginHistory> queryUserLoginHistory(UserLoginHistory queryInfo);

  /*  *//**
     * Save user log info.
     *
     * @param userInfo
     * @param type
     *//*
    @Deprecated
    void saveUserLogInfo(Map<String, String> userInfo, String type);*/

    /**
     * 记录本次用户登录
     *
     * @param userLoginHistory
     */
    void saveUserLoginRecord(UserLoginHistory userLoginHistory);

    /**
     * 返回code数目.
     *
     * @param name
     * @param password
     * @param code
     * @return string
     */
    String queryCodeNum(String name, String password, String code);

}

