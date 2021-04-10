package com.orient.pvm.controller;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.pvm.bean.CheckModelTreeNode;
import com.orient.pvm.business.CheckModelBusiness;
import com.orient.pvm.event.DeleteCheckModelEvent;
import com.orient.pvm.event.TaskBindCheckModelEvent;
import com.orient.pvm.eventparam.DeleteCheckModelEventParam;
import com.orient.pvm.eventparam.TaskBindCheckModelEventParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.pvm.*;
import com.orient.sysmodel.service.pvm.IPVMMulCheckRelationService;
import com.orient.sysmodel.service.pvm.impl.CheckTaskHtmlTemplateService;
import com.orient.sysmodel.service.pvm.impl.TaskCheckModelService;
import com.orient.sysmodel.service.pvm.impl.TaskCheckRelationService;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0  离线数据相关
 */
@Controller
@RequestMapping("/CheckModel")
public class CheckModelController extends ModelDataController {

    @Autowired
    CheckModelBusiness checkModelBusiness;

    @Autowired
    IPVMMulCheckRelationService pVMMulCheckRelationService;

    @Autowired
    private CheckTaskHtmlTemplateService checkTaskHtmlTemplateService;

    @Autowired
    private TaskCheckModelService taskCheckModelService;

    @Autowired
    protected IBusinessModelService businessModelService;

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    private TaskCheckRelationService taskCheckRelationService;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<TaskCheckModel> list(Integer page, Integer limit, TaskCheckModel filter) {
        return checkModelBusiness.list(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(TaskCheckModel formValue) {
        CommonResponseData retVal = new CommonResponseData();
        TaskBindCheckModelEventParam eventParam = new TaskBindCheckModelEventParam(formValue.getNodeId(),
                formValue.getCheckmodelid().toString(), formValue.getHtml(), "", TaskCheckModel.STATUS_EDIT);
        TaskBindCheckModelEvent event = new TaskBindCheckModelEvent(this, eventParam);
        OrientContextLoaderListener.Appwac.publishEvent(event);
        retVal.setMsg(StringUtil.isEmpty(eventParam.errorMsg) ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("createByTemplate")
    @ResponseBody
    public CommonResponseData createByTemplate(TaskCheckModel formValue, Long[] templateIds) {
        CommonResponseData retVal = new CommonResponseData();
        //获取模型ID集合
        List<CheckModelDataTemplate> checkModelDataTemplates = checkModelBusiness.getByIds(templateIds);
        List<String> errorMsgs = new ArrayList<>();
        checkModelDataTemplates.forEach(checkModelDataTemplate -> {
            Long checkModelId = checkModelDataTemplate.getCheckmodelid();
            TaskCheckModel taskCheckModel = new TaskCheckModel();
            BeanUtils.copyProperties(taskCheckModel, formValue);
            taskCheckModel.setCheckmodelid(checkModelId);
            TaskBindCheckModelEventParam eventParam = new TaskBindCheckModelEventParam(taskCheckModel.getNodeId(),
                    taskCheckModel.getCheckmodelid().toString(), formValue.getHtml(), "", TaskCheckModel.STATUS_EDIT);
            eventParam.setCheckModelDataTemplate(checkModelDataTemplate);
            TaskBindCheckModelEvent event = new TaskBindCheckModelEvent(this, eventParam);
            OrientContextLoaderListener.Appwac.publishEvent(event);
            String errorMsg = eventParam.errorMsg;
            if (!StringUtil.isEmpty(errorMsg)) {
                errorMsgs.add(errorMsg);
            }
        });
        if (errorMsgs.size() > 0) {
            throw new OrientBaseAjaxException("", com.orient.utils.CommonTools.list2String(errorMsgs, "</br>"));
        } else {
            retVal.setMsg("保存成功");
        }
        return retVal;
    }

    @RequestMapping("createByHtmlTemplate")
    @ResponseBody
    public CommonResponseData createByHtmlTemplate(TaskCheckModel formValue, Long[] htmlTemplateIds) {
        CommonResponseData retVal = new CommonResponseData();
        //获取模型ID集合
        List<CwmTaskcheckHtmlEntity> htmlEntities = checkModelBusiness.getHtmlTemplatesByIds(htmlTemplateIds);
        List<String> errorMsgs = new ArrayList<>();
        htmlEntities.forEach(CwmTaskcheckHtmlEntity -> {
            TaskCheckModel taskCheckModel = new TaskCheckModel();
            BeanUtils.copyProperties(taskCheckModel, formValue);
            TaskBindCheckModelEventParam eventParam = new TaskBindCheckModelEventParam(taskCheckModel.getNodeId(),
                    CommonTools.Obj2String(taskCheckModel.getCheckmodelid()), CwmTaskcheckHtmlEntity.getHtml(), CwmTaskcheckHtmlEntity.getName(), TaskCheckModel.STATUS_EDIT);
            TaskBindCheckModelEvent event = new TaskBindCheckModelEvent(this, eventParam);
            OrientContextLoaderListener.Appwac.publishEvent(event);
            String errorMsg = eventParam.errorMsg;
            if (!StringUtil.isEmpty(errorMsg)) {
                errorMsgs.add(errorMsg);
            }
        });
        if (errorMsgs.size() > 0) {
            throw new OrientBaseAjaxException("", com.orient.utils.CommonTools.list2String(errorMsgs, "</br>"));
        } else {
            retVal.setMsg("保存成功");
        }
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(TaskCheckModel formValue) {
        CommonResponseData retVal = new CommonResponseData();
        checkModelBusiness.update(formValue);
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
        DeleteCheckModelEventParam eventParam = new DeleteCheckModelEventParam(toDelIds);
        DeleteCheckModelEvent event = new DeleteCheckModelEvent(this, eventParam);
        OrientContextLoaderListener.Appwac.publishEvent(event);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("getByPid")
    @ResponseBody
    public AjaxResponseData<List<CheckModelTreeNode>> getByPid(String node, String nodeId) {
        List<CheckModelTreeNode> checkModelTreeNodes = checkModelBusiness.getBindCheckModelNodes(node, nodeId);
        return new AjaxResponseData<>(checkModelTreeNodes);
    }

    @RequestMapping("canAdd")
    @ResponseBody
    public AjaxResponseData<String> checkCanAdd(String checkmodelid, String nodeId) {
        String retVal = checkModelBusiness.canAdd(checkmodelid, nodeId);
        AjaxResponseData<String> mvcResp = new AjaxResponseData<>(retVal);
        mvcResp.setMsg(retVal.equals("true") ? "" : retVal);
        return mvcResp;
    }

    @RequestMapping("canAddFromTemplate")
    @ResponseBody
    public AjaxResponseData<Boolean> canAddFromTemplate(Long[] templateIds, String nodeId) {
        String errInfo = checkModelBusiness.canAddFromTemplate(templateIds, nodeId);
        Boolean canAdd = StringUtil.isEmpty(errInfo);
        AjaxResponseData<Boolean> mvcResp = new AjaxResponseData<>(canAdd);
        mvcResp.setMsg(StringUtil.isEmpty(errInfo) ? "" : errInfo);
        mvcResp.setSuccess(canAdd);
        return mvcResp;
    }

    @RequestMapping("canAddFromMulTemplate")
    @ResponseBody
    public AjaxResponseData<Boolean> canAddFromMulTemplate(Long[] mulTemplateIds, String nodeId) {
        String errInfo = checkModelBusiness.canAddFromMulTemplate(mulTemplateIds, nodeId);
        Boolean canAdd = (errInfo.length() <= 1);//StringUtil.isEmpty(errInfo);
        AjaxResponseData<Boolean> mvcResp = new AjaxResponseData<>(canAdd);
        mvcResp.setMsg(StringUtil.isEmpty(errInfo) ? "" : errInfo);
        mvcResp.setSuccess(canAdd);
        return mvcResp;
    }

    @RequestMapping("createByMulTemplate")
    @ResponseBody
    public CommonResponseData createByMulTemplate(String nodeId, Long[] mulTemplateIds) {
        CommonResponseData retVal = new CommonResponseData();
        for (Long templateId : mulTemplateIds) {
            //添加html模板数据
            List<Long> htmlTmpIds = new ArrayList<>();
            List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.isNull("checkmodelid"), Restrictions.eq("templateid", templateId.toString()));
            entities.forEach(entity -> {
                List<CwmTaskcheckHtmlEntity> htmlEntities = checkTaskHtmlTemplateService.list();
                for (CwmTaskcheckHtmlEntity htmlEntity : htmlEntities) {
                    if (entity.getHtml().equals(htmlEntity.getHtml())) {
                        htmlTmpIds.add(htmlEntity.getId());
                    }
                }
            });
            Long[] htmlTemplateIds = htmlTmpIds.toArray(new Long[htmlTmpIds.size()]);
            TaskCheckModel taskCheckModel = new TaskCheckModel();
            taskCheckModel.setNodeId(nodeId);
            createByHtmlTemplate(taskCheckModel, htmlTemplateIds);

            //添加模型数据
            entities = pVMMulCheckRelationService.list(Restrictions.isNotNull("checkmodelid"), Restrictions.eq("templateid", templateId.toString()));
            entities.forEach(entity -> {
                String checkModelId = entity.getCheckmodelid();
                TaskCheckModel tskCheckModel = checkModelBusiness.bindCheckModel(nodeId, checkModelId, TaskCheckModel.STATUS_EDIT,
                        "", "");
                tskCheckModel.setSignroles(entity.getSignroles());
                tskCheckModel.setRemark(entity.getRemark());
                taskCheckModelService.update(tskCheckModel);

                IBusinessModel checkModel = businessModelService.getBusinessModelById(checkModelId, EnumInter.BusinessModelEnum.Table);
                String modelDataStr = entity.getModeldata();
                List<Map> modelData = new ArrayList<>();
                if (!StringUtil.isEmpty(modelDataStr)) {
                    modelData = JsonUtil.json2List(modelDataStr);
                    for (Map data : modelData) {
                        TaskCheckRelation taskCheckRelation = new TaskCheckRelation();
                        data.remove("ID");//去除ID
                        String dataId = orientSqlEngine.getBmService().insertModelData(checkModel, data);
                        taskCheckRelation.setCheckdataid(Long.valueOf(dataId));
                        taskCheckRelation.setCheckmodelid(Long.valueOf(checkModelId));
                        taskCheckRelation.setNodeId(nodeId);
                        taskCheckRelationService.save(taskCheckRelation);
                    }
                }
            });
        }

        retVal.setMsg("保存成功");
        return retVal;
    }

    @RequestMapping("canAddFromHtmlTemplate")
    @ResponseBody
    public AjaxResponseData<Boolean> canAddFromHtmlTemplate(Long[] htmlTemplateIds, String nodeId) {
        String errInfo = checkModelBusiness.canAddFromHtmlTemplate(htmlTemplateIds, nodeId);
        Boolean canAdd = StringUtil.isEmpty(errInfo);
        AjaxResponseData<Boolean> mvcResp = new AjaxResponseData<>(canAdd);
        mvcResp.setMsg(StringUtil.isEmpty(errInfo) ? "" : errInfo);
        mvcResp.setSuccess(canAdd);
        return mvcResp;
    }

    @RequestMapping("getBusinessModelDesc")
    @ResponseBody
    public AjaxResponseData<Map<String, Object>> getBusinessModelDesc(Long taskCheckModelId, Integer status) {
        Map<String, Object> retVal = checkModelBusiness.getBusinessModelDesc(taskCheckModelId, status);
        return new AjaxResponseData<>(retVal);
    }

    @RequestMapping("createRelationDatas")
    @ResponseBody
    public AjaxResponseData createRelationDatas(Long taskCheckModelId, String toBindDataIds) {
        AjaxResponseData<String> retVal = new AjaxResponseData<>();
        String saveResult = checkModelBusiness.createRelationDatas(taskCheckModelId, toBindDataIds);
        retVal.setSuccess(StringUtil.isEmpty(saveResult));
        retVal.setMsg(saveResult);
        return retVal;
    }

    @RequestMapping("updateRelationDatas")
    @ResponseBody
    public AjaxResponseData updateRelationDatas(Long taskCheckModelId, Integer toSaveStatus) {
        AjaxResponseData<String> retVal = new AjaxResponseData<>();
        String saveResult = checkModelBusiness.updateCheckModelStatus(taskCheckModelId, toSaveStatus);
        retVal.setSuccess(StringUtil.isEmpty(saveResult));
        retVal.setMsg(saveResult);
        return retVal;
    }

    @RequestMapping("customDeleteModelData")
    @ResponseBody
    @Override
    public CommonResponseData delete(String modelId, Long[] toDelIds, String isCascade) {
        CommonResponseData retVal = new CommonResponseData();
        //解除绑定
        String releaseResult = checkModelBusiness.releaseRelations(modelId, toDelIds);
        if (StringUtil.isEmpty(releaseResult)) {
            return super.delete(modelId, toDelIds, isCascade);
        }
        retVal.setMsg(releaseResult);
        retVal.setSuccess(false);
        return retVal;
    }

    @RequestMapping("saveAssignRoles")
    @ResponseBody
    public CommonResponseData saveAssignRoles(Long taskCheckModelId, String signroles) {
        CommonResponseData retVal = new CommonResponseData();
        checkModelBusiness.saveAssignRoles(taskCheckModelId, signroles);
        retVal.setMsg("设置成功");
        return retVal;
    }

    @RequestMapping("saveRemark")
    @ResponseBody
    public CommonResponseData saveRemark(Long id, String remark) {
        CommonResponseData retVal = new CommonResponseData();
        checkModelBusiness.saveRemark(id, remark);
        retVal.setMsg("设置成功");
        return retVal;
    }
}
