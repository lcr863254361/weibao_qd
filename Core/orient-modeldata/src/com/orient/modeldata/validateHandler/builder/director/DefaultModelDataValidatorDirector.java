package com.orient.modeldata.validateHandler.builder.director;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.modeldata.validateHandler.bean.ValidateError;
import com.orient.modeldata.validateHandler.builder.IModelDataValidatorBuilder;
import com.orient.modeldata.validateHandler.validator.IModelDataValidator;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class DefaultModelDataValidatorDirector implements IModelDataValidatorDirector {

    private String modelId;

    private Map<String, String> dataMap;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public Map<String, String> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, String> dataMap) {
        this.dataMap = dataMap;
    }

    public DefaultModelDataValidatorDirector(String modelId, Map<String, String> dataMap) {
        this.modelId = modelId;
        this.dataMap = dataMap;
    }

    @Override
    public List<ValidateError> buildModelValidator(IModelDataValidatorBuilder builder) {
        //只有模型可以校验
        IBusinessModel model = builder.buildBusinessModel(modelId, 0);
        //如果需要 转化数据
        Map<String, String> realDataMap = builder.buildModelData(dataMap, model);
        //构建装饰模式校验器
        IModelDataValidator modelDataValidator = builder.buildModelDataValidator(model);
        //开始校验
        List<ValidateError> retVal = builder.beginValidate(realDataMap, model, modelDataValidator);
        return retVal;
    }
}
