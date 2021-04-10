package com.orient.collab.business;

import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.Task;
import com.orient.sysmodel.domain.collab.CollabDataTask;
import com.orient.sysmodel.domain.collab.CollabDataTaskHis;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.service.collab.impl.CollabDataTaskHisService;
import com.orient.sysmodel.service.collab.impl.CollabDataTaskService;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.CommonResponseData;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mengbin on 16/8/23.
 * Purpose:
 * Detail:
 */
@Service
public class DataTaskBusiness extends BaseBusiness {


    @Autowired
    protected TeamBusiness teamBusiness;

    @Autowired
    protected CollabDataTaskService collabDataTaskService;

    @Autowired
    private CollabDataTaskHisService collabDataTaskHisService;


    /**
     * 获取个人的当前数据任务
     *
     * @param userId
     * @return
     */
    public List<CollabDataTask> getCurrentTask(String userId) {

        List<Criterion> filters = new ArrayList<>();
        filters.add(Restrictions.eq("userid", userId));
        filters.add(Restrictions.ne("taskstate", CollabDataTask.STATS_COMPLETED));
        return collabDataTaskService.list(filters.toArray(new Criterion[0]));
    }


    /**
     * 根据TaskId,获取当前的数据任务
     *
     * @param taskId
     * @return
     */
    public List<CollabDataTask> getCurCollabTaskByTaskId(String taskId) {

        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        List<Criterion> filters = new ArrayList<>();

        filters.add(Restrictions.eq("taskmodelid", Long.valueOf(taskModelId)));
        filters.add(Restrictions.eq("dataid", Long.valueOf(taskId)));
        filters.add(Restrictions.ne("taskstate", CollabDataTask.STATS_COMPLETED));
        return collabDataTaskService.list(filters.toArray(new Criterion[0]));
    }


    /**
     * 删除当前的数据任务
     *
     * @param taskId
     * @return
     */
    public boolean deleteCurCollabTaskByTaskId(String taskId) {
        List<CollabDataTask> collabDataTasks = this.getCurCollabTaskByTaskId(taskId);
        for (CollabDataTask dataTask : collabDataTasks) {
            collabDataTaskService.delete(dataTask);
        }
        return true;

    }

    /**
     * 领取任务:修改任务状态,删除其它通知的任务
     *
     * @param task
     * @return
     */
    public CommonResponseData takeTask(CollabDataTask task) {
        CommonResponseData responseObject = new CommonResponseData(true, "数据任务领取成功");

        List<Criterion> filters = new ArrayList<>();

        filters.add(Restrictions.eq("taskmodelid", task.getTaskmodelid()));
        filters.add(Restrictions.eq("dataid", task.getDataid()));
        List<CollabDataTask> familyTasks = collabDataTaskService.list(filters.toArray(new Criterion[0]));
        for (CollabDataTask loopDataTask : familyTasks) {

            if (loopDataTask.getId().equals(task.getId())) {
                loopDataTask.setTaskstate(CollabDataTask.STATS_ACCEPTED);
                loopDataTask.setTaketime(new Date());
                collabDataTaskService.update(loopDataTask);
            } else {
                collabDataTaskService.delete(loopDataTask);
            }

        }
        return responseObject;
    }


    /**
     * 创建任务:根据任务中的执行人角色,创建数据任务
     *
     * @param task
     * @return
     */
    public CommonResponseData createDataTask(Task task) {

        CommonResponseData responseObject = new CommonResponseData(false, "");
        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        List<CollabDataTask> dataTasks = getCurCollabTaskByTaskId(task.getId());
        if (dataTasks.size() != 0) {
            //已经存在该task的数据任务,数据能够合并处理
            responseObject.setSuccess(true);
        }

        List<CollabRole> roles = teamBusiness.getRoles(CollabConstants.TASK, task.getId());
        for (CollabRole role : roles) {

            if (role.getName().equals(CollabConstants.ROLE_EXECUTOR)) {
                List<CollabRole.User> roleUsers = teamBusiness.getRoleUsers(String.valueOf(role.getId()));
                for (CollabRole.User user : roleUsers) {
                    CollabDataTask dataTask = new CollabDataTask();
                    dataTask.setCreatetime(new Date());
                    dataTask.setDataid(Long.valueOf(task.getId()));
                    dataTask.setTaskmodelid(Long.valueOf(taskModelId));
                    dataTask.setUserid(user.getId());
                    dataTask.setTaskstate(CollabDataTask.STATS_NOTIFY);
                    collabDataTaskService.save(dataTask);
                }
            }
        }
        responseObject.setSuccess(true);
        return responseObject;
    }

    /**
     * 完成任务,设置状态,拷贝只历史表
     *
     * @param task
     * @return
     */
    public CommonResponseData completeDataTask(CollabDataTask task) {

        CommonResponseData responseObject = new CommonResponseData(true, "");

        task.setTaskstate(CollabDataTask.STATS_COMPLETED);
        task.setFinishtime(new Date());
        CollabDataTaskHis hisDataTask = new CollabDataTaskHis();
        try {
            PropertyUtils.copyProperties(hisDataTask, task);
            collabDataTaskHisService.save(hisDataTask);
            collabDataTaskService.delete(task);
        } catch (Exception e) {
            responseObject.setSuccess(false);

        }
        return responseObject;
    }

    public List<CollabDataTaskHis> getHisDataTasks(String userId, Date startDate, Date endDate) {

        List<Criterion> filters = new ArrayList<>();

        filters.add(Restrictions.eq("userid", userId));
        filters.add(Restrictions.between("createtime", startDate, endDate));
        filters.add(Restrictions.between("finishtime", startDate, endDate));
        Order orders[] = new Order[1];
        orders[0] = Order.desc("finishtime");
        return collabDataTaskHisService.list(filters.toArray(new Criterion[0]), orders);

    }


    public CollabDataTask getTaskDataById(String id) {

        return collabDataTaskService.getById(id);

    }

    /**
     * delete history data task from collab task id
     *
     * @param taskId collab task id
     */
    public void deleteHisDataTaskByTaskId(String taskId) {
        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        List<Criterion> filters = new ArrayList<>();
        filters.add(Restrictions.eq("taskmodelid", Long.valueOf(taskModelId)));
        filters.add(Restrictions.eq("dataid", Long.valueOf(taskId)));
        List<CollabDataTaskHis> collabDataTaskHises = collabDataTaskHisService.list(filters.toArray(new Criterion[0]));
        collabDataTaskHises.forEach(collabDataTaskHis -> collabDataTaskHisService.delete(collabDataTaskHis));
    }
}
