package com.orient.alarm.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * AbstractAlarmNotice entity provides the base persistence definition of the AlarmNotice entity
 */
public abstract class AbstractAlarmNotice extends BaseObject implements Serializable {

    private String id;
    private AlarmInfo alarmInfo;

    /**
     * 通知方式：邮件00000001 短信00000010 邮件+短信00000011
     */
    private Integer noticetype; //11111111 通知

    /**
     * 通知类型：0 重发通知 1 定时通知
     */
    private String triggertype;

    /**
     * 重复发送时间间隔 毫秒
     */
    private BigDecimal interval;

    /**
     * 重复发送次数
     */
    private BigDecimal repeat;

    /**
     * 定时发送方式，指定年份
     */
    private String year;

    /**
     * 定时发送方式，指定月份
     */
    private String month;

    /**
     * 定时发送方式，指定月天
     */
    private String monthday;

    /**
     * 定时发送方式，指定周天，如每周1
     */
    private String weekday;

    /**
     * 定时发送方式，时间 12:10:10
     */
    private String time;

    public AbstractAlarmNotice() {
    }

    public AbstractAlarmNotice(AlarmInfo alarmInfo, Integer noticetype, String triggertype, BigDecimal interval, BigDecimal repeat, String year, String month, String weekday, String time, String monthday) {
        this.alarmInfo = alarmInfo;
        this.noticetype = noticetype;
        this.triggertype = triggertype;
        this.interval = interval;
        this.repeat = repeat;
        this.year = year;
        this.month = month;
        this.weekday = weekday;
        this.time = time;
        this.monthday = monthday;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AlarmInfo getAlarmInfo() {
        return this.alarmInfo;
    }

    public void setAlarmInfo(AlarmInfo alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public Integer getNoticetype() {
        return noticetype;
    }

    public void setNoticetype(Integer noticetype) {
        this.noticetype = noticetype;
    }

    public String getTriggertype() {
        return this.triggertype;
    }

    public void setTriggertype(String triggertype) {
        this.triggertype = triggertype;
    }

    public BigDecimal getInterval() {
        return this.interval;
    }

    public void setInterval(BigDecimal interval) {
        this.interval = interval;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMonthday() {
        return monthday;
    }

    public void setMonthday(String monthday) {
        this.monthday = monthday;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public BigDecimal getRepeat() {
        return repeat;
    }

    public void setRepeat(BigDecimal repeat) {
        this.repeat = repeat;
    }

}