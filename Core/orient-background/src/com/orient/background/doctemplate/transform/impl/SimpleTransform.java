package com.orient.background.doctemplate.transform.impl;

import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.background.doctemplate.transform.IDocTransform;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.utils.CommonTools;
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
public class SimpleTransform implements IDocTransform<IBusinessColumn> {

    @Override
    public DocHandlerData doTransform(IBusinessColumn target, List<Map> dataSource) {
        DocHandlerData<String> retVal = new DocHandlerData<>();
        retVal.setOriginalData(dataSource);
        List<String> tmpData = new ArrayList<>();
        if (StringUtil.isEmpty(target.getSelector())) {
            dataSource.forEach(map -> tmpData.add(CommonTools.Obj2String(map.get(target.getS_column_name()))));
        } else {
            dataSource.forEach(map -> tmpData.add(CommonTools.Obj2String(map.get(target.getS_column_name() + "_display"))));
        }
        String afterTransformData = CommonTools.list2String(tmpData);
        retVal.setAfterDataChange(afterTransformData);
        return retVal;
    }
}
