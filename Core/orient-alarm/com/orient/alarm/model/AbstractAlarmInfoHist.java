package com.orient.alarm.model;

import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractAlarmInfoHist entity provides the base persistence definition of the AlarmInfoHist entity.
 */

public abstract class AbstractAlarmInfoHist extends com.orient.alarm.model.BaseObject implements java.io.Serializable {

    private String id;
    private Long nlevel;
    private String title;
    private Clob content;
    private String url;
    private Date sendtime;
    private Set alarmUserHists = new HashSet(0);

    public AbstractAlarmInfoHist() {
    }

    public AbstractAlarmInfoHist(Long nlevel, String title, Clob content, String url, Date sendtime, Set alarmUserHists) {
        this.nlevel = nlevel;
        this.title = title;
        this.content = content;
        this.url = url;
        this.sendtime = sendtime;
        this.alarmUserHists = alarmUserHists;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Clob getContent() {
        return this.content;
    }

    public void setContent(Clob content) {
        this.content = content;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getSendtime() {
        return this.sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public Set getAlarmUserHists() {
        return this.alarmUserHists;
    }

    public void setAlarmUserHists(Set alarmUserHists) {
        this.alarmUserHists = alarmUserHists;
    }

}