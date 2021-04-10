package com.orient.modeldata.validateHandler.validator;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.modeldata.validateHandler.bean.ValidateError;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public interface IModelDataValidator {

    public List<ValidateError> doCheck(Map<String, String> dataMap, IBusinessModel model);
}
