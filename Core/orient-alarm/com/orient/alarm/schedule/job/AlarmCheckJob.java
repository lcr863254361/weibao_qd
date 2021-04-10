package com.orient.alarm.schedule.job;

import com.edm.nio.util.CommonTools;
import com.orient.alarm.model.AlarmInfo;
import com.orient.alarm.model.AlarmNotice;
import com.orient.alarm.service.impl.AlarmServiceImpl;
import com.orient.edm.init.OrientContextLoaderListener;
import org.quartz.*;

import java.util.List;

/**
 * 预警校验
 *
 * @author [创建人] qjl <br/>
 *         [创建时间] 2014-11-4 下午3:40:36 <br/>
 *         [修改人] qjl <br/>
 *         [修改时间] 2014-11-4 下午3:40:36
 */
public class AlarmCheckJob implements Job {

    public static final String ALARM_NOTICE_JOB = "ALARM_NOTICE_JOB";

    private final String ALARM_NOTICE_TRIGGER = "ALARM_NOTICE_TRIGGER";

    public static final String ALARM_NOTICE_GROUP = "ALARM_NOTICE_GROUP";

    private final String NOTICE_REPEAT = "0";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        AlarmServiceImpl alarmService = (AlarmServiceImpl) OrientContextLoaderListener.Appwac.getBean("AlarmService");
        List<AlarmInfo> unTriggerAlarms = alarmService.checkAlarm();
        //创建预警通知任务
        for (AlarmInfo alarm : unTriggerAlarms) {
            try {
                JobKey jobKey = new JobKey(ALARM_NOTICE_JOB + "_" + alarm.getId(), ALARM_NOTICE_GROUP);
                if (!context.getScheduler().checkExists(jobKey)) {
                    //判断通知类型
                    AlarmNotice notice = alarm.getAlarmNotice();

                    JobDataMap noticeJobData = new JobDataMap();
                    noticeJobData.put(AlarmNoticeJob.ALARM_INFO, alarm);

                    JobDetail noticeJob = JobBuilder.newJob(AlarmNoticeJob.class)
                            .withIdentity(jobKey)
                            .usingJobData(noticeJobData)
                            .build();

                    Trigger trigger = null;
                    //按间隔时间重复发送通知
                    if (NOTICE_REPEAT.equals(notice.getTriggertype())) {
                        SimpleScheduleBuilder schedulerBuilder = SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInMilliseconds(notice.getInterval().longValue());
                        if (notice.getRepeat() != null) {
                            schedulerBuilder.withRepeatCount(notice.getRepeat().intValue());
                        } else {
                            schedulerBuilder.repeatForever();
                        }
                        trigger = TriggerBuilder.newTrigger().startNow()
                                .withIdentity(new TriggerKey(ALARM_NOTICE_TRIGGER + "_" + alarm.getId(), ALARM_NOTICE_GROUP))
                                .withSchedule(schedulerBuilder)
                                .build();
                    } else {
                        //按指定的时间点发送通知
                        String cronExpression = getCronExpression(notice);

                        trigger = TriggerBuilder.newTrigger()
                                .withIdentity(new TriggerKey(ALARM_NOTICE_TRIGGER + "_" + alarm.getId(), ALARM_NOTICE_GROUP))
                                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                                .build();
                    }

                    context.getScheduler().scheduleJob(noticeJob, trigger);

                    alarm.setIsAlarm(true);
                    alarmService.getAlarmInfoDAO().attachDirty(alarm);

                }

            } catch (SchedulerException e) {
                throw new JobExecutionException(e);
            }
        }
    }

    /**
     * 组装定时表达式 格式： 秒 分 小时 月天 月 周天 年.
     * <p>
     * <p>detailed comment</p>
     *
     * @param notice
     * @return
     * @author [创建人] qjl <br/>
     * [创建时间] 2014-11-6 上午8:44:37 <br/>
     * [修改人] qjl <br/>
     * [修改时间] 2014-11-6 上午8:44:37
     */
    private String getCronExpression(AlarmNotice notice) {
        String cronExpression = "";
        String[] timeInfos = notice.getTime().split(":");
        cronExpression += timeInfos[2] + " " + timeInfos[1] + " " + timeInfos[0];
        //月天
        String monthDay = CommonTools.Obj2String(notice.getMonthday());
        //周天
        String weekDay = CommonTools.Obj2String(notice.getWeekday());
        //月份
        String month = CommonTools.Obj2String(notice.getMonth());
        //年份
        String year = CommonTools.Obj2String(notice.getYear());

        if (!"".equals(monthDay)) {
            cronExpression += " " + monthDay;
        } else if (!"".equals(weekDay)) {
            cronExpression += " ?";
        } else {
            cronExpression += " *";
        }
        if (!"".equals(month)) {
            cronExpression += " " + month;
        } else {
            cronExpression += " *";
        }
        if (!"".equals(weekDay)) {
            cronExpression += " " + weekDay;
        } else {
            cronExpression += " ?";
        }
        if (!"".equals(year)) {
            cronExpression += " " + year;
        } else {
            cronExpression += " *";
        }
        return cronExpression;
    }

}
