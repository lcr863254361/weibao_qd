package com.orient.modeldata.validateHandler.builder.director;

import com.orient.modeldata.validateHandler.bean.ValidateError;
import com.orient.modeldata.validateHandler.builder.IModelDataValidatorBuilder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/12 0012.
 * 构建校验器接口
 */
public interface IModelDataValidatorDirector {

    List<ValidateError> buildModelValidator(IModelDataValidatorBuilder builder);
}
