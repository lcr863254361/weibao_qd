package com.orient.modeldata.eventListener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.modeldata.business.ModelFileBusiness;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.event.UpdateModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.modeldata.fileHandle.OrientModelFileHandle;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.domain.modeldata.CwmModelDataUnitEntity;
import com.orient.sysmodel.service.modeldata.IModelDataUnitService;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 默认模型数据保存后数据处理
 */
@Component
public class DefaultAfterSaveModelDataListener extends OrientEventListener {

    @Autowired
    @Qualifier("BusinessModelService")
    protected IBusinessModelService businessModelService;

    @Autowired
    ModelFileBusiness modelFileBusiness;

    @Autowired
    OrientModelFileHandle orientModelFileHandle;

    @Autowired
    IModelDataUnitService modelDataUnitService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        SaveModelDataEventParam eventSource = (SaveModelDataEventParam) orientEvent.getParams();
        String modelId = eventSource.getModelId();
        Map dataMap = eventSource.getDataMap();
        String dataId = (String) dataMap.get("ID");
        String userId = UserContextUtil.getUserId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, modelId, null, EnumInter.BusinessModelEnum.Table);
        //文件处理
        businessModel.getAddCols().forEach(iBusinessColumn -> {
            EnumInter colType = iBusinessColumn.getColType();
            if (EnumInter.BusinessModelEnum.BusinessColumnEnum.C_File.equals(colType) ||
                    EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Hadoop.equals(colType) ||
                    EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Ods.equals(colType)) {
                String data = (String) dataMap.get(iBusinessColumn.getS_column_name());
                if (!StringUtil.isEmpty(data)) {
                    List<Map> fileDescs = JsonUtil.json2List(data);
                    fileDescs.forEach(fileDesc -> {
                        String fileId = (String) fileDesc.get("id");
                        if (!StringUtil.isEmpty(fileId)) {
                            CwmFile cwmFile = modelFileBusiness.getFileById(fileId);
                            if (StringUtil.isEmpty(cwmFile.getConverState())) {
                                //文件上传时已做该处理，见相关Listener
                                //orientModelFileHandle.doHandleModelFile(modelId, dataId, cwmFile);
                            }
                        }
                    });
                }
            }
        });
        //绑定数据单位处理
        List<CwmModelDataUnitEntity> cwmModelDataUnitEntities = (List<CwmModelDataUnitEntity>)eventSource.getFlowParams("numberConvers");
        if(null != cwmModelDataUnitEntities){
            cwmModelDataUnitEntities.forEach(cwmModelDataUnitEntity -> {
                cwmModelDataUnitEntity.setDataId(dataId);
                modelDataUnitService.save(cwmModelDataUnitEntity);
            });
        }

    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == SaveModelDataEvent.class || SaveModelDataEvent.class.isAssignableFrom(eventType)
                || eventType == UpdateModelDataEvent.class || UpdateModelDataEvent.class.isAssignableFrom(eventType);
    }
}
