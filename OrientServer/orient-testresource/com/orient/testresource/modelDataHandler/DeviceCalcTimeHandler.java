package com.orient.testresource.modelDataHandler;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
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
@ModelOperateExtend(modelNames = {"T_JLSB"}, schemaName = "试验资源管理")
public class DeviceCalcTimeHandler implements ModelDataOperate {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    @Qualifier("BusinessModelService")
    protected IBusinessModelService businessModelService;

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Override
    public void beforeAdd(IBusinessModel bm, Map<String, String> dataMap) {
        String modelId = bm.getId();
        String startTime = dataMap.get("C_SCJYRQ_"+modelId);
        String period = dataMap.get("C_JYZQ_"+modelId);
        if(startTime!=null && !"".equals(startTime) && period!=null && !"".equals(period)) {
            try {
                Date startDate = format.parse(startTime);
                long during = Long.valueOf(period);
                long endTS = startDate.getTime()+during*24*3600*1000;
                String endTime = format.format(new Date(endTS));
                dataMap.put("C_XCJYRQ_"+modelId, endTime);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new OrientBaseAjaxException("", "上次校验日期和校验周期不可为空");
        }
    }

    @Override
    public void afterAdd(IBusinessModel bm, Map<String, String> dataMap, String id) {

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
        String modelId = bm.getId();
        String startTime = dataMap.get("C_SCJYRQ_"+modelId);
        String period = dataMap.get("C_JYZQ_"+modelId);
        if(startTime!=null && !"".equals(startTime) && period!=null && !"".equals(period)) {
            try {
                Date startDate = format.parse(startTime);
                long during = Long.valueOf(period);
                long endTS = startDate.getTime()+during*24*3600*1000;
                String endTime = format.format(new Date(endTS));
                dataMap.put("C_XCJYRQ_"+modelId, endTime);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new OrientBaseAjaxException("", "上次校验日期和校验周期不可为空");
        }
    }

    @Override
    public void afterUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId, Boolean result) {

    }
}
