package com.orient.modeldata.eventListener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.metamodel.metadomain.Restriction;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IEnum;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.event.UpdateModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sqlengine.internal.SqlEngineHelper;
import com.orient.sysmodel.domain.modeldata.CwmModelDataUnitEntity;
import com.orient.sysmodel.service.modeldata.IModelDataUnitService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.util.UserContextUtil;
import net.sf.json.JSONObject;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 默认模型数据保存前数据处理
 */
@Component
public class DefaultBeforeSaveModelDataListener extends OrientEventListener {

    @Autowired
    @Qualifier("BusinessModelService")
    protected IBusinessModelService businessModelService;

    @Autowired
    IModelDataUnitService modelDataUnitService;

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
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, modelId, null, EnumInter.BusinessModelEnum.Table);
        //单位处理 对于新增数据 延迟绑定数据ID
        List<CwmModelDataUnitEntity> cwmModelDataUnitEntities = converNumberColumn(businessModel, dataMap);
        //转化枚举值
        repairEnumData(businessModel, dataMap);
        eventSource.setFlowParams("numberConvers", cwmModelDataUnitEntities);

    }

    /**
     * 修复枚举值
     *
     * @param businessModel
     * @param dataMap
     */
    private void repairEnumData(IBusinessModel businessModel, Map dataMap) {
        List<IBusinessColumn> columns = businessModel.getAllBcCols();
        columns.forEach(iBusinessColumn -> {
            IColumn column = iBusinessColumn.getCol();
            String colData = CommonTools.Obj2String(dataMap.get(iBusinessColumn.getS_column_name()));
            if (!StringUtil.isEmpty(colData) && column.getCategory() == IColumn.CATEGORY_COMMON && column.getRestriction() != null) {
                Restriction restriction = column.getRestriction();
                if (restriction.getType() == 1) {
                    List<IEnum> enumList = restriction.getAllEnums();//获取枚举值
                    colData = getStaticEnumRealData(colData, enumList);
                }
            }
            dataMap.put(iBusinessColumn.getS_column_name(), colData);
        });
    }

    /**
     * @param originalValue 原始值
     * @param enumList      静态枚举列表
     * @return 获取静态枚举值中的真实值
     */
    private String getStaticEnumRealData(String originalValue, List<IEnum> enumList) {
        final String[] retVal = {originalValue};
        if (originalValue.indexOf(",") != -1 || originalValue.indexOf(";") != -1) {
            String split = originalValue.indexOf(",") != -1 ? "," : ";";
                /*多个值时*/
            String[] values = originalValue.split(split);//拆分串
            List<String> newValueList = new ArrayList<>();
            for (String value : values) {
                enumList.forEach(iEnum -> {
                    if (iEnum.getDisplayValue().equals(value) || iEnum.getValue().equals(value)) {
                        newValueList.add(iEnum.getValue());
                    }
                });
            }
            retVal[0] = CommonTools.list2String(newValueList, split);
        } else {
            enumList.forEach(iEnum -> {
                if (iEnum.getDisplayValue().equals(originalValue) || iEnum.getValue().equals(originalValue)) {
                    retVal[0] = iEnum.getValue();
                }
            });
        }
        return retVal[0];
    }

    private List<CwmModelDataUnitEntity> converNumberColumn(IBusinessModel businessModel, Map dataMap) {
        String dataId = (String) dataMap.get("ID");
        //单位信息
        List<Map<String, Object>> units = orientSqlEngine.getSysModelService().queryNumberUnit();
        List<CwmModelDataUnitEntity> retVal = new ArrayList<>();
        //有单位的数值 转化为标准单位存储
        Boolean hasUnitColumn = businessModel.getAllBcCols().stream().filter(iBusinessColumn -> !StringUtil.isEmpty(iBusinessColumn.getUnit())).count() > 0;
        if (hasUnitColumn) {
            //遍历所有字段
            businessModel.getAllBcCols().forEach(iBusinessColumn -> {
                IColumn column = iBusinessColumn.getCol();
                //如果是普通属性 并且有单位描述
                if (column.getCategory() == IColumn.CATEGORY_COMMON && !CommonTools.isNullString(column.getUnit())) {
                    //获取单位描述
                    String unitJson = column.getUnit();
                    JSONObject unit = JSONObject.fromObject(unitJson);
                    //获取单位分组
                    String unitName = unit.getString("unitName");
                    //根据单位分组 获取 单位详细信息
                    Map<String, Object> baseUnit = units.stream().filter(unitMap -> unitName.equals(unitMap.get("NAME")) && "1".equals(unitMap.get("IS_BASE")))
                            .findFirst().get();
                    //获取页面中输入的值
                    String value = (String) dataMap.get(iBusinessColumn.getS_column_name());
                    //如果不为空
                    if (!StringUtil.isEmpty(value)) {
                        //获取前台选择的单位ID
                        String unitId = (String) dataMap.get(iBusinessColumn.getS_column_name() + "_unit");
                        String standValue = value;
                        //如果为空 则说明为标准单位
                        if (StringUtil.isEmpty(unitId)) {
                            unitId = CommonTools.Obj2String(baseUnit.get("ID"));
                        } else {
                            //转化为标准数值
                            final String finalUnitId = unitId;
                            Map<String, Object> selUnitInfo = units.stream().filter(unitMap -> unitMap.get("ID").equals(finalUnitId)).findFirst().get();
                            String formulaIn = CommonTools.Obj2String(selUnitInfo.get("FORMULA_IN"));
                            standValue = SqlEngineHelper.unitCalculate(value, CommonTools.Obj2String(selUnitInfo.get("UNIT")), formulaIn);
                        }
                        //保存
                        dataMap.put(iBusinessColumn.getS_column_name(), standValue);
                        //保存映射信息
                        List<CwmModelDataUnitEntity> modelDataUnitEntities = modelDataUnitService.list(Restrictions.eq("modelId", column.getRefMatrix().getId())
                                , Restrictions.eq("dataId", dataId), Restrictions.eq("sColumnName", iBusinessColumn.getS_column_name()));
                        if (modelDataUnitEntities.isEmpty()) {
                            CwmModelDataUnitEntity cwmModelDataUnitEntity = new CwmModelDataUnitEntity();
                            cwmModelDataUnitEntity.setModelId(businessModel.getId());
                            cwmModelDataUnitEntity.setDataId(dataId);
                            cwmModelDataUnitEntity.setsColumnName(iBusinessColumn.getS_column_name());
                            cwmModelDataUnitEntity.setUnitId(unitId);
                            cwmModelDataUnitEntity.setColumnValue(value);
                            retVal.add(cwmModelDataUnitEntity);
                        } else {
                            CwmModelDataUnitEntity cwmModelDataUnitEntity = modelDataUnitEntities.get(0);
                            cwmModelDataUnitEntity.setUnitId(unitId);
                            cwmModelDataUnitEntity.setColumnValue(value);
                            modelDataUnitService.update(cwmModelDataUnitEntity);
                        }
                    }
                }
            });
        }
        return retVal;
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
