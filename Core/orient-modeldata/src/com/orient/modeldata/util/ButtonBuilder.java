package com.orient.modeldata.util;

import com.orient.background.bean.ModelBtnInstanceWrapper;
import com.orient.sysmodel.domain.form.ModelBtnInstanceEntity;
import com.orient.sysmodel.operationinterface.IMatrixRight;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据权限过滤按钮
 *
 * @author enjoy
 * @creare 2016-04-18 10:06
 */
public class ButtonBuilder {

    /**
     * 根据权限说明 过滤按钮
     *
     * @param modelBtnWrapEntityList
     * @param matrixRight
     * @return
     */
    public static List<ModelBtnInstanceEntity> filterButtonByRight(List<ModelBtnInstanceWrapper> modelBtnWrapEntityList, IMatrixRight matrixRight) {
        List<ModelBtnInstanceEntity> retVal = new ArrayList<>();
        modelBtnWrapEntityList.forEach(modelBtnInstanceWrapper -> {
            if (matrixRight.getBtnTypeIds().contains(modelBtnInstanceWrapper.getBelongModelBtnType().getId())) {
                retVal.add(modelBtnInstanceWrapper.getModelBtnInstanceEntity());
            }
        });
        return retVal;
    }
}
