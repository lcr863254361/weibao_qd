package com.orient.modeldata.validateHandler.validator.decorator.concreteDecorator;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.modeldata.validateHandler.annotation.ValidateModelData;
import com.orient.modeldata.validateHandler.bean.ColumnValidateError;
import com.orient.modeldata.validateHandler.bean.ValidateError;
import com.orient.modeldata.validateHandler.validator.decorator.ModelDataValidatDecorator;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/8/12 0012.
 * 最大长度校验
 */
@Component
@ValidateModelData(modelNames = {"*"}, order = 50)
public class MaxLengthValidator extends ModelDataValidatDecorator {

    private static final String errorMsgTemplate = "【COLUMN_DISPLAY】字段超出最大长度，最大长度为MAX_LENGTH";

    @Override
    public List<ValidateError> doCheck(Map<String, String> dataMap, IBusinessModel model) {
        //校验上层
        List<ValidateError> lastValidatErrors = super.doCheck(dataMap, model);
        //special validate
        List<ValidateError> selfValidatErrors = new ArrayList<>();
        //只对字符串类型进行校验
        List<IBusinessColumn> stringColumns = getStringColumns(model);
        if (!CommonTools.isEmptyList(stringColumns)) {
            stringColumns.forEach(iBusinessColumn -> {
                String columnSName = iBusinessColumn.getS_column_name();
                String dataValue = dataMap.get(columnSName);
                Long maxLength = iBusinessColumn.getCol().getMaxLength();
                if (!StringUtil.isEmpty(dataValue) && dataValue.length() > maxLength) {
                    String columnId = iBusinessColumn.getId();
                    String columnDisplayName = iBusinessColumn.getDisplay_name();
                    ColumnValidateError validateError = new ColumnValidateError(columnId, columnSName, columnDisplayName);
                    validateError.setErrorMsg(errorMsgTemplate.replaceAll("COLUMN_DISPLAY", columnDisplayName).replaceAll("MAX_LENGTH", String.valueOf(maxLength.intValue())));
                    selfValidatErrors.add(validateError);
                }
            });
        }
        selfValidatErrors.addAll(lastValidatErrors);
        return selfValidatErrors;
    }

    private List<IBusinessColumn> getStringColumns(IBusinessModel model) {
        List<IBusinessColumn> retVal = model.getAllBcCols().stream().filter(iBusinessColumn -> iBusinessColumn.getColType().equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Simple)).collect(Collectors.toList());
        return retVal;
    }
}
