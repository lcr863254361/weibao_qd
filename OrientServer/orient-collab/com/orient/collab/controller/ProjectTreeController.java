package com.orient.collab.controller;

import com.orient.collab.business.ProjectTreeBusiness;
import com.orient.collab.event.*;
import com.orient.collab.model.Project;
import com.orient.collab.model.ProjectTreeNode;
import com.orient.collab.model.TreeDeleteResult;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.utils.CommonTools;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static com.orient.collab.config.CollabConstants.STATUS_UNSTARTED;

/**
 * 项目树控制
 *
 * @author Seraph
 *         2016-07-06 上午11:05
 */
@Controller
@RequestMapping("/projectTree")
public class ProjectTreeController extends BaseController {

    @RequestMapping(value = "/nextLayerNodes")
    @ResponseBody
    public AjaxResponseData<List<ProjectTreeNode>> getNextLayerNodes(String functionModule, String modelName, String dataId, String node) {
        return new AjaxResponseData(projectTreeBusiness.getNextLayerNodes(functionModule, modelName, dataId));
    }

    @RequestMapping(value = "/nodeInfo")
    @ResponseBody
    public ProjectTreeNode getTreeNodeInfo(String modelName, String dataId) {
        return projectTreeBusiness.getTreeNodeInfo(modelName, dataId);
    }

    @RequestMapping(value = "/nodeInfo/parent")
    @ResponseBody
    public ProjectTreeNode getParentTreeNodeInfo(String modelName, String dataId) {
        return projectTreeBusiness.getParentTreeNodeInfo(modelName, dataId);
    }

    @RequestMapping(value = "/nodeModelInfo")
    @ResponseBody
    public AjaxResponseData<Map<String, String>> getNodeModelInfo(String modelName) {
        return new AjaxResponseData(projectTreeBusiness.getNodeModelInfo(modelName));
    }

    @RequestMapping(value = "/deleteNode")
    @ResponseBody
    public CommonResponseData deleteNode(String modelName, String dataId) {
        TreeDeleteResult retV = projectTreeBusiness.deleteNode(modelName, dataId);
        CommonResponseData commonResponseData = new CommonResponseData();
        if (retV.getDeleteSuccess()) {
            ProjectTreeNodeDeletedEventParam deletedEventParam = new ProjectTreeNodeDeletedEventParam(modelName, dataId, retV);
            OrientContextLoaderListener.Appwac.publishEvent(new ProjectTreeNodeDeletedEvent(this, deletedEventParam));
            commonResponseData.setSuccess(true);
            commonResponseData.setMsg("删除成功!");
        } else {
            commonResponseData.setSuccess(false);
            commonResponseData.setMsg(retV.getErrorMsg());
        }
        return commonResponseData;
    }

    @RequestMapping("/saveModelData")
    @ResponseBody
    public AjaxResponseData<Map<String, String>> saveModelData(String modelId, String formData) {
        AjaxResponseData<Map<String, String>> retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            dataMap.put("STATUS_" + modelId, STATUS_UNSTARTED);
            ProjectTreeNodeCreatedEventParam eventParam = new ProjectTreeNodeCreatedEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            eventParam.setCreateData(true);
            OrientContextLoaderListener.Appwac.publishEvent(new ProjectTreeNodeCreatedEvent(this, eventParam));
            retVal.setResults(dataMap);
            retVal.setMsg("保存成功");
        }
        return retVal;
    }

    @RequestMapping("/updateModelData")
    @ResponseBody
    public AjaxResponseData<Map<String, String>> updateModelData(String modelId, String formData) {
        AjaxResponseData<Map<String, String>> retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            ProjectTreeNodeEditEventParam eventParam = new ProjectTreeNodeEditEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            eventParam.setOriginalUserId(this.projectTreeBusiness.getOriginalAssigneeId(modelId, CommonTools.Obj2String(dataMap.get("ID"))));
            OrientContextLoaderListener.Appwac.publishEvent(new ProjectTreeNodeEditEvent(this, eventParam));
            retVal.setResults(dataMap);
            retVal.setMsg("保存成功");
        }
        return retVal;
    }

    @RequestMapping("/listPrjAsGrid")
    @ResponseBody
    public ExtGridData<Project> listPrjAsGrid(String dirId) {
        List<Project> projects = projectTreeBusiness.listPrjAsGrid(dirId);
        ExtGridData<Project> retVal = new ExtGridData<>();
        retVal.setResults(projects);
        retVal.setTotalProperty(projects.size());
        return retVal;
    }

    @Autowired
    private ProjectTreeBusiness projectTreeBusiness;
}
