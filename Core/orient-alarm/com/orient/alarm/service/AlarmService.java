package com.orient.alarm.service;

import com.orient.alarm.model.AlarmInfo;
import com.orient.alarm.model.AlarmInfoHist;

import java.util.List;

public interface AlarmService {

    /**
     * 注册预警信息.
     *
     * @return
     * @author [创建人] qjl <br/>
     * [创建时间] 2014-11-4 上午10:33:58 <br/>
     * [修改人] qjl <br/>
     * [修改时间] 2014-11-4 上午10:33:58
     */
    String register(AlarmInfo alarmInfo);

    /**
     * 注销预警信息.
     *
     * @param sid
     * @return
     * @author [创建人] qjl <br/>
     * [创建时间] 2014-11-4 上午10:34:19 <br/>
     * [修改人] qjl <br/>
     * [修改时间] 2014-11-4 上午10:34:19
     */
    boolean cancel(String sid);

    /**
     * 获取未触发的预警信息.
     *
     * @return
     * @author [创建人] qjl <br/>
     * [创建时间] 2014-11-4 下午4:09:13 <br/>
     * [修改人] qjl <br/>
     * [修改时间] 2014-11-4 下午4:09:13
     */
    List<AlarmInfo> checkAlarm();

    /**
     * 获取我的预警通知历史.
     *
     * @param userId
     * @return
     * @author [创建人] qjl <br/>
     * [创建时间] 2014-11-6 下午3:07:49 <br/>
     * [修改人] qjl <br/>
     * [修改时间] 2014-11-6 下午3:07:49
     */
    List<AlarmInfoHist> getMyAlarmHist(String userId);

    /**
     * 修改通知人.
     *
     * @param alarmId
     * @param copyers
     * @author [创建人] qjl <br/>
     * [创建时间] 2014-11-16 上午11:13:19 <br/>
     * [修改人] qjl <br/>
     * [修改时间] 2014-11-16 上午11:13:19
     */
    void modifyCopyer(String alarmId, List<String> copyers);

    /**
     * 获取我的预警.
     *
     * @param userId
     * @return
     * @author [创建人] qjl <br/>
     * [创建时间] 2014-11-20 下午7:45:07 <br/>
     * [修改人] qjl <br/>
     * [修改时间] 2014-11-20 下午7:45:07
     */
    List<AlarmInfo> getMyAlarm(String userId);

}