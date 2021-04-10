package com.orient.modeldata.validateHandler.validator.impl;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.modeldata.validateHandler.bean.ValidateError;
import com.orient.modeldata.validateHandler.validator.IModelDataValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/12 0012.
 * 被装饰检查器
 * 可做默认检查
 * 最后执行
 */
@Component
public class DefaultModelDataValidator implements IModelDataValidator {

    @Override
    public List<ValidateError> doCheck(Map<String, String> dataMap, IBusinessModel model) {
        return new ArrayList<>();
    }
}
