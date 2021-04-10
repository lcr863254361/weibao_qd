package com.orient.pvm.validate.templateparser.impl;

import com.orient.pvm.validate.templateparser.TemplateParser;
import com.orient.utils.CommonTools;
import com.orient.utils.ExcelUtil.reader.DataEntity;
import com.orient.utils.ExcelUtil.reader.FieldEntity;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
@Component(value = "defaultTemplateParser")
public class DefaultTemplateParser implements TemplateParser {

    @Override
    public List<Map<String, String>> getCheckItems(TableEntity tableEntity) {
        List<Map<String, String>> retVal = new ArrayList<>();
        //检查数据默认在第二个sheet中
        List<TableEntity> subTableEntities = tableEntity.getSubTableEntityList();
        if (!CommonTools.isEmptyList(subTableEntities)) {
            subTableEntities.forEach(subTableEntity -> {
                List<DataEntity> checkDatas = subTableEntity.getDataEntityList();
                checkDatas.forEach(checkData -> {
                    List<FieldEntity> fieldEntities = checkData.getFieldEntityList();
                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("SHEETNAME", subTableEntity.getName());
                    fieldEntities.forEach(fieldEntity -> {
                        dataMap.put(fieldEntity.getName(), fieldEntity.getValue());
                    });
                    retVal.add(dataMap);
                });
            });
        }
        return retVal;
    }

    @Override
    public String getModelName(TableEntity tableEntity) {
        String retVal = "";
        List<DataEntity> checkDescData = tableEntity.getDataEntityList();
        if (checkDescData.size() > 0) {
            DataEntity firstRow = checkDescData.get(0);
            List<FieldEntity> fieldEntities = firstRow.getFieldEntityList();
            //第二列为模型名称 ID为默认第一列
            if (!CommonTools.isEmptyList(fieldEntities) && fieldEntities.size() > 1) {
                retVal = fieldEntities.get(1).getValue();
            }
        }
        return retVal;
    }

    @Override
    public List<String> getSigners(TableEntity tableEntity) {
        List<String> singers = new ArrayList<>();
        List<DataEntity> checkDescData = tableEntity.getDataEntityList();
        checkDescData.forEach(dataEntity -> {
            List<FieldEntity> fieldEntities = dataEntity.getFieldEntityList();
            //第二列为模型名称 ID为默认第一列
            if (!CommonTools.isEmptyList(fieldEntities) && fieldEntities.size() > 2) {
                String singer = fieldEntities.get(2).getValue();
                if (!StringUtil.isEmpty(singer)) {
                    singers.add(singer);
                }
            }
        });
        return singers;
    }

    @Override
    public String getRemark(TableEntity tableEntity) {
        String retVal = "";
        List<DataEntity> checkDescData = tableEntity.getDataEntityList();
        if (checkDescData.size() > 0) {
            DataEntity firstRow = checkDescData.get(0);
            List<FieldEntity> fieldEntities = firstRow.getFieldEntityList();
            //第二列为模型名称 ID为默认第一列
            if (!CommonTools.isEmptyList(fieldEntities) && fieldEntities.size() > 3) {
                retVal = fieldEntities.get(3).getValue();
            }
        }
        return retVal;
    }
}
