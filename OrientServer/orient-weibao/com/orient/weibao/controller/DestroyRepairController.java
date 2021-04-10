package com.orient.weibao.controller;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.event.ProjectTreeNodeCreatedEvent;
import com.orient.collab.event.ProjectTreeNodeCreatedEventParam;
import com.orient.collab.event.ProjectTreeNodeDeletedEvent;
import com.orient.collab.event.ProjectTreeNodeDeletedEventParam;
import com.orient.collab.model.GanttPlan;
import com.orient.collab.model.Project;
import com.orient.collab.model.TreeDeleteResult;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.CommonTools;
import com.orient.utils.ExcelUtil.reader.ExcelReader;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.*;
import com.orient.weibao.bean.ProductStructureTreeNode;
import com.orient.weibao.bean.ganttBean.DestroyFlowBean;
import com.orient.weibao.bean.ganttBean.DestroyTypeBean;
import com.orient.weibao.business.DestroyRepairBusiness;
import com.orient.weibao.constants.PropertyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/destroyRepairMgr")
public class DestroyRepairController extends BaseController {

    @Autowired
    DestroyRepairBusiness destroyRepairBusiness;
    @Autowired
    ISqlEngine orientSqlEngine;
    @Autowired
    IBusinessModelService businessModelService;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    @RequestMapping("getDestroyNextLayerNodes")
    @ResponseBody
    public AjaxResponseData<List<ProductStructureTreeNode>> getDestroyNextLayerNodes(String id, String type, String level) {
        return destroyRepairBusiness.getDestroyNextLayerNodes(id, type, level);
    }

