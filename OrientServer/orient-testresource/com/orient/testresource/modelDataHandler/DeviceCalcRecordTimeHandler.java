package com.orient.testresource.modelDataHandler;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.config.ConfigInfo;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sqlengine.extend.ModelDataOperate;
import com.orient.sqlengine.extend.annotation.ModelOperateExtend;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
@ModelOperateExtend(modelNames = {"T_SBJLJL"}, schemaName = "试验资源管理")
public class DeviceCalcRecordTimeHandler implements ModelDataOperate {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    @Qualifier("BusinessModelService")
    protected IBusinessModelService businessModelService;

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Override
    public void beforeAdd(IBusinessModel bm, Map<String, String> dataMap) {

    }

    @Override
    public void afterAdd(IBusinessModel bm, Map<String, String> dataMap, String id) {
        String modelId = bm.getId();
        String timeStr = dataMap.get("C_JLSJ_"+modelId);
        if(timeStr==null || "".equals(timeStr)) {
            throw new OrientBaseAjaxException("", "计量时间不可为空");
        }

        String schemaId = ConfigInfo.DEVICE_SCHEMA_ID;
        String deviceId = dataMap.get("T_JLSB_"+schemaId+"_ID");
        IBusinessModel calcBm = businessModelService.getBusinessModelBySName("T_JLSB", schemaId, EnumInter.BusinessModelEnum.Table);
        String mId = calcBm.getId();
        calcBm.setReserve_filter(" AND ID = " + deviceId);
        Map<String,String> deviceMap = (Map<String, String>) orientSqlEngine.getBmService().createModelQuery(calcBm).list().get(0);
        deviceMap.put("C_SCJYRQ_"+mId, timeStr);
        String period = deviceMap.get("C_JYZQ_"+mId);
        try {
            Date startDate = format.parse(timeStr);
            long during = Long.valueOf(period);
            long endTS = startDate.getTime()+during*24*3600*1000;
            String endTime = format.format(new Date(endTS));
            deviceMap.put("C_XCJYRQ_"+mId, endTime);
            orientSqlEngine.getBmService().updateModelData(calcBm, deviceMap, deviceId);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeDelete(IBusinessModel bm, String dataIds) {

    }

    @Override
    public void afterDelete(IBusinessModel bm, String dataIds) {

    }

    @Override
    public void beforeDeleteCascade(IBusinessModel bm, String dataIds) {

    }

    @Override
    public void afterDeleteCascade(IBusinessModel bm, String dataIds) {

    }

    @Override
    public void beforeUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId) {

    }

    @Override
    public void afterUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId, Boolean result) {
        String modelId = bm.getId();
        String timeStr = dataMap.get("C_JLSJ_"+modelId);
        if(timeStr==null || "".equals(timeStr)) {
            throw new OrientBaseAjaxException("", "计量时间不可为空");
        }

        String schemaId = ConfigInfo.DEVICE_SCHEMA_ID;
        String deviceId = dataMap.get("T_JLSB_"+schemaId+"_ID");

        IBusinessModel calcBm = businessModelService.getBusinessModelBySName("T_JLSB", schemaId, EnumInter.BusinessModelEnum.Table);
        String mId = calcBm.getId();
        calcBm.setReserve_filter(" AND ID = " + deviceId);
        Map<String,String> deviceMap = (Map<String, String>) orientSqlEngine.getBmService().createModelQuery(calcBm).list().get(0);
        deviceMap.put("C_SCJYRQ_"+mId, timeStr);
        String period = deviceMap.get("C_JYZQ_"+mId);
        try {
            Date startDate = format.parse(timeStr);
            long during = Long.valueOf(period);
            long endTS = startDate.getTime()+during*24*3600*1000;
            String endTime = format.format(new Date(endTS));
            deviceMap.put("C_XCJYRQ_"+mId, endTime);
            orientSqlEngine.getBmService().updateModelData(calcBm, deviceMap, deviceId);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
