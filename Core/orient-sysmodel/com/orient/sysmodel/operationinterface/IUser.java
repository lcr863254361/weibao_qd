/**
 * IUser.java
 * com.orient.sysmodel.roleengine.operationinterface
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2012-4-5 		zhang yan
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.sysmodel.operationinterface;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.orient.metamodel.operationinterface.IEnum;
import com.orient.sysmodel.domain.user.Department;

/**
 * ClassName:IUser
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author zhang yan
 * @version
 * @since Ver 1.1
 * @Date 2012-4-5		上午11:00:13
 *
 * @see
 */
public interface IUser {

    /**
     *
     * @Method: getId

     * 用户ID

     * @return

     * @return String

     * @throws
     */
    public String getId();

    /**
     *
     * @Method: getUserName

     * 用户名称

     * @return

     * @return String

     * @throws
     */
    public String getUserName();

    /**
     *
     * @Method: getAllName

     * 真实姓名

     * @return

     * @return String

     * @throws
     */
    public String getAllName();

    /**
     *
     * @Method: getPassword

     * 密码(加密过的)

     * @return

     * @return String

     * @throws
     */
    public String getPassword();

    /**
     *
     * @Method: getSex

     * 性别 1：男  0：女

     * @return

     * @return String

     * @throws
     */
    public String getSex();

    /**
     *
     * @Method: getSexList

     * 性别列表

     * @return

     * @return List<IEnum>

     * @throws
     */
    public List<IEnum> getSexList();

    /**
     *
     * @Method: getSexValue

     * 性别显示值

     * @return

     * @return String

     * @throws
     */
    public String getSexValue();

    /**
     *
     * @Method: getPhone

     * 电话

     * @return

     * @return String

     * @throws
     */
    public String getPhone();

    /**
     *
     * @Method: getPost

     * 职务

     * @return

     * @return String

     * @throws
     */
    public String getPost();

    /**
     *
     * @Method: getSpecialty

     * 专业

     * @return

     * @return String

     * @throws
     */
    public String getSpecialty();

    /**
     *
     * @Method: getGrade

     * 密级编码

     * @return

     * @return String

     * @throws
     */
    public String getGrade();

    /**
     *
     * @Method: getGradeList

     * 密级列表

     * @return

     * @return List<IEnum>

     * @throws
     */
    public List<IEnum> getGradeList();

    /**
     *
     * @Method: getGradeValue

     * 密级显示值

     * @return

     * @return String

     * @throws
     */
    public String getGradeValue();

    /**
     *
     * @Method: getCreateTime

     * 创建时间

     * @return

     * @return Date

     * @throws
     */
    public Date getCreateTime();

    /**
     *
     * @Method: getCreateUser

     * 创建人员

     * @return

     * @return String

     * @throws
     */
    public String getCreateUser();

    /**
     *
     * @Method: getUpdateTime

     * 修改时间

     * @return

     * @return Date

     * @throws
     */
    public Date getUpdateTime();

    /**
     *
     * @Method: getUpdateUser

     * 修改操作人员

     * @return

     * @return String

     * @throws
     */
    public String getUpdateUser();

    /**
     *
     * @Method: getNotes

     * 备注

     * @return

     * @return String

     * @throws
     */
    public String getNotes();

    /**
     *
     * @Method: getState

     * 启用标志 1：启用 0：禁止

     * @return

     * @return String

     * @throws
     */
    public String getState();

    /**
     *
     * @Method: getBirthday

     * 出生年月日

     * @return

     * @return Date

     * @throws
     */
    public Date getBirthday();

    /**
     *
     * @Method: getMobile

     * 手机

     * @return

     * @return String

     * @throws
     */
    public String getMobile();

    /**
     *
     * @Method: getFlg

     * 固化信息标志  1：表示为固化数据

     * @return

     * @return String

     * @throws
     */
    public String getFlg();

    /**
     *
     * @Method: getDept

     * 部门

     * @return

     * @return Department

     * @throws
     */
    public IDepartment getDept();

