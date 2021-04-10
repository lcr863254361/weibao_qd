package com.orient.alarm.schedule.job;

import com.orient.alarm.model.*;
import com.orient.alarm.schedule.notice.MailServiceImpl;
import com.orient.alarm.schedule.notice.SmsServiceImpl;
import com.orient.edm.init.OrientContextLoaderListener;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * 预警通知.
 *
 * @author [创建人] qjl <br/>
 *         [创建时间] 2014-11-4 下午3:40:20 <br/>
 *         [修改人] qjl <br/>
 *         [修改时间] 2014-11-4 下午3:40:20
 */
public class AlarmNoticeJob implements Job {

    public static final String ALARM_INFO = "ALARM_INFO";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            JobDataMap jobDataMap = context.getMergedJobDataMap();

            AlarmInfo alarm = (AlarmInfo) jobDataMap.get(ALARM_INFO);
            AlarmNotice notice = alarm.getAlarmNotice();
            if (notice.isMailNotice()) {
                //发送邮件
                MailServiceImpl mailService = (MailServiceImpl) OrientContextLoaderListener.Appwac.getBean("MailService");
                mailService.sendMultiPartMail(alarm);
            }
            if (notice.isMessageNotice()) {
                //发送短息
                SmsServiceImpl smsService = (SmsServiceImpl) OrientContextLoaderListener.Appwac.getBean("SmsService");
                smsService.sendMessage(alarm);
            }
            //预警通知保存
            saveAlarmHistory(alarm);
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }

    /**
     * 保存预警通知到历史表.
     *
     * @param alarm
     * @throws Exception
     * @author [创建人] qjl <br/>
     * [创建时间] 2014-11-6 下午1:56:01 <br/>
     * [修改人] qjl <br/>
     * [修改时间] 2014-11-6 下午1:56:01
     */
    @SuppressWarnings("unchecked")
    public void saveAlarmHistory(AlarmInfo alarm) throws Exception {
        AlarmContent alarmContent = alarm.getAlarmContent();
        AlarmInfoHist alarmHist = new AlarmInfoHist(alarm.getNlevel(), alarmContent.getTitle(),
                alarmContent.getContent(), alarmContent.getUrl(), new Date(), null);
        Map<AlarmUser, AlarmUserRelation> alarmUserRelation = alarm.getAlarmUserMap();
        for (Iterator<AlarmUser> iter = alarmUserRelation.keySet().iterator(); iter.hasNext(); ) {
            AlarmUser alarmUser = iter.next();
            alarmHist.getAlarmUserMap().put(alarmUser, alarmUserRelation.get(alarmUser));
        }
        AlarmInfoHistDAO alarmHistDAO = (AlarmInfoHistDAO) OrientContextLoaderListener.Appwac.getBean("AlarmInfoHistDAO");
        alarmHistDAO.save(alarmHist);
    }

}
