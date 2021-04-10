package com.orient.history.controller;

import com.orient.history.business.HisTaskBusiness;
import com.orient.history.core.binddata.model.HisTaskInfo;
import com.orient.history.core.request.*;
import com.orient.sysmodel.domain.his.CwmSysHisTaskEntity;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/hisTask")
public class HisTaskController extends BaseController {
    @Autowired
    HisTaskBusiness hisTaskBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmSysHisTaskEntity> list(Integer page, Integer limit, CwmSysHisTaskEntity filter) {
        return hisTaskBusiness.list(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CwmSysHisTaskEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        hisTaskBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmSysHisTaskEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        hisTaskBusiness.update(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        hisTaskBusiness.delete(toDelIds);
        return retVal;
    }


//    /**
//     * 保存历史审批信息
//     *
//     * @param frontViewRequest
//     */
//    @RequestMapping("/saveHisAuditTaskInfo")
//    @ResponseBody
//    public CommonResponseData saveHisAuditTaskInfo(@RequestBody AuditFrontViewRequest frontViewRequest) {
//        hisTaskBusiness.saveHisTaskInfo(frontViewRequest);
//        return new CommonResponseData();
//    }
    /**
     * 保存历史审批信息
     * String piId,String taskName,String taskId,String taskType,String auditType
     */
    @RequestMapping("/saveHisAuditTaskInfo")
    @ResponseBody
    public CommonResponseData saveHisAuditTaskInfo(@RequestBody RequestParam requestParam) {
        AuditFrontViewRequest frontViewRequest = new AuditFrontViewRequest();
        frontViewRequest.setPiId(requestParam.getPiId());
        frontViewRequest.setTaskId(requestParam.getTaskId());
        frontViewRequest.setTaskType(requestParam.getTaskType());
        frontViewRequest = hisTaskBusiness.buildFrontViewRequest(frontViewRequest,requestParam.getAuditType(),requestParam.getTaskName());
        hisTaskBusiness.saveHisTaskInfo(frontViewRequest);
        return new CommonResponseData();
    }

//    @RequestMapping("/saveHisCollabTaskInfo")
//    @ResponseBody
//    public AjaxResponseData<String> saveHisCollabTaskInfo(@RequestBody WorkFlowFrontViewRequest frontViewRequest) {
//        CwmSysHisTaskEntity hisTaskEntity = hisTaskBusiness.saveHisTaskInfo(frontViewRequest);
//        return new AjaxResponseData<>(hisTaskEntity.getId().toString());
//    }

    @RequestMapping("/getHisTaskInfo")
    @ResponseBody
    public AjaxResponseData<HisTaskInfo> getHisTaskInfo(String taskId, String taskType) {
        return new AjaxResponseData<>(hisTaskBusiness.getHisTaskInfo(taskId, taskType));
    }


    /**
     * 保存历史信息
     *
     * @param frontViewRequest
     */
    @RequestMapping("/saveHisTaskInfo")
    @ResponseBody
    public AjaxResponseData<String> saveHisTaskInfo(@RequestBody FrontViewRequest frontViewRequest) {
        CwmSysHisTaskEntity hisTaskEntity = hisTaskBusiness.saveHisTaskInfo(frontViewRequest);
        return new AjaxResponseData<>(hisTaskEntity.getId().toString());
    }

    /**
     * 保存计划历史信息
     *
     */
    @RequestMapping("/saveHisPlanTaskInfo")
    @ResponseBody
    public AjaxResponseData<String> saveHisPlanTaskInfo(@RequestBody PlanTaskParam param) {
        FrontViewRequest request = new FrontViewRequest();
        request.setTaskType(param.getTaskType());
        request.setTaskId(param.getTaskId());
        request = hisTaskBusiness.buildPlanTaskFrontViewRequest(request,param.getRootModelName(),param.getRootModelId(),param.getRootDataId());
        CwmSysHisTaskEntity hisTaskEntity = hisTaskBusiness.saveHisTaskInfo(request);
        return new AjaxResponseData<>(hisTaskEntity.getId().toString());
    }

    /**
     *保存协同历史信息
     */
    @RequestMapping("/saveHisCollabTaskInfo")
    @ResponseBody
    public AjaxResponseData<String> saveHisCollabTaskInfo(@RequestBody CollabTaskParam param) {
        WorkFlowFrontViewRequest request = new WorkFlowFrontViewRequest();
        request.setPiId(param.getPiId());
        request.setTaskId(param.getTaskId());
        request.setTaskType(param.getTaskType());
        request = hisTaskBusiness.buidCollabTaskFrontViewRequest(request,param.getRootModelName(),param.getRootModelId(),param.getRootDataId());
        CwmSysHisTaskEntity hisTaskEntity = hisTaskBusiness.saveHisTaskInfo(request);
        return new AjaxResponseData<>(hisTaskEntity.getId().toString());
    }

    /**
     * 保存数据任务历史信息
     */
    @RequestMapping("/saveHisDataTaskInfo")
    @ResponseBody
    public AjaxResponseData<String> saveHisDataTaskInfo(@RequestBody DataTaskParam param) {
        FrontViewRequest request = new FrontViewRequest();
        request.setTaskType(param.getTaskType());
        request.setTaskId(param.getTaskId());
        request = hisTaskBusiness.buildDataTaskFrontViewRequest(request,param.getModelName(),param.getDataId());
        CwmSysHisTaskEntity hisTaskEntity = hisTaskBusiness.saveHisTaskInfo(request);
        return new AjaxResponseData<>(hisTaskEntity.getId().toString());
    }

}
