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
public class CheckColumnTransform implements IDocTransform<IBusinessColumn> {

    @Override
    public DocHandlerData doTransform(IBusinessColumn target, List<Map> dataSource) {
        DocHandlerData<DocGridData> retVal = new DocHandlerData<>();
        retVal.setOriginalData(dataSource);
        DocGridData docGridData = new DocGridData();
        dataSource.forEach(map -> {
            String jsonValue = CommonTools.Obj2String(map.get(target.getS_column_name()));
            if (!StringUtil.isEmpty(jsonValue)) {
                List<Map> data = JsonUtil.json2List(jsonValue);
                if (!CommonTools.isEmptyList(data)) {
                    DocGridColumn checkColumn = new DocGridColumn("labelName", "检查项");
                    DocGridColumn resultColumn = new DocGridColumn("inputValue", "检查结果");
                    docGridData.getColumns().add(checkColumn);
                    docGridData.getColumns().add(resultColumn);
                    List<Map<String, DocHandlerData>> afterTransform = new ArrayList<>();
                    for (Map dataMap : data) {
                        Map<String, DocHandlerData> tmp = new LinkedHashMap<>();
                        dataMap.forEach((key, value) -> {
                            DocHandlerData<String> docHandlerData = new DocHandlerData();
                            docHandlerData.setAfterDataChange(CommonTools.Obj2String(value));
                            tmp.put(CommonTools.Obj2String(key), docHandlerData);
                        });
                        afterTransform.add(tmp);
                    }
                    docGridData.setDataList(afterTransform);
                }
            }
        });
        retVal.setAfterDataChange(docGridData);
        return retVal;
    }
}
