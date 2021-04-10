package com.orient.alarm.schedule;

import com.orient.alarm.schedule.job.AlarmCheckJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.File;
import java.net.URISyntaxException;

public class AlarmScheduler {

    public final static String ALARM_SCHEDULER = "ALARM_SCHEDULER";

    private final String ALARM_CHECK_JOB = "ALARM_CHECK_JOB";

    private final String ALARM_CHECK_TIRGGER = "ALARM_CHECK_TRIGGER";

    private final String ALARM_CHECK_GROUP = "ALARM_CHECK_GROUP";

    private static AlarmScheduler instance;

    private SchedulerFactory schedulerFactory;

    private AlarmScheduler() {
    }

    public static AlarmScheduler getInstance() {
        if (instance == null) {
            instance = new AlarmScheduler();
        }
        return instance;
    }

    public void start(long interval) {

        try {
            String properties = new File(this.getClass().getResource("AlarmScheduler.class").toURI().getPath()).getParent()
                    + File.separator + "quartz.properties";

            schedulerFactory = new StdSchedulerFactory(properties);
            Scheduler scheduler = schedulerFactory.getScheduler();

            //启动预警校验任务
            JobDetail alarmJob = JobBuilder.newJob(AlarmCheckJob.class)
                    .withIdentity(new JobKey(ALARM_CHECK_JOB, ALARM_CHECK_GROUP))
                    .build();

            SimpleTrigger alarmTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(new TriggerKey(ALARM_CHECK_TIRGGER, ALARM_CHECK_GROUP))
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInMilliseconds(interval).repeatForever())
                    .build();
            scheduler.scheduleJob(alarmJob, alarmTrigger);
            scheduler.start();
        } catch (SchedulerException |URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Scheduler getScheduler(String name) {
        Scheduler scheduler = null;
        try {
            scheduler = schedulerFactory.getScheduler(name);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return scheduler;
    }

    public boolean deleteJob(JobKey jobKey) {
        boolean isDeleted = false;
        try {
            if (schedulerFactory != null) {

                Scheduler scheduler = schedulerFactory.getScheduler(ALARM_SCHEDULER);

                scheduler.deleteJob(jobKey);

                isDeleted = true;
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

}
