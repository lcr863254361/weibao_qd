package com.orient.modeldata.eventListener;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.modeldata.event.GetModelDescEvent;
import com.orient.modeldata.eventParam.GetModelDescEventParam;
import com.orient.sysmodel.domain.form.ModelGridViewEntity;
import com.orient.sysmodel.operationinterface.IMatrixRight;
import com.orient.sysmodel.service.form.IModelGridViewService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.modelDesc.column.ColumnDesc;
import com.orient.web.modelDesc.model.OrientModelDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取自定义模型字段描述信息
 */
@Component
public class GetCustomModelColumnDescListener extends OrientEventListener {
    @Autowired
    IModelGridViewService modelGridViewService;


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
        //获取模型ID
        String modelId = param.getModelId();
        //获取模板ID
        String templateId = param.getTemplateId();
        //获取模型描述
        OrientModelDesc orientModelDesc = param.getOrientModelDesc();
        //根据DS设置 整理表格、新增、修改、删除、查询等字段描述
        if (!StringUtil.isEmpty(templateId)) {
            IBusinessModel businessModel = (IBusinessModel) param.getFlowParams("BM");
            IMatrixRight matrixRight = businessModel.getMatrixRight();
            //获取是否有表访问权限设置
            Boolean isModelRightSetted = matrixRight.getModelRightSet();
            Long modelGridId = Long.valueOf(templateId);
            //获取自定义表格描述
            ModelGridViewEntity modelGridViewEntity = modelGridViewService.getById(modelGridId);
            //表格字段描述
            List<String> gridColumnIds = removeInvalidColumn(modelGridViewEntity.getDisplayfield() == null ? new ArrayList<>() : CommonTools.arrayToList(modelGridViewEntity.getDisplayfield().split(",")), orientModelDesc.getColumns());
            orientModelDesc.setListColumnDesc(unionList(gridColumnIds, matrixRight.getListColIds(), isModelRightSetted));
            //获取新增描述
            List<String> addColumnIds = removeInvalidColumn(modelGridViewEntity.getAddfield() == null ? new ArrayList<>() : CommonTools.arrayToList(modelGridViewEntity.getAddfield().split(",")), orientModelDesc.getColumns());
            orientModelDesc.setCreateColumnDesc(unionList(addColumnIds, matrixRight.getAddColIds(), isModelRightSetted));
            //获取修改描述
            List<String> modiftyColumnIds = removeInvalidColumn(modelGridViewEntity.getEditfield() == null ? new ArrayList<>() : CommonTools.arrayToList(modelGridViewEntity.getEditfield().split(",")), orientModelDesc.getColumns());
            orientModelDesc.setModifyColumnDesc(unionList(modiftyColumnIds, matrixRight.getModifyColIds(), isModelRightSetted));
            //获取查询字段
            List<String> searchColumnIds = removeInvalidColumn(modelGridViewEntity.getQueryfield() == null ? new ArrayList<>() : CommonTools.arrayToList(modelGridViewEntity.getQueryfield().split(",")), orientModelDesc.getColumns());
            orientModelDesc.setQueryColumnDesc(searchColumnIds);
            //获取详细字段
            List<String> detailColumnIds = removeInvalidColumn(modelGridViewEntity.getDetailfield() == null ? new ArrayList<>() : CommonTools.arrayToList(modelGridViewEntity.getDetailfield().split(",")), orientModelDesc.getColumns());
            orientModelDesc.setDetailColumnDesc(unionList(detailColumnIds, matrixRight.getDetailColIds(), isModelRightSetted));
        }
    }

    private List<String> removeInvalidColumn(List<String> columnIds, List<ColumnDesc> columns) {
        List<String> toRemoveColumnIds = new ArrayList<>();
        List<Integer> correctColumnIds = new ArrayList<>();
        columns.forEach(columnDesc -> correctColumnIds.add(Integer.valueOf(columnDesc.getId())));
        columnIds.forEach(columnId -> {
            int intValue = Integer.valueOf(columnId).intValue();
            final Boolean[] cached = {false};
            correctColumnIds.forEach(correctColumnId -> {
                if (intValue == Integer.valueOf(correctColumnId).intValue()) {
                    cached[0] = true;
                }
            });
            if (!cached[0]) {
                toRemoveColumnIds.add(columnId);
            }
        });
        //移除
        columnIds.removeAll(toRemoveColumnIds);
        return columnIds;
    }

    private List<String> unionList(List<String> originalColumnIds, List<String> filterByRightColumnIds, Boolean isModelRightSetted) {
        if (isModelRightSetted) {
            originalColumnIds.retainAll(filterByRightColumnIds);
        }
        return originalColumnIds;
    }
}
