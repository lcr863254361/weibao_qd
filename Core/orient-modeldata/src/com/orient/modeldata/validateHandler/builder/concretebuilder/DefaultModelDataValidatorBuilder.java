package com.orient.modeldata.validateHandler.builder.concretebuilder;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.modeldata.validateHandler.annotation.ValidateModelData;
import com.orient.modeldata.validateHandler.bean.ValidateError;
import com.orient.modeldata.validateHandler.builder.IModelDataValidatorBuilder;
import com.orient.modeldata.validateHandler.validator.IModelDataValidator;
import com.orient.modeldata.validateHandler.validator.decorator.ModelDataValidatDecorator;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sqlengine.internal.SqlEngineHelper;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.util.UserContextUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
@Component
public class DefaultModelDataValidatorBuilder implements IModelDataValidatorBuilder {

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    protected IBusinessModelService businessModelService;

    @Override
    public IBusinessModel buildBusinessModel(String modelId, Integer isView) {
        String userId = UserContextUtil.getUserId();
        IBusinessModel model = businessModelService.getBusinessModelById(userId, modelId, null, isView.equals(1) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table);
        return model;
    }

    @Override
    public Map<String, String> buildModelData(Map<String, String> dataMap, IBusinessModel model) {
        //单位转化
        unitTransform(dataMap, model);
        //复杂数据转化 如附件
        jsonValueTransform(dataMap, model);
        return dataMap;
    }

    /**
     * 存储为json格式的数据 空json转化为空字符串
     * 方便做空值校验
     *
     * @param dataMap
     * @param model
     */
    private void jsonValueTransform(Map<String, String> dataMap, IBusinessModel model) {
        List<IBusinessColumn> jsonValueColumns = model.getAllBcCols().stream().filter(iBusinessColumn -> {
            return iBusinessColumn.getColType().equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_File) ||
                    iBusinessColumn.getColType().equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Ods) ||
                    iBusinessColumn.getColType().equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Hadoop) ||
                    iBusinessColumn.getColType().equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Check) ||
                    iBusinessColumn.getColType().equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_SubTable) ||
                    iBusinessColumn.getColType().equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_NameValue);
        }).collect(Collectors.toList());
        jsonValueColumns.forEach(iBusinessColumn -> {
            String columnSName = iBusinessColumn.getS_column_name();
            String dataValue = dataMap.get(columnSName);
            if (!StringUtil.isEmpty(dataValue) && dataValue.equals("[]")) {
                dataMap.put(columnSName, "");
            }
        });
    }

    /**
     * 带单位的参数 转化为标准值 方便进行范围约束
     *
     * @param dataMap
     * @param model
     */
    private void unitTransform(Map<String, String> dataMap, IBusinessModel model) {
        Boolean hasUnitColumn = model.getAllBcCols().stream().filter(iBusinessColumn -> !StringUtil.isEmpty(iBusinessColumn.getUnit())).count() > 0;
        if (hasUnitColumn) {
            List<Map<String, Object>> units = orientSqlEngine.getSysModelService().queryNumberUnit();
            model.getAllBcCols().forEach(iBusinessColumn -> {
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
                        dataMap.put(iBusinessColumn.getS_column_name(), standValue);
                    }
                }
            });
        }
    }

    @Override
    public IModelDataValidator buildModelDataValidator(IBusinessModel model) {
        final IModelDataValidator[] retVal = {null};
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(com.orient.modeldata.validateHandler.validator.IModelDataValidator.class);
        IModelDataValidator beWrappedValidator = null;
        Map<Integer, IModelDataValidator> validatorLinkedHashMap = new LinkedHashMap<>();
        for (String beanName : beanNames) {
            IModelDataValidator modelDataValidator = (IModelDataValidator) OrientContextLoaderListener.Appwac.getBean(beanName);
            Class validatorClass = modelDataValidator.getClass();
            //判断是否继承
            if (com.orient.modeldata.validateHandler.validator.decorator.ModelDataValidatDecorator.class.isAssignableFrom(validatorClass)) {
                //排序
                ValidateModelData validateModelData = (ValidateModelData) validatorClass.getAnnotation(ValidateModelData.class);
                String[] modelNames = validateModelData.modelNames();
                int order = validateModelData.order();
                Boolean contains = isContained(modelNames, model.getS_table_name());
                if (contains) {
                    validatorLinkedHashMap.put(order, modelDataValidator);
                }
            } else {
                beWrappedValidator = modelDataValidator;
            }
        }
        //排序
        List<Integer> sortedKeys = validatorLinkedHashMap.keySet().stream().sorted((i1, i2) -> i1 - i2).collect(Collectors.toList());
        final IModelDataValidator finalBeWrappedValidator = beWrappedValidator;
        sortedKeys.forEach(order -> {
            ModelDataValidatDecorator modelDataValidatDecorator = (ModelDataValidatDecorator) validatorLinkedHashMap.get(order);
            if (null == retVal[0]) {
                modelDataValidatDecorator.setLastValidator(finalBeWrappedValidator);
            } else {
                ModelDataValidatDecorator tmpCheckTemplateValidator = (ModelDataValidatDecorator) retVal[0];
                modelDataValidatDecorator.setLastValidator(tmpCheckTemplateValidator);
            }
            retVal[0] = modelDataValidatDecorator;
        });
        return retVal[0];
    }

    private Boolean isContained(String[] modelNames, String s_table_name) {
        Boolean retVal = false;
        for (String modelName : modelNames) {
            if (modelName.equals("*") || modelName.equals(s_table_name)) {
                retVal = true;
            }
        }
        return retVal;
    }

    @Override
    public List<ValidateError> beginValidate(Map<String, String> dataMap, IBusinessModel model, IModelDataValidator modelDataValidator) {
        return modelDataValidator.doCheck(dataMap, model);
    }
}
