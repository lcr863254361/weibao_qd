package com.orient.modeldata.validateHandler.bean;

/**
 * Created by Administrator on 2016/8/12 0012.
 * 验证结果中出现模型错误描述
 */
public class ModelValidateError extends ValidateError{

    private String modelId;

    private String modelDisplayName;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelDisplayName() {
        return modelDisplayName;
    }

    public void setModelDisplayName(String modelDisplayName) {
        this.modelDisplayName = modelDisplayName;
    }
}
