package com.orient.collab.controller;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.business.DataTaskBusiness;
import com.orient.collab.business.MyTaskBusiness;
import com.orient.collab.business.strategy.ProjectTreeNodeStrategy;
import com.orient.collab.model.*;
import com.orient.devdataobj.bean.DataObjectBean;
import com.orient.devdataobj.business.DataObjectBusiness;
import com.orient.devdataobj.event.GetDataObjEvent;
import com.orient.devdataobj.event.param.GetDataObjParam;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.utils.PageUtil;
import com.orient.web.base.BaseController;
import com.orient.web.base.ExtGridData;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collab.config.CollabConstants.PROJECT;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * the controller of my task
 *
 * @author Seraph
 *         2016-07-25 上午11:01
 */
@Controller
@RequestMapping("/myTask")
public class MyTaskController extends BaseController {

    @RequestMapping("/plans/currentUser")
    @ResponseBody
    public ExtGridData<PlanViewModel> getMyPlans(Integer page, Integer limit, @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, String planName) {
        List<Plan> allPlans = this.myTaskBusiness.getMyPlans(UserContextUtil.getUserId(), startDate, endDate);
        List<Plan> plans = PageUtil.page(allPlans, page, limit);

        IBusinessModel projectBm = this.businessModelService.getBusinessModelBySName(PROJECT, COLLAB_SCHEMA_ID, Table);
        String projectNameCol = "NAME_" + projectBm.getId();

        List<PlanViewModel> planViewModels = plans.stream().map(plan -> {
            PlanViewModel planViewModel = new PlanViewModel();
            try {
                BeanUtils.copyProperties(planViewModel, plan);
                planViewModel.setBelongedProject(ProjectTreeNodeStrategy.PlanNode.getParentRouteInfo(null, plan).pop().get(projectNameCol));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return planViewModel;
        }).collect(Collectors.toList());

        ExtGridData<PlanViewModel> retV = new ExtGridData<>();
        retV.setResults(planViewModels);
        retV.setTotalProperty(allPlans.size());

        return retV;
    }

    @RequestMapping("/collabTasks/currentUser")
    @ResponseBody
    public ExtGridData<CollabFlowTask> getMyCollabTasks(Integer page, Integer limit, @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, String taskName) {

        return this.myTaskBusiness.getMyCollabTasks(UserContextUtil.getUserName(), page, limit, startDate, endDate, taskName);
    }

    @RequestMapping("/dataTasks/currentUser")
    @ResponseBody
    public ExtGridData<DataTaskBean> getMyDataTasks(Integer page, Integer limit, @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                    @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, String taskName) {
        return this.myTaskBusiness.getMyDataTasks(UserContextUtil.getUserId(), page, limit, startDate, endDate, taskName);
    }


   /* *//**
     * 获取当前的节点前驱的研发数据对象(如果是Plan,则获取前驱,如果是Task 则获取数据流的DataObjBean)
     *
     * @param modelId
     * @param dataId
     * @return
     *//*
    @RequestMapping("/dataTasks/preDataObjs")
    @ResponseBody
    public ExtGridData<DataObjectBean> getRefDataObj(String nodeId, String nodeVersion, String node) {
        ExtGridData<DataObjectBean> ret = new ExtGridData<DataObjectBean>();
        List<DataObjectBean> allObjs = new ArrayList<>();
        if ("root".equals(node)) {
            List<ProjectTreeNode> jobs = myTaskBusiness.getPreJob(nodeId, nodeVersion);
            for (ProjectTreeNode job : jobs) {
                GetDataObjParam eventParam = new GetDataObjParam();
                eventParam.modelId = modelId;
                eventParam.dataId = job.getDataId();
                eventParam.globalFlag = 1;
                eventParam.isOnlyRoot = true;
                OrientContextLoaderListener.Appwac.publishEvent(new GetDataObjEvent(this, eventParam));
                if (eventParam.isbAboard() == false) {
                    allObjs.addAll(eventParam.retDataObjs);
                }
            }
        } else {
            DataObjectEntity parentDataObj = dataObjectBusiness.getDataObjEntityById(Integer.valueOf(node));
            List<DataObjectEntity> objs = dataObjectBusiness.getChildrenDataObj(parentDataObj);
            try {
                allObjs = dataObjectBusiness.dataChange(objs);
            } catch (Exception e) {
                throw new OrientBaseAjaxException("", e.toString());
            }
        }
        ret.setSuccess(true);
        ret.setTotalProperty(allObjs.size());
        ret.setResults(allObjs);
        return ret;

    }
*/
    @RequestMapping("/dataTasks/getPreTaskDesc")
    @ResponseBody
    public ExtGridData<ProjectTreeNode> getPreTaskDesc(String modelId, String dataId, String node) {
        ExtGridData<ProjectTreeNode> ret = new ExtGridData<>();
        if ("root".equals(node)) {
            List<ProjectTreeNode> jobs = myTaskBusiness.getPreJob(modelId, dataId);
            ret.setSuccess(true);
            ret.setTotalProperty(jobs.size());
            ret.setResults(jobs);
        }
        return ret;

    }

    @RequestMapping("/historyTasks/currentUser")
    @ResponseBody
    public ExtGridData<OrientHistoryTask> getMyHistoryTasks(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, String name, String groupType, Integer page, Integer limit) {
        List<OrientHistoryTask> myHistoryTasks = this.myTaskBusiness.getMyHistoryTasks(UserContextUtil.getUserId(), UserContextUtil.getUserName(), startDate, endDate, name, groupType);
        List<OrientHistoryTask> retList = PageUtil.page(myHistoryTasks, page, limit);
        ExtGridData<OrientHistoryTask> retV = new ExtGridData<>();
        retV.setSuccess(true);
        retV.setResults(retList);
        retV.setTotalProperty(myHistoryTasks.size());
        return retV;
    }

    @Autowired
    private MyTaskBusiness myTaskBusiness;

    @Autowired
    private DataTaskBusiness taskBusiness;

    @Autowired
    private DataObjectBusiness dataObjectBusiness;

    @Autowired
    protected IBusinessModelService businessModelService;
}