    /**
     *
     * @Method: getIsDel

     * 帐号是否能删除标志 1不能删除

     * @return

     * @return String

     * @throws
     */
    public String getIsDel();

    /**
     *
     * @Method: getEmail

     * Email 账户

     * @return

     * @return String

     * @throws
     */
    public String getEmail();

    /**
     *
     * @Method: getPasswordSetTime

     * 当前密码设置时间

     * @return

     * @return Date

     * @throws
     */
    public Date getPasswordSetTime();

    /**
     *
     * @Method: getLockState

     * 帐号锁定状态 :0没有锁定，1锁定

     * @return

     * @return String

     * @throws
     */
    public String getLockState();

    /**
     *
     * @Method: getLockState

     * 单位名称

     * @return

     * @return String

     * @throws
     */
//	public String getUnit();

    /**
     *
     * @Method: getLockTime

     * 帐号锁定时间

     * @return

     * @return Date

     * @throws
     */
    public Date getLockTime();

    /**
     *
     * @Method: getLoginFailures

     * 登陆失败次数

     * @return

     * @return String

     * @throws
     */
    public String getLoginFailures();

    /**
     *
     * @Method: getLastFailureTime

     * 最近一次登陆失败的时间

     * @return

     * @return Date

     * @throws
     */
    public Date getLastFailureTime();

    /**
     *
     * @Method: getPasswordHistories

     * 密码历史记录

     * @return

     * @return Set

     * @throws
     */
    public Set getPasswordHistories();

    /**
     *
     * @Method: getUserColumnBySTName

     * 根据字段名查找用户字段信息

     * @param stName
     * @return

     * @return IUserColumn

     * @throws
     */
    public IUserColumn getUserColumnBySTName(String stName);

    /**
     *
     * @Method: getAllUserColumns

     * 取得所有的用户字段信息

     * @return

     * @return List<IUserColumn>

     * @throws
     */
    public List<IUserColumn> getAllUserColumns();

    /**
     *
     * @Method: getUserColumnsForSearch

     * 取得用于用户信息页面查询的字段信息

     * @return

     * @return List<IUserColumn>

     * @throws
     */
    public List<IUserColumn> getUserColumnsForSearch();

    /**
     *
     * @Method: getUserColumnsForDisplayShow

     * 取得用于用户信息列表页面的字段信息

     * @return

     * @return List<IUserColumn>

     * @throws
     */
    public List<IUserColumn> getUserColumnsForDisplayShow();

    /**
     *
     * @Method: getUserColumnsForEditShow

     * 取得用于用户信息明细和修改页面的字段信息

     * @return

     * @return List<IUserColumn>

     * @throws
     */
    public List<IUserColumn> getUserColumnsForEditShow();

    /**
     *
     * @Method: getUserColumnsForUserRoleSearch

     * 取得用于用户角色信息页面查询的字段信息

     * @return

     * @return List<IUserColumn>

     * @throws
     */
    public List<IUserColumn> getUserColumnsForUserRoleSearch();

    /**
     *
     * @Method: getUserColumnsForUserRoleDisplayShow

     * 取得用于用户角色信息列表页面的字段信息

     * @return

     * @return List<IUserColumn>

     * @throws
     */
    public List<IUserColumn> getUserColumnsForUserRoleDisplayShow();

    /**
     *
     * @Method: getUserColumnsForUserRoleEditShow

     * 取得用于用户角色信息明细页面的字段信息

     * @return

     * @return List<IUserColumn>

     * @throws
     */
    public List<IUserColumn> getUserColumnsForUserRoleEditShow();

    /**
     * 返回该用户参与的代办
     * @Method: getJbpmConfigUserSet
     * @return
     */
    public Set getJbpmConfigUserSet();

    /**
     * 返回该用户代办配置
     * @Method: getJbpmUserSet
     * @return
     */
    public Set getJbpmUserSet();

    public String getPersonClassify();
}

