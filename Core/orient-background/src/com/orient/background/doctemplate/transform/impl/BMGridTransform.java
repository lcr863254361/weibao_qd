package com.orient.background.doctemplate.transform.impl;

import com.orient.background.doctemplate.bean.DocGridColumn;
import com.orient.background.doctemplate.bean.DocGridData;
import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.background.doctemplate.transform.DocTransformRegister;
import com.orient.background.doctemplate.transform.IDocTransform;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.utils.CommonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * transform model grid data
 *
 * @author panduanduan
 * @create 2016-12-07 8:46 PM
 */
@Component
public class BMGridTransform implements IDocTransform<IBusinessModel> {

    @Autowired
    DocTransformRegister docTransformRegister;

    @Override
    public DocHandlerData doTransform(IBusinessModel target, List<Map> dataSource) {
        DocHandlerData<DocGridData> retVal = new DocHandlerData<>();
        retVal.setOriginalData(dataSource);
        DocGridData docGridData = new DocGridData();
        //transform
        target.getAllBcCols().forEach(iBusinessColumn -> {
            DocGridColumn column = new DocGridColumn(iBusinessColumn.getS_column_name(), iBusinessColumn.getDisplay_name());
            docGridData.getColumns().add(column);
        });
        List<Map<String, DocHandlerData>> afterTransform = new ArrayList<>();
        dataSource.forEach(dataMap -> {
            Map<String, DocHandlerData> tmp = new LinkedHashMap<>();
            dataMap.forEach((key, value) -> {
                if (!"ID".equals(CommonTools.Obj2String(key).toUpperCase())) {
                    IBusinessColumn businessColumn = target.getBusinessColumnBySName(CommonTools.Obj2String(key));
                    if (null != businessColumn) {
                        String colType = businessColumn.getColType().toString();
                        //compatible
                        List<Map> columnData = new ArrayList<>();
                        columnData.add(dataMap);
                        DocHandlerData docHandlerData = docTransformRegister.getDocTransform(colType).doTransform(businessColumn, columnData);
                        tmp.put(CommonTools.Obj2String(key), docHandlerData);
                    }
                }
            });
            afterTransform.add(tmp);
        });
        docGridData.setDataList(afterTransform);
        retVal.setAfterDataChange(docGridData);
        return retVal;
    }
}
