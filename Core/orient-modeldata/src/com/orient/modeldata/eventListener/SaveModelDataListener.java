package com.orient.modeldata.eventListener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.bean.impl.BusinessColumn;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.metamodel.metadomain.AbstractRestriction;
import com.orient.metamodel.metadomain.Enum;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.dao.flow.MJDao;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by enjoy on 2016/3/23 0023.
 */
@Component
public class SaveModelDataListener extends OrientEventListener {

    @Autowired
    @Qualifier("BusinessModelService")
    protected IBusinessModelService businessModelService;

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    private MJDao mjDao;

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
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId,modelId,null, EnumInter.BusinessModelEnum.Table);
        String dataId = (String) dataMap.get("ID");
        //如果存在密级字段就复制到DS密级字段
        for(IBusinessColumn ibusinessColumn : businessModel.getAllBcCols()){
            BusinessColumn businessColumn = (BusinessColumn)ibusinessColumn;
            if(businessColumn.getCol().getName().equalsIgnoreCase("C_MJ")){
                String filterParam = (String) dataMap.get("C_MJ_"+modelId);
                dataMap.put("TableName", businessModel.getMatrix().getMainTable().getTableName());
                dataMap.put("SYS_SECRECY", getSysMJValue(filterParam));
                dataMap.put("HASMJ", "YES");
            }
        }

        //容错
        if (eventSource.isCreateData()) {
            String pk = orientSqlEngine.getBmService().insertModelData(businessModel, dataMap);
            dataMap.put("ID", pk);
        } else{
            orientSqlEngine.getBmService().updateModelData(businessModel, dataMap, dataId);
        }

        //自建密级字段拷贝到DS字段中
        String hasMJ = (String) dataMap.get("HASMJ");
        if(hasMJ!=null && !hasMJ.isEmpty()){
            String secrecyValue = (String) dataMap.get("SYS_SECRECY");
            mjDao.CopyMJToSysSecrecy(businessModel.getMatrix().getMainTable().getTableName(), secrecyValue, dataMap.get("ID").toString());
        }

    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == SaveModelDataEvent.class || SaveModelDataEvent.class.isAssignableFrom(eventType);
    }

    /**
     * 根据自建密级字段（MJ_ModelId）查询系统枚举字段value
     * @param type
     * @return
     */
    private String getSysMJValue(String type){
        switch (type){
            case "机密":
                return "C4CA4238A0B923820DCC509A6F75849B";
            case "秘密":
                return "C81E728D9D4C2F636F067F89CC14862C";
            case "内部":
                return "ECCBC87E4B5CE2FE28308FD9F2A7BAF3";
            case "非密":
                return "A87FF679A2F3E71D9181A67B7542122C";
            case "公开":
                return "E4DA3B7FBBCE2345D7772B0674A318D5";
            default:
                return "";
        }
    }

}