    /**
     * @param modelId
     * @param formData
     * @return
     */
    @RequestMapping("/saveDestroyTypeData")
    @ResponseBody
    public AjaxResponseData<Map<String, String>> saveDestroyTypeData(String modelId, String formData) {
        AjaxResponseData<Map<String, String>> retVal = new AjaxResponseData();
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
            retVal.setResults(dataMap);
            retVal.setMsg("保存成功");
        }
        return retVal;
    }

    @RequestMapping("/deleteDestroyTypeData")
    @ResponseBody
    public CommonResponseData deleteDestroyTypeData(String dataId, String modelName) {
        CommonResponseData retVal = new CommonResponseData();
        IBusinessModel destroyTypeBM = businessModelService.getBusinessModelBySName(modelName, schemaId, EnumInter.BusinessModelEnum.Table);

        orientSqlEngine.getBmService().deleteCascade(destroyTypeBM, dataId);
        retVal.setSuccess(true);
        retVal.setMsg("删除成功！");
        return retVal;
    }

    @RequestMapping("/getDestroyTypeList")
    @ResponseBody
    public ExtGridData<DestroyTypeBean> getDestroyTypeList() {
        List<DestroyTypeBean> destroyTypeBeanList = destroyRepairBusiness.getDestroyTypeList();
        ExtGridData<DestroyTypeBean> retVal = new ExtGridData<>();
        retVal.setResults(destroyTypeBeanList);
        retVal.setTotalProperty(destroyTypeBeanList.size());
        return retVal;
    }

    @RequestMapping("getGanttDestroyFlow")
    @ResponseBody
    public List<DestroyFlowBean> getGanttDestroyFlow(String parModelName, String parDataId) throws Exception {
        return destroyRepairBusiness.getGanttDestroyFlow(parModelName, parDataId);
    }

    @RequestMapping(value = "/saveOrUpdateDestroyFlow", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public List<DestroyFlowBean> saveOrUpdateDestroyFlow(String rootModelName, String rootDataId, @ModelAttribute("data") String plansString, boolean isDestroyTask) {
        List<DestroyFlowBean> destroyFlowBeanList = JSONArray.parseArray(plansString, DestroyFlowBean.class);
        List<DestroyFlowBean> newPlans = destroyRepairBusiness.saveOrUpdateDestroyFlow(rootModelName, rootDataId, destroyFlowBeanList,isDestroyTask);
        return newPlans;
    }

    @RequestMapping(value = "/deleteDestroyFlow", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponseData deleteDestroyFlow(@ModelAttribute("data") String toDeletePlans, BindingResult result) {
        List<DestroyFlowBean> destroyFlowBeanList = JSONArray.parseArray(toDeletePlans, DestroyFlowBean.class);
        return destroyRepairBusiness.deleteDestroyFlow(destroyFlowBeanList);
    }

    @RequestMapping("saveDestroyTaskData")
    @ResponseBody
    public AjaxResponseData saveDestroyTaskData(String modelId, String formData) {
        return destroyRepairBusiness.saveDestroyTaskData(modelId, formData);
    }

    @RequestMapping("saveDestroyFlowInstData")
    @ResponseBody
    public AjaxResponseData<String> saveDestroyFlowInstData(String modelId, String formData, String destroyTaskId) {

        IBusinessModel deviceLifeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_FLOW, schemaId, EnumInter.BusinessModelEnum.Table);
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
            dataMap.put("T_DESTROY_TASK_" + schemaId + "_ID", destroyTaskId);
            orientSqlEngine.getBmService().insertModelData(deviceLifeBM, dataMap);
            retVal.setSuccess(true);
            retVal.setMsg("保存成功");
            return retVal;
        }
    }

    /**
     * 新增拆解任务时获取拆解模板枚举列表
     *
     * @return
     */
    @RequestMapping("getEnumDestroyType")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getEnumDestroyType() {
        ExtComboboxResponseData<ExtComboboxData> retValue = destroyRepairBusiness.getEnumDestroyType();
        return retValue;
    }

    /**
     * 启动、结束拆解任务
     * @param destroyTaskId
     * @return
     */
    @RequestMapping("destroyTaskBegin")
    @ResponseBody
    public CommonResponseData destroyTaskBegin(String destroyTaskId) {
        return destroyRepairBusiness.destroyTaskBegin(destroyTaskId);
    }

    /***
     * 通过拆解流程ID删除拆解流程
     * @param destroyFlowId
     * @return
     */
    @RequestMapping("delDestroyFlowById")
    @ResponseBody
    public AjaxResponseData delDestroyFlowById(String destroyFlowId) {
        AjaxResponseData retVal = new AjaxResponseData();
        destroyRepairBusiness.delDestroyFlowById(destroyFlowId);
        retVal.setSuccess(true);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /***
     * 拆解实例保存参与的人员
     * @param id
     * @param postId
     * @param flowId
     * @param selectPersonId
     * @return
     */
    @RequestMapping("saveAttendPersonData")
    @ResponseBody
    public AjaxResponseData saveAttendPersonData(String id,String postId,String flowId,String selectPersonId){
        return destroyRepairBusiness.saveAttendPersonData(id,postId,flowId,selectPersonId);
    }

    /**
     * 拆解实例获取拆解岗位数据
     * @param orientModelId
     * @param isView
     * @param page
     * @param limit
     * @param customerFilter
     * @param sort
     * @param taskId
     * @return
     */
    @RequestMapping("queryDestroyPostData")
    @ResponseBody
    public ExtGridData<Map> queryDestroyPostData(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort,String taskId) {
        ExtGridData<Map> retVal = destroyRepairBusiness.queryDestroyPostData(orientModelId, isView, page, limit, customerFilter, true, sort,taskId);
        return retVal;
    }

    /**
     * 导入拆解模板
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("importDestroyListFromExcel")
    @ResponseBody
    public void importDestroyListFromExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = null;
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包含multipart类型的数据
        if (multipartResolver.isMultipart(request)) {
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            if (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                fileName = file.getOriginalFilename();
                File dst = new File(fileName);
                try {
                    file.transferTo(dst);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ExcelReader excelReader = new ExcelReader();
        File excelFile = new File(fileName);
        InputStream input = new FileInputStream(excelFile);
        boolean after2007 = fileName.substring(fileName.length() - 4).equals("xlsx");
        TableEntity excelEntity = excelReader.readFile(input, after2007);
        List<String> headers = Arrays.asList(excelReader.getExcelReaderConfig().getColumns());

        String destroyTypeId = request.getParameter("destroyTypeId");
        String destroyTypeName=request.getParameter("destroyName");
        Map<String, Object> retVal = destroyRepairBusiness.importDestroyListFromExcel(excelEntity, destroyTypeId,destroyTypeName);
        try {
            response.setContentType("text/html");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), retVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        excelFile.delete();

    }

    @RequestMapping("saveChooseProductTree")
    @ResponseBody
    public AjaxResponseData  saveChooseProductTree(String treeId,String checkTableInstId){
        return  destroyRepairBusiness.saveChooseProductTree(treeId, checkTableInstId);
    }

    @RequestMapping("getChooseProductTree")
    @ResponseBody
    public AjaxResponseData  getChooseProductTree(String checkTableInstId){
        return  destroyRepairBusiness.getChooseProductTree(checkTableInstId);
    }
}
