package com.orient.alarm.model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * AlarmInfo entity. @author MyEclipse Persistence Tools
 */
public class AlarmInfo extends AbstractAlarmInfo implements java.io.Serializable {


    private Map alarmUserMap = new HashMap(0);

    public AlarmInfo() {
    }

    /**
     * full constructor
     */
    public AlarmInfo(Long nlevel, String classname, String params, AlarmNotice alarmNotice, AlarmContent alarmContent, Set alarmUsers, Boolean isAlarm) {
        super(nlevel, classname, params, alarmNotice, alarmContent, alarmUsers, isAlarm);
    }

    /**
     * @return the alarmUserMap
     */
    public Map getAlarmUserMap() {
        return alarmUserMap;
    }

    /**
     * @param alarmUserMap the alarmUserMap to set
     */
    public void setAlarmUserMap(Map alarmUserMap) {
        this.alarmUserMap = alarmUserMap;
    }

    public AlarmInfo copyAlarm() {
        AlarmInfo alarmInfo = null;
        ByteArrayOutputStream byteArrayOut = null;
        ObjectOutputStream out = null;
        ByteArrayInputStream byteArrayIn = null;
        ObjectInputStream in = null;
        try {
            byteArrayOut = new ByteArrayOutputStream();
            out = new ObjectOutputStream(byteArrayOut);
            out.writeObject(this);
            byteArrayIn = new ByteArrayInputStream(byteArrayOut.toByteArray());
            in = new ObjectInputStream(byteArrayIn);
            alarmInfo = (AlarmInfo) in.readObject();
            alarmInfo.getAlarmContent().setContent(this.getAlarmContent().getContent());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (byteArrayIn != null) {
                try {
                    byteArrayIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (byteArrayOut != null) {
                try {
                    byteArrayOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return alarmInfo;
    }

}
