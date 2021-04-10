package com.orient.sysman.bussiness;

import com.orient.sysman.scheduler.BackUpJob;
import com.orient.sysman.scheduler.BackUpScheduler;
import com.orient.sysmodel.domain.sys.QuartzJobEntity;
import com.orient.sysmodel.service.sys.IBackUpJobService;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseHibernateBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * QuartzJobEntity
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class BackUpJobBusiness extends BaseHibernateBusiness<QuartzJobEntity> {

    @Autowired
    IBackUpJobService backUpJobService;

    @Autowired
    BackUpScheduler backUpScheduler;

    @Override
    public IBackUpJobService getBaseService() {
        return backUpJobService;
    }

    public List<QuartzJobEntity> listAll() {
        return backUpJobService.list();
    }

    public void delete(Long[] toDelIds) {
        for (Long toDelId : toDelIds) {
            backUpScheduler.removeJob(toDelId.toString());
        }
        backUpJobService.delete(toDelIds);
    }

    public void save(QuartzJobEntity formValue) {
        //启动定时任务
        String conExpr = extraConExpr(formValue);
        //进行中
        formValue.setBacktype("1");
        backUpJobService.save(formValue);
        //增加job
        backUpScheduler.updateJob(BackUpJob.class, formValue.getId().toString(), conExpr);
    }

    public String extraConExpr(QuartzJobEntity formValue) {
        String retVal = "";
        String[] times;
        if (!StringUtil.isEmpty(formValue.getIsdayback())) {
            times = formValue.getDaybacktime().split(":");
            retVal = times[2] + " " + times[1] + " " + times[0] + " * * ?";
        } else if (!StringUtil.isEmpty(formValue.getIsweekback())) {
            times = formValue.getWeekbacktime().split(":");
            retVal = times[2] + " " + times[1] + " " + times[0] + " ? * " + (Integer.parseInt(formValue.getWeekbackday()) % 7 + 1);
        } else if (!StringUtil.isEmpty(formValue.getIsmonthback())) {
            times = formValue.getMonthbacktime().split(":");
            retVal = times[2] + " " + times[1] + " " + times[0] + " " + formValue.getMonthbackday() + " * ?";
        }
        return retVal;
    }

    /**
     * @param jobIds
     * @param status 改变定时备份任务状态
     */
    public void saveJobStatus(Long[] jobIds, String status) {
        for (Long jobId : jobIds) {
            QuartzJobEntity quartzJobEntity = backUpJobService.getById(jobId);
            quartzJobEntity.setBacktype(status);
            backUpJobService.update(quartzJobEntity);
            if ("1".equals(status)) {
                backUpScheduler.resumeJob(jobId.toString());
            } else if ("2".equals(status)) {
                backUpScheduler.pauseJob(jobId.toString());
            }
        }
    }
}
