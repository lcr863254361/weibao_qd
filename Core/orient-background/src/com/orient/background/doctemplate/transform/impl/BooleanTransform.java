package com.orient.background.doctemplate.transform.impl;

import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.background.doctemplate.transform.IDocTransform;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.utils.CommonTools;
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
public class BooleanTransform implements IDocTransform<IBusinessColumn> {

    @Override
    public DocHandlerData doTransform(IBusinessColumn target, List<Map> dataSource) {
        DocHandlerData<String> retVal = new DocHandlerData<>();
        retVal.setOriginalData(dataSource);
        List<String> tmpData = new ArrayList<>();
        dataSource.forEach(map -> tmpData.add(CommonTools.Obj2String("1".equals(map.get(target.getS_column_name())) ? "是" : "否")));
        String afterTransformData = CommonTools.list2String(tmpData);
        retVal.setAfterDataChange(afterTransformData);
        return retVal;
    }
}
