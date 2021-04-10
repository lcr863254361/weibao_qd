package com.orient.background.doctemplate.transform.impl;

import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.background.doctemplate.transform.IDocTransform;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.metamodel.metadomain.Restriction;
import com.orient.utils.CommonTools;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2016-12-07 9:17 PM
 */
@Component
public class EnumTransform implements IDocTransform<IBusinessColumn> {

    @Override
    public DocHandlerData doTransform(IBusinessColumn target, List<Map> dataSource) {
        DocHandlerData<String> retVal = new DocHandlerData<>();
        retVal.setOriginalData(dataSource);
        List<String> tmpData = new ArrayList<>();
        dataSource.forEach(map -> {
            Restriction restriction = target.getRestriction();
            if (restriction.getType() == 1) {
                tmpData.add(CommonTools.Obj2String(map.get(target.getS_column_name())));
            } else {
                String displayJson = CommonTools.Obj2String(map.get(target.getS_column_name()));
                if (!StringUtil.isEmpty(displayJson)) {
                    Map<String, Object> jsonMap = JsonUtil.json2Map(displayJson);
                    tmpData.add(CommonTools.Obj2String(jsonMap.get("name")));
                }
            }
        });
        String afterTransformData = CommonTools.list2String(tmpData);
        retVal.setAfterDataChange(afterTransformData);
        return retVal;
    }
}
