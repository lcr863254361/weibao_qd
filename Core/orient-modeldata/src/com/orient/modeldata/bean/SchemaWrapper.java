package com.orient.modeldata.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * schema包装类
 *
 * @author enjoy
 * @creare 2016-05-17 9:03
 */
public class SchemaWrapper implements Serializable{

    /** The id. */
    private String id;

    /** The name. */
    private String name;

    /** The version. */
    private String version;

    /** The name + version. */
    private String baseInfo;

    /** The description. */
    private String description;

    /** The modified time. */
    private Date modifiedTime;

    /** 记录业务库是否上锁的状态，0表示未上锁，1表示已上锁 */
    private Long isLock;

    /** 当前操作数据库的用户的名称以及所在的主机IP，格式为IP+”====”+NAME */
    private String userid;

    /** The lock modified time. */
    private Date lockModifiedTime;

    /** The isdelete. */
    private Long isdelete;

    /** 对应该业务库能够使用的密级 */
    private String secrecySet;

    /** 对应schema的类型 0：普通业务库 1：共享业务库 */
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Long getIsLock() {
        return isLock;
    }

    public void setIsLock(Long isLock) {
        this.isLock = isLock;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getLockModifiedTime() {
        return lockModifiedTime;
    }

    public void setLockModifiedTime(Date lockModifiedTime) {
        this.lockModifiedTime = lockModifiedTime;
    }

    public Long getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Long isdelete) {
        this.isdelete = isdelete;
    }

    public String getSecrecySet() {
        return secrecySet;
    }

    public void setSecrecySet(String secrecySet) {
        this.secrecySet = secrecySet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBaseInfo() {

        baseInfo = name + "  [" + version + "]";
        return baseInfo;
    }
}
