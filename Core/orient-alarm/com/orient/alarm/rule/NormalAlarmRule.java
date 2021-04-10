package com.orient.alarm.rule;

import com.orient.alarm.schedule.AlarmRule;
import com.orient.utils.CommonTools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NormalAlarmRule implements AlarmRule {

    private int alertTimeLength = 10;
    public static String ALARM_PARAM_SPLIT = ";";

    @Override
    public boolean isAlarm(String params) {

        String[] paramArray = params.split(ALARM_PARAM_SPLIT);

        alertTimeLength = Integer.valueOf(paramArray[0]);

        Date startDate = CommonTools.str2Date(paramArray[1]);
        String curDateStr = CommonTools.getSysdate();
        Date curDate = CommonTools.str2Date(curDateStr);

        int dayPassed = CommonTools.getDayCountBetweenDates(startDate, curDate);

        if (dayPassed < alertTimeLength) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String getFormatContent(String content, String params) {
        try {
            String[] parameters = params.split(ALARM_PARAM_SPLIT);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(parameters[1]));
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + Integer.parseInt(parameters[0]));
            content = "您有一个“" + parameters[4] + "”任务，" + dateFormat.format(calendar.getTime()) + "到期";
        } catch (NumberFormatException |ParseException e) {
            e.printStackTrace();
        }
        return content;
    }

}
