package com.orient.background.util;

import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.sysmodel.domain.form.ModelBtnInstanceEntity;
import com.orient.utils.CommonTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2017-03-03 8:39 AM
 */
public class ModelAndBtnValidator {

    private List<ITable> tables = new ArrayList<>();

    private List<ModelBtnInstanceEntity> modelBtnInstanceEntities = new ArrayList<>();

    public ModelAndBtnValidator(List<ITable> tables, List<ModelBtnInstanceEntity> modelBtnInstanceEntities) {
        this.tables = tables;
        this.modelBtnInstanceEntities = modelBtnInstanceEntities;
    }

    public String doValidate(String modelId, Set<String> columnIds, Set<String> buttonIds) {
        String retVal = "";
        List<String> modelIds = tables.stream().map(ITable::getId).collect(Collectors.toList());
        //first check model exists
        if (modelIds.contains(modelId)) {
            //second check column exists
            ITable table = tables.stream().filter(iTable -> iTable.getId().equals(modelId)).findFirst().get();
            List<String> correctColumnIds = table.getColumns().stream().map(IColumn::getId).collect(Collectors.toList());
            List<String> errorColumnIds = new ArrayList<>();
            columnIds.forEach(columnId -> {
                if (!correctColumnIds.contains(columnId)) {
                    errorColumnIds.add(columnId);
                }
            });
            if (CommonTools.isEmptyList(errorColumnIds)) {
                //last check button exists
                List<Long> btnIds = modelBtnInstanceEntities.stream().map(ModelBtnInstanceEntity::getId).collect(Collectors.toList());
                List<String> errorBtnIds = new ArrayList<>();
                buttonIds.forEach(btnId -> {
                    final Boolean[] contains = {false};
                    btnIds.forEach(loop -> {
                        if (loop.intValue() == Integer.valueOf(btnId).intValue()) {
                            contains[0] = true;
                        }
                    });
                    if (!contains[0]) {
                        errorBtnIds.add(btnId);
                    }
                });
                if (!CommonTools.isEmptyList(errorBtnIds)) {
                    retVal = "绑定的【" + CommonTools.list2String(errorColumnIds) + "】按钮实例不存在";
                }
            } else {
                retVal = "绑定的【" + CommonTools.list2String(errorColumnIds) + "】字段不存在";
            }


        } else {
            retVal = "绑定模型不存在";
        }
        return retVal;
    }
}
