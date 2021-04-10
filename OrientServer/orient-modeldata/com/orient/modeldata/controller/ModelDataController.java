package com.orient.modeldata.controller;

import com.orient.background.business.ModelFormViewBusiness;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.log.annotion.Action;
import com.orient.modeldata.bean.ImportDataBean;
import com.orient.modeldata.bean.ModelNode;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.modeldata.event.DeleteModelDataEvent;
import com.orient.modeldata.event.GetGridModelDescEvent;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.event.UpdateModelDataEvent;
import com.orient.modeldata.eventParam.DeleteModelDataEventParam;
import com.orient.modeldata.eventParam.GetModelDescEventParam;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.modeldata.validateHandler.bean.ValidateError;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.form.ModelGridViewEntity;
import com.orient.sysmodel.service.form.IModelGridViewService;
import com.orient.sysmodel.service.pvm.IPVMMulTemplateService;
import com.orient.utils.*;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.Log.LogThreadLocalHolder;
import com.orient.web.base.*;
import com.orient.web.model.BaseNode;
import com.orient.web.modelDesc.column.ColumnDesc;
import com.orient.web.modelDesc.column.EnumColumnDesc;
import com.orient.web.modelDesc.column.SimpleColumnDesc;
import com.orient.web.modelDesc.model.OrientModelDesc;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 数据管理控制层
 *
 * @author enjoy
 * @creare 2016-04-01 9:45
 */
@Controller
@RequestMapping("/modelData")
public class ModelDataController extends BaseController {
    @Autowired
    IBusinessModelService businessModelService;

    @Autowired
    IModelGridViewService modelGridViewService;

    @Autowired
    ISqlEngine orientSqlEngine;

    @Autowired
    ModelDataBusiness modelDataBusiness;

    @Autowired
    IPVMMulTemplateService pVMMulTemplateService;

    @Autowired
    ModelFormViewBusiness modelFormViewBusiness;

