package com.orient.modeldata.validateHandler.builder;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.modeldata.validateHandler.bean.ValidateError;
import com.orient.modeldata.validateHandler.validator.IModelDataValidator;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/12 0012.
 * 建造者模式
 */
public interface IModelDataValidatorBuilder {

    IBusinessModel buildBusinessModel(String modelId, Integer isView);

    Map<String, String> buildModelData(Map<String, String> dataMap, IBusinessModel model);

    IModelDataValidator buildModelDataValidator(IBusinessModel model);

    List<ValidateError> beginValidate(Map<String, String> dataMap, IBusinessModel model, IModelDataValidator modelDataValidator);

}
