package com.orient.alarm.schedule;

/**
 * AlarmInfo.classname需要实现的接口.
 *
 * @author [创建人] qjl <br/>
 *         [创建时间] 2014-11-4 上午11:01:46 <br/>
 *         [修改人] qjl <br/>
 *         [修改时间] 2014-11-4 上午11:01:46
 */
public interface AlarmRule {

    boolean isAlarm(String params);

    String getFormatContent(String content, String params);

}
