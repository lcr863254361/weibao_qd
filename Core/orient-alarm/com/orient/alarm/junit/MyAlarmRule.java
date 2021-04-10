package com.orient.alarm.junit;

import com.orient.alarm.schedule.AlarmRule;

public class MyAlarmRule implements AlarmRule {

    @Override
    public boolean isAlarm(String params) {
        return true;
    }

    @Override
    public String getFormatContent(String content, String params) {
        return null;
    }

}
