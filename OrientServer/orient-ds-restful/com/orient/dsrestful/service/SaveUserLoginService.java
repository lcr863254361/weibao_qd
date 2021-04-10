package com.orient.dsrestful.service;

import com.orient.dsrestful.enums.ClientTypeEnum;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.domain.user.UserLoginHistory;
import com.orient.sysmodel.service.user.UserLoginHistoryService;
import com.orient.utils.CommonTools;
import com.orient.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 保存用户登录记录，主要是记录ds登录
 * Created by GNY on 2018/3/24
 */
@Component
public class SaveUserLoginService {

    @Autowired
    UserLoginHistoryService userLoginHistoryService;

    /**
     * 保存用户登录ds记录，记录到数据库
     *
     * @param user 登录用户
     * @param type 登录类别
     * @param ip   request对象
     */
    public void saveUserLoginRecord(User user, String ip, String type) {
        UserLoginHistory userLoginHistory = new UserLoginHistory();
        userLoginHistory.setUserIp(ip);
        userLoginHistory.setUserName(user.getUserName());
        userLoginHistory.setUserDisplayName(user.getAllName());
        userLoginHistory.setUserDeptName(CommonTools.Obj2String(user.getDept().getName()));
        userLoginHistory.setUserDeptid(CommonTools.Obj2String(user.getDept().getId()));
        userLoginHistory.setLoginTime(Commons.getSysDate());
        switch (ClientTypeEnum.fromString(type)) {
            case TYPE_DESIGN_STUDIO:
                userLoginHistory.setOpType(ClientTypeEnum.TYPE_DESIGN_STUDIO.toString());
                userLoginHistory.setOpMessage("Design Studio" + "登录");
                break;
            case TYPE_TBOM_STUDIO:
                userLoginHistory.setOpType(ClientTypeEnum.TYPE_TBOM_STUDIO.toString());
                userLoginHistory.setOpMessage("Tbom Studio" + "登录");
                break;
            case TYPE_ETL_STUDIO:
                userLoginHistory.setOpType(ClientTypeEnum.TYPE_ETL_STUDIO.toString());
                userLoginHistory.setOpMessage("ETL Studio" + "登录");
                break;
            case TYPE_WORK_FLOW_STUDIO:
                userLoginHistory.setOpType(ClientTypeEnum.TYPE_WORK_FLOW_STUDIO.toString());
                userLoginHistory.setOpMessage("WorkFlow Studio" + "登录");
                break;
        }
        userLoginHistoryService.saveUserLoginRecord(userLoginHistory);
    }

}
