package com.orient.example.component.dataextend;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.config.ConfigInfo;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sqlengine.extend.ModelDataOperate;
import com.orient.sqlengine.extend.annotation.ModelOperateExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
@ModelOperateExtend(modelNames = {"T_SBFP"}, schemaName = "拖曳水池试验数据模型")
public class DeviceAssignHandler implements ModelDataOperate {
    @Autowired
    @Qualifier("BusinessModelService")
    protected IBusinessModelService businessModelService;

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Override
    public void beforeAdd(IBusinessModel bm, Map<String, String> dataMap) {
        //System.err.println("【拖曳水池试验数据模型】新增前处理");
    }

    @Override
    public void afterAdd(IBusinessModel bm, Map<String, String> dataMap, String id) {
        System.err.println("【拖曳水池试验数据模型】新增后处理：" + id);
        String modelId = bm.getId();
        String devId = dataMap.get("C_SB_" + modelId);
        String state = dataMap.get("C_GLZT_" + modelId);

        if(state!=null && !"".equals(state)) {
            String schemaId = ConfigInfo.DEVICE_SCHEMA_ID;
            IBusinessModel devBm = businessModelService.getBusinessModelBySName("T_DEVICE", schemaId, EnumInter.BusinessModelEnum.Table);
            String mId = devBm.getId();
            devBm.setReserve_filter(" AND ID = " + devId);
            Map<String,String> devMap = (Map<String, String>) orientSqlEngine.getBmService().createModelQuery(devBm).list().get(0);
            devMap.put("C_STATE_"+mId, state);
            orientSqlEngine.getBmService().updateModelData(devBm, devMap, devId);
        }
    }

    @Override
    public void beforeDelete(IBusinessModel bm, String dataIds) {
        //System.err.println("【拖曳水池试验数据模型】删除前处理");
    }

    @Override
    public void afterDelete(IBusinessModel bm, String dataIds) {
        //System.err.println("【拖曳水池试验数据模型】删除后处理");
    }

    @Override
    public void beforeDeleteCascade(IBusinessModel bm, String dataIds) {
        //System.err.println("【拖曳水池试验数据模型】级联删除前处理");
    }

    @Override
    public void afterDeleteCascade(IBusinessModel bm, String dataIds) {
        //System.err.println("【拖曳水池试验数据模型】级联删出后处理");
    }

    @Override
    public void beforeUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId) {
        //System.err.println("【拖曳水池试验数据模型】修改前处理");
    }

    @Override
    public void afterUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId, Boolean result) {
        System.err.println("【拖曳水池试验数据模型】修改后处理：" + result);
        String modelId = bm.getId();
        String devId = dataMap.get("C_SB_" + modelId);
        String state = dataMap.get("C_GLZT_" + modelId);

        if(state!=null && !"".equals(state)) {
            String schemaId = ConfigInfo.DEVICE_SCHEMA_ID;
            IBusinessModel devBm = businessModelService.getBusinessModelBySName("T_DEVICE", schemaId, EnumInter.BusinessModelEnum.Table);
            String mId = devBm.getId();
            devBm.setReserve_filter(" AND ID = " + devId);
            Map<String,String> devMap = (Map<String, String>) orientSqlEngine.getBmService().createModelQuery(devBm).list().get(0);
            devMap.put("C_STATE_"+mId, state);
            orientSqlEngine.getBmService().updateModelData(devBm, devMap, devId);
        }
    }
}
