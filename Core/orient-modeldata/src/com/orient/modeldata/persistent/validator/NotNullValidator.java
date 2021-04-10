package com.orient.modeldata.persistent.validator;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-05-06 14:51
 */
public class NotNullValidator extends Validator {

    @Override
    public String validate(Map<String, Object> dataMap) {
        String colName = this.getBusinessColumn().getS_column_name();
        Object val = dataMap.get(colName);
        if(val == null) {
            return this.getBusinessColumn().getDisplay_name()+"不可为空，数据："+dataMap;
        }
        if(val instanceof String) {
            if("".equals(val)) {
                return this.getBusinessColumn().getDisplay_name()+"不可为空，导入数据为："+dataMap;
            }
        }
        return null;
    }
}
