package com.orient.modeldata.validateHandler.validator.decorator;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.modeldata.validateHandler.bean.ValidateError;
import com.orient.modeldata.validateHandler.validator.IModelDataValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/9 0009.
 * 装饰模式校验基类
 */
public class ModelDataValidatDecorator implements IModelDataValidator {

    private IModelDataValidator lastValidator;

    public void setLastValidator(IModelDataValidator lastValidator) {
        this.lastValidator = lastValidator;
    }

    @Override
    public List<ValidateError> doCheck(Map<String, String> dataMap, IBusinessModel model) {
        if (null != lastValidator) {
            return lastValidator.doCheck(dataMap, model);
        }
        return new ArrayList<>();
    }
}
