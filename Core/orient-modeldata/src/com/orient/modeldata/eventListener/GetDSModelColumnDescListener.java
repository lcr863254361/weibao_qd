package com.orient.modeldata.eventListener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.modeldata.event.GetModelDescEvent;
import com.orient.modeldata.eventParam.GetModelDescEventParam;
import com.orient.sysmodel.operationinterface.IMatrixRight;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.modelDesc.model.OrientModelDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 获取DS中默认设定的相关字段描述
 *
 * @author enjoy
 * @creare 2016-04-15 16:27
 */
@Component
public class GetDSModelColumnDescListener extends OrientEventListener {
    @Autowired
    IBusinessModelService businessModelService;


    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == GetModelDescEvent.class || GetModelDescEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        //获取事件参数
        GetModelDescEventParam param = (GetModelDescEventParam) orientEvent.getParams();
        //获取模板ID
        String templateId = param.getTemplateId();
        //获取模型描述
        OrientModelDesc orientModelDesc = param.getOrientModelDesc();
        //根据DS设置 整理表格、新增、修改、删除、查询等字段描述
        if (StringUtil.isEmpty(templateId)) {
            IBusinessModel businessModel = (IBusinessModel) param.getFlowParams("BM");
            IMatrixRight matrixRight = businessModel.getMatrixRight();
            //获取是否有表访问权限设置
            Boolean isModelRightSetted = matrixRight.getModelRightSet();
            //表格字段描述
            List<String> gridColumnIds = businessModel.getGridShowColumns().stream().map(IBusinessColumn::getId).collect(Collectors.toList());
            orientModelDesc.setListColumnDesc(unionList(gridColumnIds, matrixRight.getListColIds(), isModelRightSetted));
            //新增字段描述
            List<String> addColumnIds = businessModel.getAddCols().stream().map(IBusinessColumn::getId).collect(Collectors.toList());
            orientModelDesc.setCreateColumnDesc(unionList(addColumnIds, matrixRight.getAddColIds(), isModelRightSetted));
            //修改字段
            List<String> modifyColumnIds = businessModel.getEditCols().stream().map(IBusinessColumn::getId).collect(Collectors.toList());
            orientModelDesc.setModifyColumnDesc(unionList(modifyColumnIds, matrixRight.getModifyColIds(), isModelRightSetted));
            //查询字段
            List<String> searchColumnIds = businessModel.getSearchCols().stream().map(IBusinessColumn::getId).collect(Collectors.toList());
            orientModelDesc.setQueryColumnDesc(searchColumnIds);
            //详细字段
            List<String> detailColumnIds = businessModel.getDetailCols().stream().map(IBusinessColumn::getId).collect(Collectors.toList());
            orientModelDesc.setDetailColumnDesc(unionList(detailColumnIds, matrixRight.getDetailColIds(), isModelRightSetted));


        }
    }

    private List<String> unionList(List<String> originalColumnIds, List<String> filterByRightColumnIds, Boolean isModelRightSetted) {
        if (isModelRightSetted) {
            originalColumnIds.retainAll(filterByRightColumnIds);
        }
        return originalColumnIds;
    }
}
