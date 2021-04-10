package com.orient.testresource.controller;

import com.orient.background.business.ModelFormViewBusiness;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.config.ConfigInfo;
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
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.form.ModelGridViewEntity;
import com.orient.sysmodel.service.form.IModelGridViewService;
import com.orient.sysmodel.service.pvm.IPVMMulTemplateService;
import com.orient.testresource.util.TestResourceMgrConstants;
import com.orient.utils.CommonTools;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.FileOperator;
import com.orient.utils.JsonUtil;
import com.orient.utils.Log.LogThreadLocalHolder;
import com.orient.utils.StringUtil;
import com.orient.web.base.*;
import com.orient.web.model.BaseNode;
import com.orient.web.modelDesc.column.ColumnDesc;
import com.orient.web.modelDesc.column.SimpleColumnDesc;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据管理控制层
 *
 * @author enjoy
 * @creare 2016-04-01 9:45
 */
@Controller
@RequestMapping("/resourceMgr")
public class ResourceMgrController extends BaseController {
    @Autowired
    IBusinessModelService businessModelService;

    @Autowired
    ModelDataBusiness modelDataBusiness;

    @Autowired
    ISqlEngine orientSqlEngine;

    /**
     * 获取模型数据
     *
     * @param orientModelId 模型ID
     * @param page          第几页
     * @param limit         每页数据
     * @return
     */
    @Action(ownermodel = "数据管理", detail = "查看模型【${modelDataBusiness.getModelDisplayName(orientModelId,isView,userId)}】数据")
    @RequestMapping("getCalcModelData")
    @ResponseBody
    public ExtGridData<Map> getCalcModelData(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort) {
        ExtGridData<Map> retVal = modelDataBusiness.getModelDataByModelId(orientModelId, isView, page, limit, customerFilter, true, sort);
        if(retVal.getResults()!=null && retVal.getResults().size()>0) {
            for(Map<String, String> map : retVal.getResults()) {
                String tableName = map.get("C_TAB_NAME_"+orientModelId);
                String schemaId = map.get("C_SCHEMA_ID_"+orientModelId);
                String dataId = map.get("C_DATA_ID_" + orientModelId);
                try {
                    IBusinessModel bm = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
                    String showStr = "";
                    List<IBusinessColumn> showCols = bm.getRefShowColumns();
                    Map<String, String> refMap = modelDataBusiness.getModelDataByModelIdAndDataId(bm.getId(), dataId);
                    for(IBusinessColumn bc : showCols) {
                        String colName = bc.getS_column_name();
                        if("".equals(showStr)) {
                            showStr = refMap.get(colName);
                        }
                        else {
                            showStr = showStr + "," + refMap.get(colName);
                        }
                    }
                    map.put("C_MC_"+orientModelId, showStr);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return retVal;
    }

    @RequestMapping("getCalcRecordByModelInfo")
    @ResponseBody
    public AjaxResponseData<List<Map>> getCalcRecordByModelInfo(String tableName, String schemaId, String modelId, String dataId, boolean single) {
        List<Map> retList = new ArrayList<>();
        if(modelId!=null && !"".equals(modelId)) {
            IBusinessModel bm = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
            tableName = bm.getSchema().getName();
            schemaId = bm.getSchema().getId();
        }
        IBusinessModel bm = businessModelService.getBusinessModelBySName("T_JLSB", ConfigInfo.DEVICE_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        bm.setReserve_filter("AND C_TAB_NAME_" + bm.getId() + "='" + tableName + "'" +
                " AND C_SCHEMA_ID_" + bm.getId() + "=" + schemaId +
                " AND C_DATA_ID_" + bm.getId() + "=" + dataId);
        List<Map<String,String>> calcList = orientSqlEngine.getBmService().createModelQuery(bm).list();
        if(calcList!=null && calcList.size()>0) {
            String calcId = calcList.get(0).get("ID");
            bm = businessModelService.getBusinessModelBySName("T_SBJLJL", ConfigInfo.DEVICE_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
            bm.setReserve_filter(" AND T_JLSB_"+ConfigInfo.DEVICE_SCHEMA_ID+"_ID="+calcId);
            List<Map> list = orientSqlEngine.getBmService().createModelQuery(bm).orderDesc("C_JLSJ_"+bm.getId()).list();
            if(single && list!=null && list.size()>0) {
                retList = list.subList(0, 1);
            }
            else {
                retList = list;
            }
            businessModelService.dataChangeModel(orientSqlEngine, bm, retList, false);
        }
        return new AjaxResponseData<>(retList);
    }

    @RequestMapping("getDeviceTypeByPid")
    @ResponseBody
    public AjaxResponseData<List<Map<String,Object>>> getDeviceTypeByPid(String node) {
        List<Map<String,Object>> retList = new ArrayList<>();
        String schemaId = ConfigInfo.DEVICE_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName("T_DEV_TYPE", schemaId, EnumInter.BusinessModelEnum.Table);
        if(node==null || "".equals(node) || "-1".equals(node)) {
            bm.setReserve_filter(" AND T_DEV_TYPE_"+schemaId+"_ID IS NULL");
        }
        else {
            bm.setReserve_filter(" AND T_DEV_TYPE_"+schemaId+"_ID="+node);
        }

        List<Map<String,String>> list = orientSqlEngine.getBmService().createModelQuery(bm).list();
        if(list!=null && list.size()>0) {
            for(Map<String, String> map : list) {
                Map<String, Object> retMap = new HashMap<>();
                retMap.put("id", map.get("ID"));
                retMap.put("text", map.get("C_NAME_"+bm.getId()));
                retMap.put("expanded", true);
                retList.add(retMap);
            }
        }
        return new AjaxResponseData<>(retList);
    }

    @RequestMapping("getStaffTypeByPid")
    @ResponseBody
    public AjaxResponseData<List<Map<String,Object>>> getStaffTypeByPid(String node) {
        List<Map<String,Object>> retList = new ArrayList<>();
        String schemaId = ConfigInfo.DEVICE_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName("T_RYFL", schemaId, EnumInter.BusinessModelEnum.Table);
        if(node==null || "".equals(node) || "-1".equals(node)) {
            bm.setReserve_filter(" AND T_RYFL_"+schemaId+"_ID IS NULL");
        }
        else {
            bm.setReserve_filter(" AND T_RYFL_"+schemaId+"_ID="+node);
        }

        List<Map<String,String>> list = orientSqlEngine.getBmService().createModelQuery(bm).list();
        if(list!=null && list.size()>0) {
            for(Map<String, String> map : list) {
                Map<String, Object> retMap = new HashMap<>();
                retMap.put("id", map.get("ID"));
                retMap.put("text", map.get("C_FLMC_"+bm.getId()));
                retMap.put("expanded", true);
                retList.add(retMap);
            }
        }
        return new AjaxResponseData<>(retList);
    }

    @RequestMapping("setDeviceState")
    @ResponseBody
    public CommonResponseData setDeviceState(String ids, String state) {
        CommonResponseData retVal = new CommonResponseData();
        String schemaId = ConfigInfo.DEVICE_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName("T_DEVICE", schemaId, EnumInter.BusinessModelEnum.Table);
        bm.setReserve_filter(" AND ID IN ("+ids+")");
        List<Map<String,String>> list = orientSqlEngine.getBmService().createModelQuery(bm).list();
        for(Map<String, String> map : list) {
            map.put("C_STATE_"+bm.getId(), state);
            orientSqlEngine.getBmService().updateModelData(bm, map, map.get("ID"));
        }
        return retVal;
    }

    @RequestMapping("addStaffToTeam")
    @ResponseBody
    public AjaxResponseData<List<String>> addStaffToTeam(String teamId, String staffIds) {
        String[] staffIdArr = staffIds.split(",");
        AjaxResponseData<List<String>> retVal = new AjaxResponseData<>();
        String schemaId = ConfigInfo.DEVICE_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName("T_TD_RY", schemaId, EnumInter.BusinessModelEnum.Table);
        List<String> newIds = new ArrayList<>();
        for(String staffId : staffIdArr) {
            Map<String, String> map = new HashMap<>();
            map.put("T_SYTD_"+schemaId+"_ID", teamId);
            map.put("T_SYRY_"+schemaId+"_ID", staffId);
            String newId = orientSqlEngine.getBmService().insertModelData(bm, map);
            newIds.add(newId);
        }

        retVal.setResults(newIds);
        return retVal;
    }

    @RequestMapping("deleteStaffFromTeam")
    @ResponseBody
    public CommonResponseData deleteStaffFromTeam(String teamId, Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        String ids = CommonTools.array2String(toDelIds);
        String schemaId = ConfigInfo.DEVICE_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName("T_TD_RY", schemaId, EnumInter.BusinessModelEnum.Table);
        bm.setReserve_filter(" AND T_SYRY_"+schemaId+"_ID IN ("+ids+")");
        List<Map<String,Object>> list = orientSqlEngine.getBmService().createModelQuery(bm).list();
        List<String> delIdList = new ArrayList<>();
        for(Map<String, Object> map : list) {
            delIdList.add((String) map.get("ID"));
        }
        orientSqlEngine.getBmService().delete(bm, CommonTools.list2String(delIdList));
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("addDeviceUseRecord")
    @ResponseBody
    public AjaxResponseData<String> addDeviceUseRecord(String deviceId, String task, String outTime, String inTime, String times) {
        AjaxResponseData<String> retVal = new AjaxResponseData<>();
        String schemaId = ConfigInfo.DEVICE_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName("T_SBSYJL", schemaId, EnumInter.BusinessModelEnum.Table);
        Map<String, String> map = new HashMap<String, String>();
        map.put("T_DEVICE_"+schemaId+"_ID", deviceId);
        map.put("C_SYRW_"+bm.getId(), task);
        map.put("C_JCSJ_"+bm.getId(), outTime);
        map.put("C_GHSJ_"+bm.getId(), inTime);
        map.put("C_SYCS_" + bm.getId(), times);
        String newId = orientSqlEngine.getBmService().insertModelData(bm, map);
        retVal.setResults(newId);
        return retVal;
    }

    @RequestMapping("addDeviceRepairRecord")
    @ResponseBody
    public AjaxResponseData<String> addDeviceRepairRecord(String deviceId, String company, String time, String worker, String bug, String billId) {
        AjaxResponseData<String> retVal = new AjaxResponseData<>();
        String schemaId = ConfigInfo.DEVICE_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName("T_SBWXJL", schemaId, EnumInter.BusinessModelEnum.Table);
        Map<String, String> map = new HashMap<String, String>();
        map.put("T_DEVICE_"+schemaId+"_ID", deviceId);
        map.put("C_WTDW_"+bm.getId(), company);
        map.put("C_WXSJ_"+bm.getId(), time);
        map.put("C_WHRY_"+bm.getId(), worker);
        map.put("C_GZDW_"+bm.getId(), bug);
        map.put("C_WXDH_"+bm.getId(), billId);
        String newId = orientSqlEngine.getBmService().insertModelData(bm, map);
        retVal.setResults(newId);
        return retVal;
    }

    @RequestMapping("addDeviceCalcRecord")
    @ResponseBody
    public AjaxResponseData<String> addDeviceCalcRecord(String tableName, String schemaId, String modelId, String dataId, String code, String time, String worker, String certificate, String properties) {
        AjaxResponseData<String> retVal = new AjaxResponseData<>();
        if(modelId!=null && !"".equals(modelId)) {
            IBusinessModel bm = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
            tableName = bm.getSchema().getName();
            schemaId = bm.getSchema().getId();
        }
        String deviceSchemaId = ConfigInfo.DEVICE_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName("T_JLSB", deviceSchemaId, EnumInter.BusinessModelEnum.Table);
        bm.setReserve_filter("AND C_TAB_NAME_"+bm.getId()+"='"+tableName+"'" +
                " AND C_SCHEMA_ID_"+bm.getId()+"="+schemaId +
                " AND C_DATA_ID_"+bm.getId()+"="+dataId);
        List<Map<String,String>> list = orientSqlEngine.getBmService().createModelQuery(bm).list();
        if(list==null || list.size()==0) {
            retVal.setSuccess(false);
            retVal.setMsg("未找到相关计量数据");
            return retVal;
        }

        String calcDeviceId = list.get(0).get("ID");
        IBusinessModel calcRecBm = businessModelService.getBusinessModelBySName("T_SBJLJL", deviceSchemaId, EnumInter.BusinessModelEnum.Table);
        Map<String, String> map = new HashMap<>();
        map.put("T_JLSB_"+deviceSchemaId+"_ID", calcDeviceId);
        map.put("C_JLBH_"+calcRecBm.getId(), code);
        map.put("C_JLSJ_"+calcRecBm.getId(), time);
        map.put("C_JLRY_"+calcRecBm.getId(), worker);
        map.put("C_JLHGZ_" + calcRecBm.getId(), certificate);
        map.put("C_JLSX_" + calcRecBm.getId(), properties);
        String newId = orientSqlEngine.getBmService().insertModelData(calcRecBm, map);
        retVal.setResults(newId);
        return retVal;
    }
}

