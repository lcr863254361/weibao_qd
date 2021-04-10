package com.orient.collab.controller;

import com.orient.collab.business.DataTaskBusiness;
import com.orient.collab.business.PlanTaskProcessBusiness;
import com.orient.collab.model.Task;
import com.orient.devdataobj.business.DataObjectBusiness;
import com.orient.devdataobj.event.DataObjModifiedEvent;
import com.orient.devdataobj.event.param.DataObjModifiedParam;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collab.CollabDataTask;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * the plan and task process controller
 * i.e. the flow which consists of tasks
 *
 * @author Seraph
 * 2016-07-26 下午8:33
 */
@Controller
public class PlanTaskProcessController extends BaseController {

    @RequestMapping("/{modelName}/start")
    @ResponseBody
    public CommonResponseData start(@PathVariable("modelName") String modelName, String dataId) {
        return this.planTaskProcessBusiness.start(modelName, dataId);
    }

    @RequestMapping("/cb_plan/submit")
    @ResponseBody
    public CommonResponseData submitPlan(String dataId) {
        return this.planTaskProcessBusiness.submitPlan(dataId);
    }

    @RequestMapping("/cb_task/submit")
    @ResponseBody
    public CommonResponseData submitTask(String dataId, String transitionName) {
        return this.planTaskProcessBusiness.submitTask(dataId, transitionName);
    }


    /**
     * 数据提交任务使用
     *
     * @param modelId
     * @param dataId
     * @return
     */
    @RequestMapping("dataTask/submit")
    @ResponseBody
    public CommonResponseData submitDataTask(String modelId, String dataId) {
        //todo 代码待修改
        /*List<DataObjectEntity> dataObjectEntities = dataObjectBusiness.getAllCurrentDataObject(modelId, dataId, 1, true);
        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        Task orientTask = orientSqlEngine.getTypeMappingBmService().getById(Task.class, dataId);
        if(null == orientTask){
           throw new OrientBaseAjaxException("","只有任务节点数据可以提交");
        }
        String parentTaskId = orientTask.getParTaskId();
        String parentPlanId = orientTask.getParPlanId();
        if (!StringUtil.isEmpty(parentTaskId)) {
            List<DataObjectEntity> objs = dataObjectBusiness.getAllCurrentDataObject(taskModelId, parentTaskId, 1, true);
            for (DataObjectEntity loopObj : objs) {
                if (loopObj.getModifierid().equals(UserContextUtil.getCurrentUser().getId())) {
                    dataObjectEntities.add(loopObj);//只有自己修改的才需要提交
                }
            }
        }
        for (DataObjectEntity loop : dataObjectEntities) {
            dataObjectBusiness.dataTaskSubmitUpVersion(loop);
        }
        //提交当前的数据任务
        List<CollabDataTask> dataTasks = dataTaskBusiness.getCurCollabTaskByTaskId(dataId);
        assert (dataTasks.size() <= 1);
        for (CollabDataTask dataTask : dataTasks) {
            dataTaskBusiness.completeDataTask(dataTask);
        }


        //发起数据提交后的事件
        DataObjModifiedParam eventParam = new DataObjModifiedParam();
        eventParam.modifiedDataObjList.addAll(dataObjectEntities);
        OrientContextLoaderListener.Appwac.publishEvent(new DataObjModifiedEvent(this, eventParam));
        if (eventParam.isbAboard()) {
            return new CommonResponseData(false, eventParam.errorMsg);
        } else {
            return new CommonResponseData(true, "数据提交成功");
        }*/
        return null;

    }


    /**
     * @param dataTaskId
     * @return
     */
    @RequestMapping("dataTask/take")
    @ResponseBody
    public CommonResponseData takeDataTask(String dataTaskId, String modelId, String dataId) {

        CollabDataTask dataTask = dataTaskBusiness.getTaskDataById(dataTaskId);
        if (dataTask == null) {
            return new CommonResponseData(false, "该数据任务已被其他人领取");
        }
        return dataTaskBusiness.takeTask(dataTask);
    }

    @RequestMapping("/{modelName}/suspend")
    @ResponseBody
    public CommonResponseData suspend(@PathVariable("modelName") String modelName, String dataId) {
        return this.planTaskProcessBusiness.suspend(modelName, dataId);

    }

    @RequestMapping("/{modelName}/resume")
    @ResponseBody
    public CommonResponseData resume(@PathVariable("modelName") String modelName, String dataId) {

        return this.planTaskProcessBusiness.resume(modelName, dataId);
    }

    @RequestMapping("/{modelName}/close")
    @ResponseBody
    public CommonResponseData close(@PathVariable("modelName") String modelName, String dataId) {
        return this.planTaskProcessBusiness.close(modelName, dataId);
    }

    @RequestMapping("/collabCounterSign/assignee/set")
    @ResponseBody
    public CommonResponseData updateCounterSignTaskAssignee(String piId, String assignee, String assigneeIds, String parModelName, String parDataId, String taskName) {
        return this.planTaskProcessBusiness.updateCounterSignTaskAssignee(piId, assignee, assigneeIds, parModelName, parDataId, taskName);
    }

    @Autowired
    private PlanTaskProcessBusiness planTaskProcessBusiness;

    @Autowired
    private DataObjectBusiness dataObjectBusiness;

    @Autowired
    private DataTaskBusiness dataTaskBusiness;

    @Autowired
    private ISqlEngine orientSqlEngine;
}

