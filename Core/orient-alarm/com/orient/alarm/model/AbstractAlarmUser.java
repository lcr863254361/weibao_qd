package com.orient.alarm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.orient.sysmodel.domain.BaseBean;
import com.orient.sysmodel.domain.user.Department;

/**
 * AbstractUser entity provides the base persistence definition of the User entity.
 */
public abstract class AbstractAlarmUser extends BaseBean implements Serializable {

     /**
      * 用户ID
      */
     private String id;
     
     /**
      * 用户名称
      */
     private String userName;
     
     /**
      * 真实姓名
      */
     private String allName;
     
     /**
      * 密码
      */
     private String password;
     
     /**
      * 性别 1：男  0：女
      */
     private String sex;
     
     /**
      * 电话
      */
     private String phone;
     
     /**
      * 职务
      */
     private String post;

     /**
      * 专业
      */
     private String specialty;
     
     /**
      * 密级
      */
     private String grade;
     
     /**
      * 创建时间
      */
     private Date createTime;
     
     /**
      * 创建操作人员
      */
     private String createUser;
     
     /**
      * 修改时间
      */
     private Date updateTime;
     
     /**
      * 修改操作人员
      */
     private String updateUser;
     
     /**
      * 备注
      */
     private String notes;
     
     /**
      * 启用标志 1：启用 0：禁止
      */
     private String state;
     
     /**
      * 出生年月日
      */
     private Date birthday;
     
     /**
      * 手机
      */
     private String mobile;
     
     /**
      * 固化信息标志  1：表示为固化数据
      */
     private String flg;
     
     /**
      * 部门
      */
     private Department dept;
     /**
      *  部门名称
      */
 	private String depName;
     
     /**
      * 帐号是否能删除标志 1不能删除
      */
     private String isDel;
     
     /**
      *  Email 账户
      */
     private String EMail;
     
     /**
      * 当前密码设置时间
      */
     private Date passwordSetTime;
     
     /**
      * 帐号锁定状态 :0没有锁定，1锁定
      */
     private String lockState;
     
     /**
      * 帐号锁定时间
      */
     private Date lockTime;
     
     /**
      * 登陆失败次数
      */
     private String loginFailures;
     
     /**
      * 最近一次登陆失败的时间
      */
     private Date lastFailureTime;

    public AbstractAlarmUser() {
    }

    public AbstractAlarmUser(String userName, String allName, String password, Date createTime, String createUser, String state) {
        this.userName = userName;
        this.allName = allName;
        this.password = password;
        this.createTime = createTime;
        this.createUser = createUser;
        this.state = state;
    }

    public AbstractAlarmUser(String userName, String allName, String password, String sex, String phone, String post, String specialty, String grade, Date createTime, String createUser, Date updateTime, String updateUser, String notes, String state, Date birthday, String mobile, String flg, Department dept, String isDel, String EMail, Date passwordSetTime, String lockState, Date lockTime, String loginFailures, Date lastFailureTime) {
        this.userName = userName;
        this.allName = allName;
        this.password = password;
        this.sex = sex;
        this.phone = phone;
        this.post = post;
        this.specialty = specialty;
        this.grade = grade;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.notes = notes;
        this.state = state;
        this.birthday = birthday;
        this.mobile = mobile;
        this.flg = flg;
        this.dept = dept;
        this.isDel = isDel;
        this.EMail = EMail;
        this.passwordSetTime = passwordSetTime;
        this.lockState = lockState;
        this.lockTime = lockTime;
        this.loginFailures = loginFailures;
        this.lastFailureTime = lastFailureTime;
    }

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAllName() {
        return this.allName;
    }
    
    public void setAllName(String allName) {
        this.allName = allName;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return this.sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPost() {
        return this.post;
    }
    
    public void setPost(String post) {
        this.post = post;
    }

    public String getSpecialty() {
        return this.specialty;
    }
    
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getGrade() {
        return this.grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }
    
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }

    public Date getBirthday() {
        return this.birthday;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFlg() {
        return this.flg;
    }
    
    public void setFlg(String flg) {
        this.flg = flg;
    }

    public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getIsDel() {
        return this.isDel;
    }
    
    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getEMail() {
        return this.EMail;
    }
    
    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public Date getPasswordSetTime() {
        return this.passwordSetTime;
    }
    
    public void setPasswordSetTime(Date passwordSetTime) {
        this.passwordSetTime = passwordSetTime;
    }

    public String getLockState() {
        return this.lockState;
    }
    
    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public Date getLockTime() {
        return this.lockTime;
    }
    
    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public String getLoginFailures() {
        return this.loginFailures;
    }
    
    public void setLoginFailures(String loginFailures) {
        this.loginFailures = loginFailures;
    }

    public Date getLastFailureTime() {
        return this.lastFailureTime;
    }
    
    public void setLastFailureTime(Date lastFailureTime) {
        this.lastFailureTime = lastFailureTime;
    }

    public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("loginName=" + this.userName);
		sb.append("|password=" + this.password);
		sb.append("|mobile=" + this.mobile);
		sb.append("|createDate=" + this.createTime);
		return sb.toString();
	}

}