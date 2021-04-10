package com.orient.history.core.util;

import com.orient.collab.model.Plan;
import com.orient.history.core.binddata.model.HisTaskInfo;
import com.orient.sysmodel.domain.collab.CollabDataTaskHis;
import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/26 0026.
 * 单例模式
 */
public class HisTaskHelper {

    private HisTaskHelper() {
    }



    private static class HisTaskHelperInnerClass {
        private static HisTaskHelper hisTaskHelper = new HisTaskHelper();
    }

    public static HisTaskHelper getInstance() {
        return HisTaskHelperInnerClass.hisTaskHelper;
    }

    public void initPlanTaskBaseInfo(Plan plan, HisTaskInfo taskInfo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        taskInfo.setTaskId(plan.getId());
        taskInfo.setTaskType(HisTaskTypeConstants.PLAN_TASK);
        taskInfo.setTaskName(plan.getName());
        taskInfo.setTaskAssigner(plan.getPrincipal());
        taskInfo.setTaskBeginTime(plan.getActualStartDate() != null ? plan.getActualStartDate() : plan.getPlannedStartDate());
        taskInfo.setTaskEndTime(simpleDateFormat.format(new Date()));
    }

    public void initDataTaskBaseInfo(com.orient.collab.model.Task task, HisTaskInfo taskInfo, CollabDataTaskHis collabDataTaskHis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        taskInfo.setTaskId(collabDataTaskHis.getId());
        taskInfo.setTaskName(task.getName());
        taskInfo.setTaskAssigner(task.getPrincipal());
        taskInfo.setTaskType(HisTaskTypeConstants.DATA_TASK);
        taskInfo.setTaskBeginTime(simpleDateFormat.format(collabDataTaskHis.getCreatetime()));
        taskInfo.setTaskEndTime(simpleDateFormat.format(new Date()));
    }

    /**
     * 根据流程任务信息 初始化待保存历史任务基本信息
     *
     * @param task     流程任务
     * @param taskInfo 待保存的历史任务
     */
    public void initWorkFlowTaskBaseInfo(HistoryTaskImpl task, HisTaskInfo taskInfo, String taskType, String taskName) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        taskInfo.setTaskId(task.getId());
        taskInfo.setTaskName(taskName);
        taskInfo.setTaskAssigner(task.getAssignee());
        taskInfo.setTaskType(taskType);
        taskInfo.setTaskBeginTime(simpleDateFormat.format(task.getCreateTime()));
        taskInfo.setTaskEndTime(simpleDateFormat.format(new Date()));
    }

    public byte[] SerializeHisTask(HisTaskInfo hisTaskInfo) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(hisTaskInfo);
            objectOutputStream.flush();
            byte[] bytes = outputStream.toByteArray();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public HisTaskInfo DerializeHisTask(byte[] bytes) {

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (HisTaskInfo) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
