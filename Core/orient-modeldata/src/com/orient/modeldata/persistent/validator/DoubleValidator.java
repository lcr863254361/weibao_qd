package com.orient.modeldata.persistent.validator;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-05-06 14:51
 */
public class DoubleValidator extends Validator {

    @Override
    public String validate(Map<String, Object> dataMap) {
        String colName = this.getBusinessColumn().getS_column_name();
        Object val = dataMap.get(colName);
        if(val == null) {
            return null;
        }

        if(val instanceof String) {
            try {
                Double.valueOf((String) val);
            }
            catch (NumberFormatException e) {
                return this.getBusinessColumn().getDisplay_name()+"列为浮点型，"+val+"不是浮点数，导入数据为："+dataMap;
            }
            return null;
        }
        else if(val instanceof Integer) {
            return null;
        }
        else if(val instanceof Long) {
            return null;
        }
        else if(val instanceof Float) {
            return null;
        }
        else if(val instanceof Double) {
            return null;
        }

        return this.getBusinessColumn().getDisplay_name()+"列为浮点型，"+val+"不是浮点数，导入数据为："+dataMap;
    }
}
