package com.orient.alarm.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractAlarmInfo entity provides the base persistence definition of the AlarmInfo entity
 */
public abstract class AbstractAlarmInfo extends BaseObject implements Serializable {

    private String id;

    /**
     * 预警等级
     */
    private Long nlevel;

    /**
     * 预警规则校验类
     */
    private String classname;

    /**
     * 预警规则校验参数
     */
    private String params;

    /**
     * 是否已产生预警
     */
    private Boolean isAlarm;

    /**
     * 预警通知方式
     */
    private AlarmNotice alarmNotice;

    /**
     * 预警通知内容
     */
    private AlarmContent alarmContent;

    /**
     * 预警通知人、抄送人
     */
    private Set alarmUsers = new HashSet(0);

    public AbstractAlarmInfo() {
    }

    public AbstractAlarmInfo(Long nlevel, String classname, String params, AlarmNotice alarmNotice, AlarmContent alarmContent, Set alarmUsers, Boolean isAlarm) {
        this.nlevel = nlevel;
        this.classname = classname;
        this.params = params;
        this.alarmNotice = alarmNotice;
        this.alarmContent = alarmContent;
        this.alarmUsers = alarmUsers;
        this.isAlarm = isAlarm;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getNlevel() {
        return this.nlevel;
    }

    public void setNlevel(Long nlevel) {
        this.nlevel = nlevel;
    }

    public String getClassname() {
        return this.classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public AlarmNotice getAlarmNotice() {
        return alarmNotice;
    }

    public void setAlarmNotice(AlarmNotice alarmNotice) {
        this.alarmNotice = alarmNotice;
    }

    public AlarmContent getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(AlarmContent alarmContent) {
        this.alarmContent = alarmContent;
    }

    public Set getAlarmUsers() {
        return this.alarmUsers;
    }

    public void setAlarmUsers(Set alarmUsers) {
        this.alarmUsers = alarmUsers;
    }

    public Boolean getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(Boolean isAlarm) {
        this.isAlarm = isAlarm;
    }

}