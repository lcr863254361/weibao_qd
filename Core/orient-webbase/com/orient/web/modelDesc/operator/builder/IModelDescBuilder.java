package com.orient.web.modelDesc.operator.builder;

import com.orient.web.modelDesc.model.OrientModelDesc;

/**
 * Created by enjoy on 2016/4/1 0001.
 */
public interface IModelDescBuilder {

    OrientModelDesc getModelDescByModelId(String modelId, String isView);
}
