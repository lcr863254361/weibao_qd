package com.orient.web.modelDesc.operator.builder.impl;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.web.modelDesc.column.ColumnDesc;
import com.orient.web.modelDesc.model.OrientModelDesc;
import com.orient.web.modelDesc.operator.builder.IColumnDescBuilder;
import com.orient.web.modelDesc.operator.builder.IModelDescBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 模型描述构造器
 *
 * @author enjoy
 * @creare 2016-04-01 14:03
 */
@Component
public class ModelDescBuilder implements IModelDescBuilder {

    //不同用途模型 展现内容不一样
    public static final String MODEL_PURPOSE_ALL = "all";
    //用于查询
    public static final String MODEL_PURPOSE_QUERY = "query";
    //用于新增
    public static final String MODEL_PURPOSE_ADD = "add";
    //用户详细
    public static final String MODEL_PURPOSE_DETAIL = "detail";
    //用户修改
    public static final String MODEL_PURPOSE_EDIT = "edit";
    //用于列表展现
    public static final String MODEL_PURPOSE_LIST = "list";

    @Autowired
    IColumnDescBuilder iColumnDescBuilder;

    @Autowired
    IBusinessModelService businessModelService;

    @Override
    public OrientModelDesc getModelDescByModelId(String modelId, String isView) {

        OrientModelDesc orientModelDesc = null;
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        //获取DS模型
        IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId, modelTypeEnum);
        if (null != businessModel) {
            orientModelDesc = new OrientModelDesc();
            //注入模型基本属性
            orientModelDesc.setModelId(businessModel.getId());
            orientModelDesc.setText(businessModel.getDisplay_name());
            orientModelDesc.setDbName(businessModel.getS_table_name());
            List<IBusinessColumn> showCols = businessModel.getRefShowColumns();
            if(showCols!=null && showCols.size()>0) {
                orientModelDesc.setShowColumn(showCols.get(0).getS_column_name());
            }
            //注入字段属性
            List<ColumnDesc> columnDescs = new ArrayList<>();
            List<IBusinessColumn> businessColumns = businessModel.getAllBcCols();
            businessColumns.forEach(businessColumn -> {
                ColumnDesc columnDesc = iColumnDescBuilder.buildColumnDesc(businessColumn);
                columnDescs.add(columnDesc);
            });
            orientModelDesc.setColumns(columnDescs);
        }
        return orientModelDesc;
    }
}
