package com.orient.metamodel.metadomain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 对应CWM_SCHEMA
 *
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractSchema extends BaseMetaBean {

    /**
     * The id.
     */
    private String id;

    /**
     * The name.
     */
    private String name;

    /**
     * The version.
     */
    private String version;

    /**
     * The description.
     */
    private String description;

    /**
     * The modified time.
     */
    private Date modifiedTime;

    /**
     * 记录业务库是否上锁的状态，0表示未上锁，1表示已上锁
     */
    private Integer isLock;

    /**
     * 当前操作数据库的用户
     */
    private String username;

    /**
     * 当前操作数据库的主机IP
     */
    private String ip;

    /**
     * The lock modified time.
     */
    private Date lockModifiedTime;

    /**
     * The isdelete.
     */
    private Integer isdelete;

    /**
     * 对应所有的数据类
     */
    private Set<Table> tables = new HashSet<>(0);

    /**
     * 对应所有的约束
     */
    private Set<Restriction> restrictions = new HashSet<>(0);

    /**
     * 对应所有的视图
     */
    private Set<View> views = new HashSet<>(0);

    /**
     * 对应该业务库能够使用的密级
     */
    private String secrecySet;

    /**
     * 对应schema的类型 0：普通业务库 1：共享业务库
     */
    private String type;


    public AbstractSchema() {
    }

    public AbstractSchema(String name, String version) {
        this.name = name;
        this.version = version;
    }

    /**
     * minimal constructor.
     *
     * @param name             the name
     * @param version          the version
     * @param modifiedTime     the modified time
     * @param isLock           the is lock
     * @param username         the username
     * @param ip               the ip
     * @param lockModifiedTime the lock modified time
     */
    public AbstractSchema(String name, String version, Date modifiedTime, Integer isLock, String username, String ip, Date lockModifiedTime) {
        this.name = name;
        this.version = version;
        this.modifiedTime = modifiedTime;
        this.isLock = isLock;
        this.username = username;
        this.ip = ip;
        this.lockModifiedTime = lockModifiedTime;
    }

    /**
     * full constructor.
     *
     * @param name             the name
     * @param version          the version
     * @param description      the description
     * @param modifiedTime     the modified time
     * @param isLock           the is lock
     * @param username         the username
     * @param ip               the ip
     * @param lockModifiedTime the lock modified time
     * @param cwmTableses      the cwm tableses
     * @param cwmRestrictions  the cwm restrictions
     * @param cwmViewses       the cwm viewses
     */
    public AbstractSchema(String name, String version, String description, Date modifiedTime, Integer isLock, String username, String ip, Date lockModifiedTime, Set cwmTableses, Set cwmRestrictions, Set cwmViewses) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.modifiedTime = modifiedTime;
        this.isLock = isLock;
        this.username = username;
        this.ip = ip;
        this.lockModifiedTime = lockModifiedTime;
        this.tables = cwmTableses;
        this.restrictions = cwmRestrictions;
        this.views = cwmViewses;
    }

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

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLockModifiedTime() {
        return lockModifiedTime;
    }

    public void setLockModifiedTime(Date lockModifiedTime) {
        this.lockModifiedTime = lockModifiedTime;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public Set<Table> getTables() {
        return tables;
    }

    public void setTables(Set<Table> tables) {
        this.tables = tables;
    }

    public Set<Restriction> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(Set<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    public Set<View> getViews() {
        return views;
    }

    public void setViews(Set<View> views) {
        this.views = views;
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
}