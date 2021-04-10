package com.orient.collabdev.controller;

import com.orient.collabdev.business.designing.DesignTaskBusiness;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.event.UpdateModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
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

import java.util.Map;

/**
 * @Description 数据包（任务）分解相关
 * @Author GNY
 * @Date 2018/8/1 18:11
 * @Version 1.0
 **/
@Controller
@RequestMapping("/designTask")
public class DesignTaskController extends BaseController {

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    DesignTaskBusiness designTaskBusiness;

    /**
     * 保存任务
     *
     * @param modelId  所属模型ID
     * @param formData 模型数据
     * @return
     */
    @RequestMapping("saveTask")
    @ResponseBody
    public AjaxResponseData<String> saveTask(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            eventParam.setCreateData(true);
            OrientContextLoaderListener.Appwac.publishEvent(new SaveModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("保存成功");
            retVal.setResults(eventParam.getDataMap().get("ID"));
        }
        return retVal;
    }

    /**
     * 查询数据包（任务）
     *
     * @param orientModelId
     * @param isView
     * @param page
     * @param limit
     * @param customerFilter
     * @param sort
     * @param parentNodeId
     * @return
     */
    @RequestMapping("queryTasks")
    @ResponseBody
    public ExtGridData<Map> doQueryTasks(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort, String parentNodeId) {
        return designTaskBusiness.queryTasks(orientModelId, isView, page, limit, customerFilter, sort, parentNodeId);
    }

    /**
     * 更新数据包（任务）记录的数据
     *
     * @param modelId
     * @param formData
     * @return
     */
    @RequestMapping("updateTask")
    @ResponseBody
    public CommonResponseData updateModelData(String modelId, String formData) {
        CommonResponseData retVal = new CommonResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            OrientContextLoaderListener.Appwac.publishEvent(new UpdateModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("保存成功");
        }
        return retVal;
    }

}
