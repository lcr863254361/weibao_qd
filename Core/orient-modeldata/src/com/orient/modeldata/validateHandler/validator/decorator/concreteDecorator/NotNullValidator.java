package com.orient.modeldata.validateHandler.validator.decorator.concreteDecorator;

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
 * 非空校验
 */
@Component
@ValidateModelData(modelNames = {"*"}, order = 0)
public class NotNullValidator extends ModelDataValidatDecorator {

    private static final String errorMsgTemplate = "【COLUMN_DISPLAY】字段为必填项，不可为空";

    @Override
    public List<ValidateError> doCheck(Map<String, String> dataMap, IBusinessModel model) {
        //校验上层
        List<ValidateError> lastValidatErrors = super.doCheck(dataMap, model);
        //special validate
        List<ValidateError> selfValidatErrors = new ArrayList<>();
        //获取非空字段集合
        List<IBusinessColumn> notNullColumns = getNotNullColumns(model);
        if (!CommonTools.isEmptyList(notNullColumns)) {
            notNullColumns.forEach(iBusinessColumn -> {
                String columnSName = iBusinessColumn.getS_column_name();
                String dataValue = dataMap.get(columnSName);
                if (StringUtil.isEmpty(dataValue)) {
                    String columnId = iBusinessColumn.getId();
                    String columnDisplayName = iBusinessColumn.getDisplay_name();
                    ColumnValidateError validateError = new ColumnValidateError(columnId, columnSName, columnDisplayName);
                    validateError.setErrorMsg(errorMsgTemplate.replaceAll("COLUMN_DISPLAY", columnDisplayName));
                    selfValidatErrors.add(validateError);
                }
            });
        }
        selfValidatErrors.addAll(lastValidatErrors);
        return selfValidatErrors;
    }

    private List<IBusinessColumn> getNotNullColumns(IBusinessModel model) {
        //关系属性与普通属性 非空字段不一致
        List<IBusinessColumn> retVal = model.getAllBcCols().stream().filter(iBusinessColumn ->
                        (iBusinessColumn.getCol().getIsNull() == null || iBusinessColumn.getCol().getIsNull().toLowerCase().equals("false") ? false : true) || (iBusinessColumn.getCol().getIsNeed() == null || iBusinessColumn.getCol().getIsNeed().toLowerCase().equals("false") ? false : true)
        ).collect(Collectors.toList());
        return retVal;
    }
}

