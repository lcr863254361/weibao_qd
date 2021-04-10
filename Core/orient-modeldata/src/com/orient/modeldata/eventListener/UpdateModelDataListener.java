package com.orient.modeldata.eventListener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.event.UpdateModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.util.UserContextUtil;
import org.jasig.cas.client.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by enjoy on 2016/3/23 0023.
 */
@Component
public class UpdateModelDataListener extends OrientEventListener {

    @Autowired
    @Qualifier("BusinessModelService")
    protected IBusinessModelService businessModelService;

    @Autowired
    protected ISqlEngine orientSqlEngine;


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        SaveModelDataEventParam eventSource = (SaveModelDataEventParam) orientEvent.getParams();
        String modelId = eventSource.getModelId();
        Map dataMap = eventSource.getDataMap();
        String userId = UserContextUtil.getUserId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, modelId,null, EnumInter.BusinessModelEnum.Table);
        String dataId = (String) dataMap.get("ID");
        //如果存在密级字段就复制到DS密级字段
        List<IBusinessColumn> iBusinessColumnList = businessModel.getAllBcCols();
        for(IBusinessColumn iBusinessColumn : iBusinessColumnList){
            if(iBusinessColumn.getCol().getColumnName().equalsIgnoreCase("C_MJ")){
                iBusinessColumn.getCol().getDataValue();
                dataMap.put("SYS_SECRECY", iBusinessColumn.getCol().getDataValue());
            }
        }

        //容错
        if (StringUtil.isEmpty(dataId)) {
            orientSqlEngine.getBmService().insertModelData(businessModel, dataMap);
        } else{
            Map<String, String> oriDataMap = orientSqlEngine.getBmService().createModelQuery(businessModel).findById(dataId);
            eventSource.setOriDataMap(oriDataMap);
            orientSqlEngine.getBmService().updateModelData(businessModel, dataMap, dataId);
        }
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == UpdateModelDataEvent.class || UpdateModelDataEvent.class.isAssignableFrom(eventType);
    }
}
