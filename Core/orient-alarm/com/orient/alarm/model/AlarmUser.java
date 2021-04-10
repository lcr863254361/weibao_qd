package com.orient.alarm.model;

import com.orient.sysmodel.domain.user.Department;

import java.util.Date;

/**
 * User entity
 */
public class AlarmUser extends AbstractAlarmUser {

    public AlarmUser() {
    }

    public AlarmUser(String userName, String allName, String password, Date createTime, String createUser, String state) {
        super(userName, allName, password, createTime, createUser, state);
    }

    public AlarmUser(String userName, String allName, String password, String sex, String phone, String post, String specialty, String grade, Date createTime, String createUser, Date updateTime, String updateUser, String notes, String state, Date birthday, String mobile, String flg, Department dept, String isDel, String EMail, Date passwordSetTime, String lockState, Date lockTime, String loginFailures, Date lastFailureTime) {
        super(userName, allName, password, sex, phone, post, specialty, grade, createTime, createUser, updateTime, updateUser, notes, state, birthday, mobile, flg, dept, isDel, EMail, passwordSetTime, lockState, lockTime, loginFailures, lastFailureTime);
    }

}
