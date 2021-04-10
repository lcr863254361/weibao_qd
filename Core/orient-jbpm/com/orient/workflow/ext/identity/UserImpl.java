package com.orient.workflow.ext.identity;

import java.io.Serializable;
import java.util.Date;

import org.jbpm.api.identity.User;

/**
 * 用户表 UserImpl
 * 
 * @version 1.0
 * 
 */

public class UserImpl implements Serializable, User {

  private static final long serialVersionUID = 1L;
  // Fields

  private String userName;
  private String db_id;
  private String allName = "匿名用户";
  private String password = "12345";
  private String sex = "1";
  private String phone;
  private String post;
  private String specialty;
  private String grade;
  private Date createTime = new Date();
  private String createUser = "jbpm4";
  private Date updateTime;
  private String updateUser;
  private String notes;
  private String state = "1";
  private Date birthday;
  private String mobile;
  private String flg;
  private String depId;
  private String isDel;
  private String EMail = "test@test.cn";

  // Constructors

  /** default constructor */
  public UserImpl() {
  }

  /** minimal constructor */
  public UserImpl(String db_id, String userName,String password, String allName,String eMail) {
    this.db_id = db_id;
    this.userName = userName;
    if(allName!=null)this.allName = allName;
    if(password!=null)this.password = password;
    if(eMail!=null)this.EMail = eMail;
  }

  /** full constructor */
  public UserImpl(String id, String userName, String allName, String password, String sex, String phone, String post, String specialty, String grade,
          Date createTime, String createUser, Date updateTime, String updateUser, String notes, String state, Date birthday, String mobile, String flg,
          String depId, String isDel, String EMail) {
    this.db_id = id;
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
    this.depId = depId;
    this.isDel = isDel;
    this.EMail = EMail;
  }

  // Property accessors

  public String getId() {
    return this.userName;
  }

  public void setId(String userName) {
    this.userName = userName;
  }

  public String getDb_id() {
    return db_id;
  }

  public void setDb_id(String dbId) {
    db_id = dbId;
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

  public String getDepId() {
    return this.depId;
  }

  public void setDepId(String depId) {
    this.depId = depId;
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

  public String getBusinessEmail() {
    return this.EMail;
  }

  public String getFamilyName() {
    return null;
  }

  public String getGivenName() {
    return this.allName;
  }
}
