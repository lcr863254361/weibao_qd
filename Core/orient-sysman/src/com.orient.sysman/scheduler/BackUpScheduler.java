/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.sysman.scheduler;

import com.orient.edm.init.IContextLoadRun;
import com.orient.sysman.bussiness.BackUpJobBusiness;
import com.orient.sysmodel.domain.sys.QuartzJobEntity;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Singleton;
import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

/**
 * 定时备份调度服务
 */
@Component("backUpScheduler")
@Singleton
public class BackUpScheduler implements IContextLoadRun {

    @Autowired
    BackUpJobBusiness backUpJobBusiness;

    Logger logger = Logger.getLogger(BackUpScheduler.class);

    private SchedulerFactory schedulerFactory;

    private final static String BACKUP_SCHEDULER = "BACKUP_SCHEDULER";

    private final static String BACKUP_GROUP = "BACKUP_CHECK_GROUP";

    public BackUpScheduler() {
        try {
            String properties = this.getClass().getResource("/").toURI().getPath() + File.separator + "BackUpQuartz.properties";
            schedulerFactory = new StdSchedulerFactory(properties);
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
            //初始化定时任务
            List<QuartzJobEntity> jobs = backUpJobBusiness.listAll();
            jobs.forEach(job -> {
                String cronExpression = backUpJobBusiness.extraConExpr(job);
                updateJob(BackUpJob.class, job.getId().toString(), cronExpression);
            });
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        logger.warn("定时备份调度服务启动成功...");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean modelLoadRun(WebApplicationContext contextLoad) {
        this.start();
        return true;
    }


    public void updateJob(Class jobClass, String jobName, String cronExpression) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler(BACKUP_SCHEDULER);
            //获取触发器标识
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, BACKUP_GROUP);
            //获取触发器trigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            if (null == trigger) {//不存在任务
                //创建任务
                JobDetail jobDetail = JobBuilder.newJob(jobClass)
                        .withIdentity(jobName, BACKUP_GROUP)
                        .build();
                scheduler.getContext().put(jobName, jobName);
                //按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(jobName, BACKUP_GROUP)
                        .withSchedule(scheduleBuilder)
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                trigger = trigger.getTriggerBuilder()
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder)
                        .build();
                //按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }
            logger.warn("定时任务---" + jobName + " 已设定");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    public void pauseJob(String jobName) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler(BACKUP_SCHEDULER);
            JobKey jobKey = JobKey.jobKey(jobName, BACKUP_GROUP);
            scheduler.pauseJob(jobKey);
            logger.warn("定时任务---" + jobName + " 已暂停");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void resumeJob(String jobName) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler(BACKUP_SCHEDULER);
            JobKey jobKey = JobKey.jobKey(jobName, BACKUP_GROUP);
            scheduler.resumeJob(jobKey);
            logger.warn("定时任务---" + jobName + " 已恢复");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void removeJob(String jobName) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler(BACKUP_SCHEDULER);
            JobKey jobKey = JobKey.jobKey(jobName, BACKUP_GROUP);
            scheduler.deleteJob(jobKey);
            scheduler.getContext().remove(jobName);
            logger.warn("定时任务---" + jobName + " 已删除");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
