package com.orient.alarm.model;

import java.sql.Clob;

/**
 * AlarmContent entity
 */
public class AlarmContent extends AbstractAlarmContent {

    public AlarmContent() {
    }

    public AlarmContent(AlarmInfo alarmInfo, String title, Clob content, String url) {
        super(alarmInfo, title, content, url);
    }

}
