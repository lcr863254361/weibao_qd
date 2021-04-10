package com.orient.background.doctemplate.transform.impl;

import com.orient.background.doctemplate.bean.DocGridColumn;
import com.orient.background.doctemplate.bean.DocGridData;
import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.background.doctemplate.transform.IDocTransform;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.utils.CommonTools;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2016-12-07 9:17 PM
 */
@Component
public class TableColumnTransform implements IDocTransform<IBusinessColumn> {

    @Override
    public DocHandlerData doTransform(IBusinessColumn target, List<Map> dataSource) {
        DocHandlerData<DocGridData> retVal = new DocHandlerData<>();
        retVal.setOriginalData(dataSource);
        DocGridData docGridData = new DocGridData();
        docGridData.setNeedHead(false);
        dataSource.forEach(map -> {
            String jsonValue = CommonTools.Obj2String(map.get(target.getS_column_name()));
            if (!StringUtil.isEmpty(jsonValue)) {
                List<Map> data = JsonUtil.json2List(jsonValue);
                if (!CommonTools.isEmptyList(data)) {
                    int index = 0;
                    List<Map<String, DocHandlerData>> afterTransform = new ArrayList<>();
                    for (Map dataMap : data) {
                        if (0 == index) {
                            dataMap.forEach((key, value) -> {
                                DocGridColumn column = new DocGridColumn(CommonTools.Obj2String(key), CommonTools.Obj2String(key));
                                docGridData.getColumns().add(column);
                            });
                        }
                        Map<String, DocHandlerData> tmp = new LinkedHashMap<>();
                        dataMap.forEach((key, value) -> {
                            DocHandlerData<String> docHandlerData = new DocHandlerData();
                            docHandlerData.setAfterDataChange(CommonTools.Obj2String(value));
                            tmp.put(CommonTools.Obj2String(key), docHandlerData);
                        });
                        afterTransform.add(tmp);
                        index++;
                    }
                    docGridData.setDataList(afterTransform);
                }
            }
        });
        retVal.setAfterDataChange(docGridData);
        return retVal;
    }
}
