package com.orient.background.bean;

import com.orient.sysmodel.domain.form.ModelBtnInstanceEntity;
import com.orient.sysmodel.domain.form.ModelBtnTypeEntity;

import java.io.Serializable;

/**
 * 按钮实例包装类
 *
 * @author enjoy
 * @creare 2016-04-18 10:11
 */
public class ModelBtnInstanceWrapper implements Serializable {

    private ModelBtnInstanceEntity modelBtnInstanceEntity;

    //增加按钮类型描述
    private ModelBtnTypeEntity belongModelBtnType;

    public ModelBtnTypeEntity getBelongModelBtnType() {
        return belongModelBtnType;
    }

    public void setBelongModelBtnType(ModelBtnTypeEntity belongModelBtnType) {
        this.belongModelBtnType = belongModelBtnType;
    }

    public ModelBtnInstanceEntity getModelBtnInstanceEntity() {
        return modelBtnInstanceEntity;
    }

    public void setModelBtnInstanceEntity(ModelBtnInstanceEntity modelBtnInstanceEntity) {
        this.modelBtnInstanceEntity = modelBtnInstanceEntity;
    }
}
