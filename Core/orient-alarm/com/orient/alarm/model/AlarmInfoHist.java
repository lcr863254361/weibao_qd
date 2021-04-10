package com.orient.alarm.model;

import java.sql.Clob;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * AlarmInfoHist entity. @author MyEclipse Persistence Tools
 */
public class AlarmInfoHist extends AbstractAlarmInfoHist implements java.io.Serializable {

    private Map alarmUserMap = new HashMap(0);

    public AlarmInfoHist() {
    }

    public AlarmInfoHist(Long nlevel, String title, Clob content, String url, Date sendtime, Set alarmUserHists) {
        super(nlevel, title, content, url, sendtime, alarmUserHists);
    }

    public Map getAlarmUserMap() {
        return alarmUserMap;
    }

    public void setAlarmUserMap(Map alarmUserMap) {
        this.alarmUserMap = alarmUserMap;
    }

}
