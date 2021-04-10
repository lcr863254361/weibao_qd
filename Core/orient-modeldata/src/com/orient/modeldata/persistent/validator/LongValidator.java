package com.orient.modeldata.persistent.validator;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-05-06 14:51
 */
public class LongValidator extends Validator {

    @Override
    public String validate(Map<String, Object> dataMap) {
        String colName = this.getBusinessColumn().getS_column_name();
        Object val = dataMap.get(colName);
        if(val == null) {
            return null;
        }

        if(val instanceof String) {
            try {
                Long.valueOf((String) val);
            }
            catch (NumberFormatException e) {
                return this.getBusinessColumn().getDisplay_name()+"列为整型，"+val+"不是整数，导入数据为："+dataMap;
            }
            return null;
        }
        else if(val instanceof Integer) {
            return null;
        }
        else if(val instanceof Long) {
            return null;
        }

        return this.getBusinessColumn().getDisplay_name()+"列为整型，"+val+"不是整数，导入数据为："+dataMap;
    }
}
