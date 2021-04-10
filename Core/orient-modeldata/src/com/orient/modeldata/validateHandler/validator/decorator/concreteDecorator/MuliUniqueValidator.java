package com.orient.modeldata.validateHandler.validator.decorator.concreteDecorator;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.modeldata.validateHandler.annotation.ValidateModelData;
import com.orient.modeldata.validateHandler.bean.ColumnValidateError;
import com.orient.modeldata.validateHandler.bean.ValidateError;
import com.orient.modeldata.validateHandler.validator.decorator.ModelDataValidatDecorator;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/12 0012.
 * 组合唯一性校验
 */
@Component
@ValidateModelData(modelNames = {"*"}, order = 20)
public class MuliUniqueValidator extends ModelDataValidatDecorator {

    private static final String errorMsgTemplate = "【COLUMN_DISPLAY】违反组合唯一性约束，数据库中已经存在相同的记录";

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Override
    public List<ValidateError> doCheck(Map<String, String> dataMap, IBusinessModel model) {
        //校验上层
        List<ValidateError> lastValidatErrors = super.doCheck(dataMap, model);
        //special validate
        List<ValidateError> selfValidatErrors = new ArrayList<>();
        //判断是否与原始值一致
        String dataId = dataMap.get("ID");
        Map<String, String> originalDataMap = new HashMap<>();
        if (!StringUtil.isEmpty(dataId)) {
            //原始值
            originalDataMap = orientSqlEngine.getBmService().createModelQuery(model).findById(dataId);
        }
        Boolean checkMultiDataUnChanged = checkMultiDataUnChanged(originalDataMap, dataMap, model);
        Boolean isValidate = !checkMultiDataUnChanged ? orientSqlEngine.getBmService().checkMultiUk(model, dataMap) : true;
        if (!isValidate) {
            model.getMultyUkColumns().forEach(iBusinessColumn -> {
                String columnSName = iBusinessColumn.getS_column_name();
                String columnId = iBusinessColumn.getId();
                String columnDisplayName = iBusinessColumn.getDisplay_name();
                ColumnValidateError validateError = new ColumnValidateError(columnId, columnSName, columnDisplayName);
                validateError.setErrorMsg(errorMsgTemplate.replaceAll("COLUMN_DISPLAY", columnDisplayName));
                selfValidatErrors.add(validateError);
            });
        }
        selfValidatErrors.addAll(lastValidatErrors);
        return selfValidatErrors;
    }

    private Boolean checkMultiDataUnChanged(Map<String, String> originalDataMap, Map<String, String> dataMap, IBusinessModel model) {
        Boolean retVal = true;
        if (null != originalDataMap) {
            final String[] originalCombineValue = {""};
            final String[] newestCombineValue = {""};
            model.getMultyUkColumns().forEach(iBusinessColumn -> {
                String columnSName = iBusinessColumn.getS_column_name();
                originalCombineValue[0] += originalDataMap.get(columnSName) + "[orient-mid]";
                newestCombineValue[0] += dataMap.get(columnSName) + "[orient-mid]";
            });
            retVal = originalCombineValue[0].equals(newestCombineValue[0]);
        }
        return retVal;
    }
}
