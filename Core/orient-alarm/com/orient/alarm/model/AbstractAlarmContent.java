package com.orient.alarm.model;

import java.io.Serializable;
import java.sql.Clob;

/**
 * AbstractAlarmContent entity provides the base persistence definition of the AlarmContent entity
 */
public abstract class AbstractAlarmContent extends BaseObject implements Serializable {


    private String id;
    private AlarmInfo alarmInfo;

    /**
     * 标题
     */
    private String title;

    /**
     * 主题
     */
    private Clob content;

    /**
     * 连接 optional
     */
    private String url;

    public AbstractAlarmContent() {
    }

    /**
     * full constructor
     */
    public AbstractAlarmContent(AlarmInfo alarmInfo, String title, Clob content, String url) {
        this.alarmInfo = alarmInfo;
        this.title = title;
        this.content = content;
        this.url = url;
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

}