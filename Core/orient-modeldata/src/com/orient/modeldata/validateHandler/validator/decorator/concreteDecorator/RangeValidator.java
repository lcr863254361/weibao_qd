package com.orient.modeldata.validateHandler.validator.decorator.concreteDecorator;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.metamodel.metadomain.Restriction;
import com.orient.modeldata.validateHandler.annotation.ValidateModelData;
import com.orient.modeldata.validateHandler.bean.ColumnValidateError;
import com.orient.modeldata.validateHandler.bean.ValidateError;
import com.orient.modeldata.validateHandler.validator.decorator.ModelDataValidatDecorator;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/8/12 0012.
 * 数值范围校验
 */
@Component
@ValidateModelData(modelNames = {"*"}, order = 30)
public class RangeValidator extends ModelDataValidatDecorator {

    private static final String minErrorMsgTemplate = "【COLUMN_DISPLAY】的值不可小于MIN_VALUE";
    private static final String maxErrorMsgTemplate = "【COLUMN_DISPLAY】的值不可大于MAX_VALUE";

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Override
    public List<ValidateError> doCheck(Map<String, String> dataMap, IBusinessModel model) {
        //校验上层
        List<ValidateError> lastValidatErrors = super.doCheck(dataMap, model);
        //special validate
        List<ValidateError> selfValidatErrors = new ArrayList<>();
        List<IBusinessColumn> numberColumns = getNumberColumns(model);
        if (!CommonTools.isEmptyList(numberColumns)) {
            numberColumns.forEach(iBusinessColumn -> {
                String columnSName = iBusinessColumn.getS_column_name();
                String numberValue = dataMap.get(columnSName);
                String msg = validateNumberColumn(iBusinessColumn, numberValue);
                if (!StringUtil.isEmpty(msg)) {
                    String columnId = iBusinessColumn.getId();
                    String columnDisplayName = iBusinessColumn.getDisplay_name();
                    ColumnValidateError validateError = new ColumnValidateError(columnId, columnSName, columnDisplayName);
                    validateError.setErrorMsg(msg.replaceAll("COLUMN_DISPLAY", columnDisplayName));
                    selfValidatErrors.add(validateError);
                }
            });
        }
        selfValidatErrors.addAll(lastValidatErrors);
        return selfValidatErrors;
    }

    private String validateNumberColumn(IBusinessColumn iBusinessColumn, String numberValue) {
        String retVal = "";
        Restriction restriction = iBusinessColumn.getRestriction();
        if (null != restriction && !StringUtil.isEmpty(numberValue)) {
            //静态最大值最小值
            BigDecimal minValue = restriction.getMinLength();
            BigDecimal maxValue = restriction.getMaxLength();
            //动态最大值最小值
            Map<String, String> dynamicRange = orientSqlEngine.getBmService().queryResDynamicRange(restriction);
            String dynamicMinValue = dynamicRange == null ? null : dynamicRange.get("mindata");
            String dynamicMaxValue = dynamicRange == null ? null : dynamicRange.get("maxdata");
            Long combineMinValue = getCombineMinValue(minValue, dynamicMinValue);
            Long combineMaxValue = getCombineMaxValue(maxValue, dynamicMaxValue);
            //校验
            Long longValue = Long.valueOf(numberValue);
            if (null != combineMinValue && longValue < combineMinValue) {
                retVal += minErrorMsgTemplate.replaceAll("MIN_VALUE", combineMinValue.toString());
            }
            if (null != combineMaxValue && longValue > combineMaxValue) {
                retVal += StringUtil.isEmpty(retVal) ? maxErrorMsgTemplate.replaceAll("MAX_VALUE", combineMaxValue.toString()) : "\r\n" + maxErrorMsgTemplate.replaceAll("MAX_VALUE", combineMaxValue.toString());
            }
        }
        return retVal;
    }

    private Long getCombineMaxValue(BigDecimal maxValue, String dynamicMaxValue) {
        Long retVal = null;
        if (null != maxValue && !StringUtil.isEmpty(dynamicMaxValue)) {
            retVal = Math.max(maxValue.longValue(), Long.valueOf(dynamicMaxValue));
        } else if (null != maxValue) {
            retVal = maxValue.longValue();
        } else if (null != dynamicMaxValue) {
            retVal = Long.valueOf(dynamicMaxValue);
        }
        return retVal;
    }

    private Long getCombineMinValue(BigDecimal minValue, String dynamicMinValue) {
        Long retVal = null;
        if (null != minValue && !StringUtil.isEmpty(dynamicMinValue)) {
            retVal = Math.min(minValue.longValue(), Long.valueOf(dynamicMinValue));
        } else if (null != minValue) {
            retVal = minValue.longValue();
        } else if (null != dynamicMinValue) {
            retVal = Long.valueOf(dynamicMinValue);
        }
        return retVal;
    }

    private List<IBusinessColumn> getNumberColumns(IBusinessModel model) {
        return model.getAllBcCols().stream().filter(iBusinessColumn -> {
            EnumInter.BusinessModelEnum.BusinessColumnEnum colEnum = (EnumInter.BusinessModelEnum.BusinessColumnEnum) iBusinessColumn.getColType();
            return colEnum.equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Integer) || colEnum.equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_BigInteger)
                    || colEnum.equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Decimal) || colEnum.equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Float)
                    || colEnum.equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Double);
        }).collect(Collectors.toList());
    }


}
