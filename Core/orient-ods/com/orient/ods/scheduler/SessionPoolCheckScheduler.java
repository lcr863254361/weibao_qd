/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.ods.scheduler;

import com.orient.edm.init.IContextLoadRun;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by mengbin on 16/3/23.
 * Purpose: 能够管理数据读写的SessionPool
 * Detail:
 */
@Service
public class SessionPoolCheckScheduler implements IContextLoadRun {

    private static SessionPoolCheckScheduler instance = null;

    private SchedulerFactory schedulerFactory;

    public final static String SESSIONPOOLCHECK_SCHEDULER = "SESSIONPOOLCHECK_SCHEDULER";

    private final String SESSIONPOOL_CHECK_JOB = "SESSIONPOOL_CHECK_JOB";

    private final String SESSIONPOOL_CHECK_TIRGGER = "SESSIONPOOL_CHECK_TRIGGER";

    private final String SESSIONPOOL_CHECK_GROUP = "SESSIONPOOL_CHECK_GROUP";


    private SessionPoolCheckScheduler() {
    }

    public static SessionPoolCheckScheduler getInstance() {
        if (instance == null) {
            instance = new SessionPoolCheckScheduler();
        }
        return instance;
    }

    public void start(long interval) {
        try {
            InputStream in = this.getClass().getResourceAsStream("/com/orient/ods/scheduler/quartz.properties");
            Properties properties = new Properties();
            properties.load(in);
            schedulerFactory = new StdSchedulerFactory(properties);
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobDetail checkJob = JobBuilder.newJob(SessionPoolCheckJob.class)
                    .withIdentity(new JobKey(SESSIONPOOL_CHECK_JOB, SESSIONPOOL_CHECK_GROUP))
                    .build();

            SimpleTrigger checkTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(new TriggerKey(SESSIONPOOL_CHECK_TIRGGER, SESSIONPOOL_CHECK_GROUP))
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInMilliseconds(interval).repeatForever())
                    .build();

            scheduler.deleteJob(checkJob.getKey());
            scheduler.scheduleJob(checkJob, checkTrigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean modelLoadRun(WebApplicationContext contextLoad) {
        SessionPoolCheckScheduler.getInstance().start(60 * 1000 * 150);  //每150分钟执行一次
        return true;
    }

}
