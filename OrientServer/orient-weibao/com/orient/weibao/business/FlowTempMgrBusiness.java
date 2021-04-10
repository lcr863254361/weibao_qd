package com.orient.weibao.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseBusiness;
import com.orient.weibao.constants.PropertyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-05-23 14:20
 */
@Service
public class FlowTempMgrBusiness extends BaseBusiness{
    @Autowired
    JdbcTemplate jdbcTemplate;
    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
    public List<Map> queryFlowTempTypeList() {

        IBusinessModel flowTempTypeModel = businessModelService.getBusinessModelBySName(PropertyConstant.FLOW_TEMP_TYPE, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId = flowTempTypeModel.getId();
        String sql = "select * from T_FLOW_TEMP_TYPE_" + schemaId +" order BY TO_NUMBER(ID) ASC";
        List<Map<String, Object>> flowTypeList = jdbcTemplate.queryForList(sql);
        List consumeList = new ArrayList<>();
        if (flowTypeList.size() > 0) {
            for (Map map : flowTypeList) {
                Map flowMap = new HashMap<>();
                flowMap.put("id", map.get("ID"));
                flowMap.put("flowTempType", map.get("C_NAME_" + modelId));
                consumeList.add(flowMap);
            }
        }
        return consumeList;
    }

    public AjaxResponseData addFlowTempTypeData(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("保存内容为空");
            retVal.setSuccess(false);
            return retVal;
        }
        Map formDataMap = JsonUtil.json2Map(formData);
        Map dataMap = (Map) formDataMap.get("fields");
        dataMap.put("C_NAME_" + modelId, dataMap.get("C_NAME_" + modelId));
        SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
        eventParam.setModelId(modelId);
        eventParam.setDataMap(dataMap);
        eventParam.setCreateData(true);
        OrientContextLoaderListener.Appwac.publishEvent(new SaveModelDataEvent(ModelDataController.class, eventParam));
        retVal.setMsg("保存成功");
        return retVal;
    }

    public AjaxResponseData updateFlowTempTypeData(String modelId, String flowTempTypeId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("保存内容为空");
            retVal.setSuccess(false);
            return retVal;
        }
        Map formDataMap = JsonUtil.json2Map(formData);
        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        String sql = "update T_FLOW_TEMP_TYPE_" + schemaId + " set C_NAME_" + modelId + "=?" + " where id=?";
        Map dataMap = (Map) formDataMap.get("fields");
        jdbcTemplate.update(sql, dataMap.get("C_NAME_" + modelId), flowTempTypeId);
        retVal.setMsg("修改成功");
        return retVal;
    }

    public void delFlowTempTypeById(String flowTempTypeId) {
        String tableName = PropertyConstant.FLOW_TEMP_TYPE;
        IBusinessModel bm = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        orientSqlEngine.getBmService().deleteCascade(bm, flowTempTypeId);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        divingTaskBM.setReserve_filter("AND C_FLOW_TEMP_TYPE_" + divingTaskBM.getId() + "='" + flowTempTypeId + "'");
        List<Map> divingTaskList=orientSqlEngine.getBmService().createModelQuery(divingTaskBM).list();
        if (divingTaskList.size()>0){
            for (Map taskMap:divingTaskList){
                String taskId=(String)taskMap.get("ID");
                taskMap.put("C_FLOW_TEMP_TYPE_"+divingTaskBM.getId(),"");
                orientSqlEngine.getBmService().updateModelData(divingTaskBM,taskMap,taskId);
            }
        }
    }
}