    @RequestMapping("getModelId")
    @ResponseBody
    public AjaxResponseData<String> getModelId(String tableName, String schemaId, String isView) {
        if (isView == null || "".equals(isView)) {
            isView = "0";
        }
        IBusinessModel businessModel = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.getBusinessModelType(isView));
        String modelId = businessModel.getMainModel().getId();
        AjaxResponseData retVal = new AjaxResponseData();
        retVal.setResults(modelId);
        return retVal;
    }

    @RequestMapping("getTemplateId")
    @ResponseBody
    public AjaxResponseData<String> getTemplateId(String modelId, String templateName) {
        AjaxResponseData retVal = new AjaxResponseData();
        ModelGridViewEntity modelGridViewEntity = modelGridViewService.get(Restrictions.eq("modelid", modelId), Restrictions.eq("name", templateName));
        if (modelGridViewEntity != null) {
            String templateId = String.valueOf(modelGridViewEntity.getId());
            retVal.setResults(templateId);
        } else {
            retVal.setSuccess(false);
            retVal.setMsg("未找到对应模板");
        }
        return retVal;
    }

    @RequestMapping("createDataQuery")
    @ResponseBody
    public AjaxResponseData<List> createDataQuery(String modelName, String schemaId, String customerFilter, String ascCol, String descCol, boolean single, String isView) {
        if (isView == null || "".equals(isView)) {
            isView = "0";
        }
        AjaxResponseData<List> retVal = new AjaxResponseData<>();
        IBusinessModel bm = businessModelService.getBusinessModelBySName(modelName, schemaId, EnumInter.BusinessModelEnum.getBusinessModelType(isView));
        List<CustomerFilter> customerFilters = JsonUtil.getJavaCollection(new CustomerFilter(), customerFilter, new HashMap());
        customerFilters.forEach((cs) -> {
            bm.appendCustomerFilter(cs);
        });
        IBusinessModelQuery modelQuery = orientSqlEngine.getBmService().createModelQuery(bm);
        if (ascCol != null && !"".equals(ascCol)) {
            modelQuery.orderAsc(ascCol);
        }
        if (descCol != null && !"".equals(descCol)) {
            modelQuery.orderDesc(descCol);
        }

        List<Map> dataList = modelQuery.list();
        if (single && dataList != null && dataList.size() > 0) {
            dataList = dataList.subList(0, 1);
        }
        businessModelService.dataChangeModel(orientSqlEngine, bm, dataList, false);
        retVal.setResults(dataList);

        return retVal;
    }

    @RequestMapping("updateModelDataList")
    @ResponseBody
    public AjaxResponseData<String> updateModelDataList(String modelId, String dataList) {
        AjaxResponseData retVal = new AjaxResponseData();
        List<Map> list = JsonUtil.json2List(dataList);
        Map<String, Map<String, String>> dataMap = new HashMap<>();
        for (Map map : list) {
            dataMap.put(map.get("ID").toString(), map);
        }
        IBusinessModel bm = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
        bm.setReserve_filter(" AND ID IN (" + CommonTools.set2String(dataMap.keySet()) + ")");
        List<Map<String, String>> resultList = orientSqlEngine.getBmService().createModelQuery(bm).list();
        if (resultList == null && resultList.size() == 0) {
            retVal.setSuccess(false);
            retVal.setMsg("保存失败");
        } else {
            for (Map<String, String> resultMap : resultList) {
                Map<String, String> modifyMap = dataMap.get(resultMap.get("ID"));
                resultMap.putAll(modifyMap);
                orientSqlEngine.getBmService().updateModelData(bm, resultMap, resultMap.get("ID"));
            }
            retVal.setMsg("保存成功");
        }
        return retVal;
    }

    @RequestMapping("getModelDataByDataId")
    @ResponseBody
    public AjaxResponseData<Map> getDataMapByDataId(String modelName, String schemaId, String dataId) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel bm = businessModelService.getBusinessModelBySName(modelName, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId = bm.getId();
        Map dataMap = modelDataBusiness.getModelDataByModelIdAndDataId(modelId, dataId);
        retVal.setResults(dataMap);

        return retVal;
    }

    @Action(ownermodel = "数据管理", detail = "查看模型【${modelDataBusiness.getModelDisplayName(modelId,isView,userId)}】元数据")
    @RequestMapping("getGridModelDesc")
    @ResponseBody
    public AjaxResponseData<GetModelDescEventParam> getGridModelDesc(String modelId, String templateId, String isView) {
        GetModelDescEventParam getModelDescEventParam = new GetModelDescEventParam(modelId, templateId, isView);
        GetGridModelDescEvent getModelDescEvent = new GetGridModelDescEvent(ModelDataController.class, getModelDescEventParam);
        OrientContextLoaderListener.Appwac.publishEvent(getModelDescEvent);
        OrientModelDesc orientModelDesc = getModelDescEventParam.getOrientModelDesc();
        List<ColumnDesc> columns = orientModelDesc.getColumns();
        Map<Integer, EnumColumnDesc> replaceMap = UtilFactory.newHashMap();
        int count = 0;
        for (ColumnDesc columnDesc : columns) {
            if (columnDesc.getsColumnName().equals("DIVINGDENTRYTIME_" + modelId)||columnDesc.getsColumnName().equals("HATCHOPENTIME_" + modelId)) {
                columnDesc.setType("C_DateTime");
                columnDesc.setClassName("DateTimeColumnDesc");
//                    enumColumnDesc.setClassName("SingleEnumColumnDesc");
//                Map<String, String> aryOptions = new LinkedHashMap<>();
//                aryOptions.put("0:00:00","0:00:00");
//                aryOptions.put("0:15:00","0:15:00");
//                aryOptions.put("0:30:00","0:30:00");
//                aryOptions.put("0:45:00","0:45:00");
//                aryOptions.put("1:00:00","1:00:00");
//                aryOptions.put("1:15:00","1:15:00");
//                aryOptions.put("1:30:00","1:30:00");
//                aryOptions.put("1:45:00","1:45:00");
//                aryOptions.put("2:00:00","2:00:00");
//                aryOptions.put("2:15:00","2:15:00");
//                aryOptions.put("2:30:00","2:30:00");
//                aryOptions.put("2:45:00","2:45:00");
//                aryOptions.put("3:00:00","3:00:00");
//                aryOptions.put("3:15:00","3:15:00");
//                aryOptions.put("3:30:00","3:30:00");
//                aryOptions.put("3:45:00","3:45:00");
//                aryOptions.put("4:00:00","4:00:00");
//                aryOptions.put("4:15:00","4:15:00");
//                aryOptions.put("4:30:00","4:30:00");
//                aryOptions.put("4:45:00","4:45:00");
//                aryOptions.put("5:00:00","5:00:00");
//                aryOptions.put("5:15:00","5:15:00");
//                aryOptions.put("5:30:00","5:30:00");
//                aryOptions.put("5:45:00","5:45:00");
//                aryOptions.put("6:00:00","6:00:00");
//                aryOptions.put("6:15:00","6:15:00");
//                aryOptions.put("6:30:00","6:30:00");
//                aryOptions.put("6:45:00","6:45:00");
//                aryOptions.put("7:00:00","7:00:00");
//                aryOptions.put("7:15:00","7:15:00");
//                aryOptions.put("7:30:00","7:30:00");
//                aryOptions.put("7:45:00","7:45:00");
//                aryOptions.put("8:00:00","8:00:00");
//                aryOptions.put("8:15:00","8:15:00");
//                aryOptions.put("8:30:00","8:30:00");
//                aryOptions.put("8:45:00","8:45:00");
//                aryOptions.put("9:00:00","9:00:00");
//                aryOptions.put("9:15:00","9:15:00");
//                aryOptions.put("9:30:00","9:30:00");
//                aryOptions.put("9:45:00","9:45:00");
//                aryOptions.put("10:00:00","10:00:00");
//                aryOptions.put("10:15:00","10:15:00");
//                aryOptions.put("10:30:00","10:30:00");
//                aryOptions.put("10:45:00","10:45:00");
//                aryOptions.put("11:00:00","11:00:00");
//                aryOptions.put("11:15:00","11:15:00");
//                aryOptions.put("11:30:00","11:30:00");
//                aryOptions.put("11:45:00","11:45:00");
//                aryOptions.put("12:00:00","12:00:00");
//                aryOptions.put("12:15:00","12:15:00");
//                aryOptions.put("12:30:00","12:30:00");
//                aryOptions.put("12:45:00","12:45:00");
//                aryOptions.put("13:00:00","13:00:00");
//                aryOptions.put("13:15:00","13:15:00");
//                aryOptions.put("13:30:00","13:30:00");
//                aryOptions.put("13:45:00","13:45:00");
//                aryOptions.put("14:00:00","14:00:00");
//                aryOptions.put("14:15:00","14:15:00");
//                aryOptions.put("14:30:00","14:30:00");
//                aryOptions.put("14:45:00","14:45:00");
//                aryOptions.put("15:00:00","15:00:00");
//                aryOptions.put("15:15:00","15:15:00");
//                aryOptions.put("15:30:00","15:30:00");
//                aryOptions.put("15:45:00","15:45:00");
//                aryOptions.put("16:00:00","16:00:00");
//                aryOptions.put("16:15:00","16:15:00");
//                aryOptions.put("16:30:00","16:30:00");
//                aryOptions.put("16:45:00","16:45:00");
//                aryOptions.put("17:00:00","17:00:00");
//                aryOptions.put("17:15:00","17:15:00");
//                aryOptions.put("17:30:00","17:30:00");
//                aryOptions.put("17:45:00","17:45:00");
//                aryOptions.put("18:00:00","18:00:00");
//                aryOptions.put("18:15:00","18:15:00");
//                aryOptions.put("18:30:00","18:30:00");
//                aryOptions.put("18:45:00","18:45:00");
//                aryOptions.put("19:00:00","19:00:00");
//                aryOptions.put("19:15:00","19:15:00");
//                aryOptions.put("19:30:00","19:30:00");
//                aryOptions.put("19:45:00","19:45:00");
//                aryOptions.put("20:00:00","20:00:00");
//                aryOptions.put("20:15:00","20:15:00");
//                aryOptions.put("20:30:00","20:30:00");
//                aryOptions.put("20:45:00","20:45:00");
//                aryOptions.put("21:00:00","21:00:00");
//                aryOptions.put("21:15:00","21:15:00");
//                aryOptions.put("21:30:00","21:30:00");
//                aryOptions.put("21:45:00","21:45:00");
//                aryOptions.put("22:00:00","22:00:00");
//                aryOptions.put("22:15:00","22:15:00");
//                aryOptions.put("22:30:00","22:30:00");
//                aryOptions.put("22:45:00","22:45:00");
//                aryOptions.put("23:00:00","23:00:00");
//                aryOptions.put("23:15:00","23:15:00");
//                aryOptions.put("23:30:00","23:30:00");
//                aryOptions.put("23:45:00","23:45:00");
//                EnumColumnDesc enumColumnDesc=new EnumColumnDesc();
//                try {
//                    BeanUtils.copyProperties(enumColumnDesc, columnDesc);
//                    enumColumnDesc.setType("C_SingleEnum");
//                    enumColumnDesc.setClassName("SingleEnumColumnDesc");
//                    enumColumnDesc.setAryOptions(aryOptions);
//                    replaceMap.put(count,enumColumnDesc);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
            }
            count++;
        }
        if (replaceMap != null) {
            //遍历map集合
            for (Integer key : replaceMap.keySet()) {
                EnumColumnDesc enumColumnDesc = replaceMap.get(key);
                columns.remove(key);
                columns.add(enumColumnDesc);
            }
        }
        return new AjaxResponseData<>(getModelDescEventParam);
    }

    @RequestMapping("getCheckTemplateGridModelDesc")
    @ResponseBody
    public AjaxResponseData<GetModelDescEventParam> getCheckTemplateGridModelDesc(String modelId, String templateId, String isView) {
        GetModelDescEventParam getModelDescEventParam = new GetModelDescEventParam(modelId, templateId, isView);
        GetGridModelDescEvent getModelDescEvent = new GetGridModelDescEvent(ModelDataController.class, getModelDescEventParam);
        OrientContextLoaderListener.Appwac.publishEvent(getModelDescEvent);
        SimpleColumnDesc test = (SimpleColumnDesc) getModelDescEventParam.getOrientModelDesc().getColumns().get(4);
        test.setSelector("{'selectorType': '3','multiSelect': false,'selectorName': '选择综合模板'}");
        return new AjaxResponseData<>(getModelDescEventParam);
    }

    /**
     * 获取模型数据
     *
     * @param orientModelId 模型ID
     * @param page          第几页
     * @param limit         每页数据
     * @return
     */
    @Action(ownermodel = "数据管理", detail = "查看模型【${modelDataBusiness.getModelDisplayName(orientModelId,isView,userId)}】数据")
    @RequestMapping("getModelData")
    @ResponseBody
    public ExtGridData<Map> getModelData(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort) {
        ExtGridData<Map> retVal = modelDataBusiness.getModelDataByModelId(orientModelId, isView, page, limit, customerFilter, true, sort);
        return retVal;
    }

    @RequestMapping("getCheckTemplateGridData")
    @ResponseBody
    public ExtGridData<Map> getCheckTemplateGridData(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort) {
        ExtGridData<Map> retVal = modelDataBusiness.getModelDataByModelId(orientModelId, isView, page, limit, customerFilter, true, sort);
        List<Map> results = retVal.getResults();
        results.forEach(map -> {
            String multemplateId = (String) map.get("C_JCJLB_2100");
            String multemplateName = pVMMulTemplateService.get(Restrictions.eq("id", multemplateId)).getName();
            map.put("C_JCJLB_2100_display", multemplateName);
        });
        return retVal;
    }

    /**
     * 保存模型数据
     *
     * @param modelId  所属模型ID
     * @param formData 模型数据
     * @return
     */
    @Action(ownermodel = "数据管理", detail = "新增模型【${modelDataBusiness.getModelDisplayName(modelId,\"0\",userId)}】数据")
    @RequestMapping("saveModelData")
    @ResponseBody
    public AjaxResponseData<String> saveModelData(String modelId, String formData) {
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
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @Action(ownermodel = "数据管理", detail = "删除模型【${modelDataBusiness.getModelDisplayName(modelId,\"0\",userId)}】数据")
    @RequestMapping("deleteModelData")
    @ResponseBody
    public CommonResponseData delete(String modelId, Long[] toDelIds, String isCascade) {
        CommonResponseData retVal = new CommonResponseData();
        DeleteModelDataEventParam deleteModelDataEventParam = new DeleteModelDataEventParam(modelId, toDelIds, isCascade);
        OrientContextLoaderListener.Appwac.publishEvent(new DeleteModelDataEvent(ModelDataController.class, deleteModelDataEventParam));
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 更新数据
     *
     * @param modelId
     * @param formData
     * @return
     */
    @Action(ownermodel = "数据管理", detail = "修改模型【${modelDataBusiness.getModelDisplayName(modelId,\"0\",userId)}】数据")
    @RequestMapping("updateModelData")
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

    /**
     * 获取模型描述 以及 数据描述
     *
     * @param modelId
     * @param templateId
     * @param dataId
     * @return
     */
    @Action(ownermodel = "数据管理", detail = "获取模型【${modelDataBusiness.getModelDisplayName(modelId,isView,userId)}】数据")
    @RequestMapping("getGridModelDescAndData")
    @ResponseBody
    public AjaxResponseData<Map<String, Object>> getGridModelDescAndData(String modelId, String templateId, String dataId, String isView) {
        Map<String, Object> retVal = new HashMap<>();
        //获取模型描述
        GetModelDescEventParam getModelDescEventParam = new GetModelDescEventParam(modelId, templateId, isView);
        GetGridModelDescEvent getModelDescEvent = new GetGridModelDescEvent(ModelDataController.class, getModelDescEventParam);
        OrientContextLoaderListener.Appwac.publishEvent(getModelDescEvent);
        //获取模型数据
        Map dataMap = modelDataBusiness.getModelDataByModelIdAndDataId(modelId, dataId);
        retVal.put("orientModelDesc", getModelDescEventParam.getOrientModelDesc());
        retVal.put("modelData", dataMap);
        return new AjaxResponseData<>(retVal);
    }


    /**
     * @param node
     * @param schemaId
     * @return 采用树形结构展现模型信息
     */
    @Action(ownermodel = "数据管理", detail = "获取模型集合")
    @RequestMapping("getModelNodes")
    @ResponseBody
    public AjaxResponseData<List<ModelNode>> getModelNodes(String node, String schemaId) {
        return new AjaxResponseData(modelDataBusiness.getModelNodes(node, schemaId));
    }

    /**
     * @param node
     * @return 采用树形结构展现所有schema下的所有模型信息
     */
    @Action(ownermodel = "数据管理", detail = "获取模型集合")
    @RequestMapping("getModelTree")
    @ResponseBody
    public AjaxResponseData<List<ModelNode>> getModelTree(String node, Boolean containsView, String excludeSchemaId, String[] excludedSchemaNames) {
        return new AjaxResponseData(modelDataBusiness.getModelNodes(node, containsView, excludeSchemaId, excludedSchemaNames));
    }

    @Action(ownermodel = "数据管理", detail = "获取模型集合")
    @RequestMapping("getModelColumCombobox")
    @ResponseBody
    public ExtComboboxResponseData<ColumnDesc> getModelColumCombobox(String orientModelId) {
        ExtComboboxResponseData<ColumnDesc> retValue = modelDataBusiness.getModelColumCombobox(orientModelId);
        return retValue;
    }

    @Action(ownermodel = "数据管理", detail = "上传文件${fileName}")
    @RequestMapping("uploadModelData")
    @ResponseBody
    public AjaxResponseData<TableEntity> uploadModelData(MultipartFile dataFile) {
        AjaxResponseData retVal = new AjaxResponseData();
        TableEntity tableEntity = modelDataBusiness.uploadAndanalyze(dataFile);
        retVal.setMsg("上传成功");
        retVal.setResults(tableEntity);
        LogThreadLocalHolder.putParamerter("fileName", dataFile.getName());
        return retVal;
    }

    @Action(ownermodel = "数据管理", detail = "导入模型【${modelDataBusiness.getModelDisplayName(modelId,\"0\",userId)}】")
    @RequestMapping("saveImportData")
    @ResponseBody
    public CommonResponseData saveImportData(ImportDataBean importDataBean) {
        CommonResponseData retVal = new CommonResponseData();
        LogThreadLocalHolder.putParamerter("modelId", importDataBean.getModelId());
        modelDataBusiness.saveImportData(importDataBean);
        retVal.setMsg("导入成功");
        return retVal;
    }

    @Action(ownermodel = "数据管理", detail = "导出模型【${modelDataBusiness.getModelDisplayName(modelId,\"0\",userId)}】")
    @RequestMapping("downloadTemplateFile")
    public void downloadTemplateFile(HttpServletRequest request, HttpServletResponse response, String modelId) {
        String filePath = modelDataBusiness.prepareModelTemplateFile(modelId);
        FileOperator.downLoadFile(request, response, filePath, "模板.xls");
    }

    @Action(ownermodel = "数据管理", detail = "导入模型【${modelDataBusiness.getModelDisplayName(modelId,\"0\",userId)}】数据预处理")
    @RequestMapping("preapareExportData")
    @ResponseBody
    public AjaxResponseData<String> preapareExportData(String modelId, String isView, String customerFilter) {
        AjaxResponseData<String> retVal = new AjaxResponseData();
        String fileName = modelDataBusiness.preapareExportData(modelId, isView, customerFilter);
        retVal.setResults(fileName);
        return retVal;
    }

    @Action(ownermodel = "数据管理", detail = "获取模型【${modelDataBusiness.getModelDisplayName(modelId,\"0\",userId)}】动态约束范围")
    @RequestMapping("getDynamicRangeData")
    @ResponseBody
    public AjaxResponseData<Map<String, String>> getDynamicRangeData(String modelId, String columnId) {
        AjaxResponseData<Map<String, String>> retVal = new AjaxResponseData<>();
        Map<String, String> rangeMap = modelDataBusiness.getDynamicRangeData(modelId, columnId);
        retVal.setResults(rangeMap);
        return retVal;
    }

    @Action(ownermodel = "数据管理", detail = "验证模型【${modelDataBusiness.getModelDisplayName(modelId,\"0\",userId)}】数据唯一性")
    @RequestMapping("validateUnique")
    @ResponseBody
    public AjaxResponseData<Boolean> validateUnique(String modelId, String columnId, String columnValue) {
        AjaxResponseData<Boolean> retVal = new AjaxResponseData<>();
        Boolean checkResult = modelDataBusiness.validateUnique(modelId, columnId, columnValue);
        retVal.setResults(checkResult);
        return retVal;
    }

    @Action(ownermodel = "数据管理", detail = "验证模型数据组合唯一性")
    @RequestMapping("validateMultiUnique")
    @ResponseBody
    public AjaxResponseData<Boolean> validateMultiUnique(String data) {
        AjaxResponseData<Boolean> retVal = new AjaxResponseData<>();
        Map dataMap = JsonUtil.json2Map(data);
        //转化为String
        Map<String, String> stringMap = CommonTools.MapToStringMap(dataMap);
        Boolean checkResult = modelDataBusiness.validateMultiUnique(stringMap);
        retVal.setResults(checkResult);
        return retVal;
    }

    @Action(ownermodel = "数据管理", detail = "获取模型【${modelDataBusiness.getModelDisplayName(modelId,\"0\",userId)}】的关联模型信息")
    @RequestMapping("getRefModel")
    @ResponseBody
    public AjaxResponseData<List<BaseNode>> getRefModel(String modelId, Boolean isMain) {
        List<BaseNode> retVal = modelDataBusiness.getRefModel(modelId, isMain);
        return new AjaxResponseData<>(retVal);
    }

    @Action(ownermodel = "数据管理", detail = "验证【${modelDataBusiness.getModelDisplayName(modelId,\"0\",userId)}】模型数据")
    @RequestMapping("validateAll")
    @ResponseBody
    public AjaxResponseData<List<ValidateError>> validateAll(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            //转化为String
            Map<String, String> stringMap = CommonTools.MapToStringMap(dataMap);
            List<ValidateError> errors = modelDataBusiness.doValidate(stringMap, modelId);
            retVal.setResults(errors);
        }
        return retVal;
    }

    @RequestMapping("getModelSNameByModelId")
    @ResponseBody
    public AjaxResponseData<String> getModelSNameByModelId(String modelId) {
        String modelSName = modelDataBusiness.getModelSNameByModelId(modelId);
        return new AjaxResponseData<>(modelSName);
    }

    @RequestMapping("getColumnComboboxByModelId")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getColumnComboboxByModelId(String modelId) {
        if (!StringUtil.isEmpty(modelId)) {
            List<ColumnDesc> columns = modelFormViewBusiness.getModelColumn(modelId);
            ExtComboboxResponseData<ExtComboboxData> retVal = new ExtComboboxResponseData<>();
            retVal.setTotalProperty(columns.size());
            List<ExtComboboxData> items = new ArrayList<>();
            columns.forEach(columnDesc -> {
                ExtComboboxData extComboboxData = new ExtComboboxData(columnDesc.getsColumnName(), columnDesc.getText());
                items.add(extComboboxData);
            });
            retVal.setResults(items);
            return retVal;
        }
        return null;
    }
}

