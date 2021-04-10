package com.orient.background.eventParam;

import com.orient.sysmodel.domain.form.ModelFormViewEntity;
import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * Created by enjoy on 2016/3/22 0022.
 */
public class SaveModelViewEventParam extends OrientEventParams {

    ModelFormViewEntity modelFormViewEntity;

    public ModelFormViewEntity getModelFormViewEntity() {
        return modelFormViewEntity;
    }

    public void setModelFormViewEntity(ModelFormViewEntity modelFormViewEntity) {
        this.modelFormViewEntity = modelFormViewEntity;
    }
}
