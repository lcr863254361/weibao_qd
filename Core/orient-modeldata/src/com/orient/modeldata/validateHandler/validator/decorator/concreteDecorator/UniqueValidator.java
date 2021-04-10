package com.orient.modeldata.validateHandler.validator.decorator.concreteDecorator;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.modeldata.validateHandler.annotation.ValidateModelData;
import com.orient.modeldata.validateHandler.bean.ColumnValidateError;
import com.orient.modeldata.validateHandler.bean.ValidateError;
import com.orient.modeldata.validateHandler.validator.decorator.ModelDataValidatDecorator;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/8/12 0012.
 * 唯一性校验
 */
@Component
@ValidateModelData(modelNames = {"*"}, order = 10)
public class UniqueValidator extends ModelDataValidatDecorator {

    private static final String errorMsgTemplate = "【COLUMN_DISPLAY】违反唯一性约束，数据库中已经存在相同的记录";

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Override
    public List<ValidateError> doCheck(Map<String, String> dataMap, IBusinessModel model) {
        //校验上层
        List<ValidateError> lastValidatErrors = super.doCheck(dataMap, model);
        //special validate
        List<ValidateError> selfValidatErrors = new ArrayList<>();
        //获取非空字段集合
        String dataId = dataMap.get("ID");
        Map<String, String> originalDataMap = new HashMap<>();
        if (!StringUtil.isEmpty(dataId)) {
            //原始值
            originalDataMap = orientSqlEngine.getBmService().createModelQuery(model).findById(dataId);
        }
        List<IBusinessColumn> uniqusColumns = getUniqusColumns(model);
        if (!CommonTools.isEmptyList(uniqusColumns)) {
            final Map<String, String> finalOriginalDataMap = originalDataMap;
            uniqusColumns.forEach(iBusinessColumn -> {
                String columnSName = iBusinessColumn.getS_column_name();
                String dataValue = dataMap.get(columnSName);
                String originalValue = finalOriginalDataMap.get(columnSName);
                if (!isUnique(originalValue, dataValue, iBusinessColumn)) {
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

    /**
     * 校验是否唯一
     *
     * @param dataValue
     * @param column
     * @return
     */
    private boolean isUnique(String originalValue, String dataValue, IBusinessColumn column) {
        Boolean retVal = true;
        if (StringUtil.isEmpty(originalValue) || (!StringUtil.isEmpty(dataValue) && !originalValue.equals(dataValue))) {
            IBusinessModel model = column.getParentModel();
            String originalReserveFilter = CommonTools.null2String(model.getReserve_filter());
            model.setReserve_filter(originalReserveFilter + " AND " + column.getS_column_name() + " = '" + dataValue + "'");
            Long count = orientSqlEngine.getBmService().createModelQuery(model).count();
            retVal = count > 0 ? false : true;
            model.setReserve_filter(originalReserveFilter);
        }
        return retVal;
    }

    private List<IBusinessColumn> getUniqusColumns(IBusinessModel model) {
        return model.getAllBcCols().stream().filter(iBusinessColumn -> iBusinessColumn.getCol().getIsOnly() == null || iBusinessColumn.getCol().getIsOnly().toLowerCase().equals("false") ? false : true).collect(Collectors.toList());
    }
}
