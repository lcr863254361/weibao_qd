package com.orient.web.modelDesc.column;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 单选类型表单元素描述
 *
 * @author enjoy
 * @creare 2016-03-30 9:43
 */
public class BooleanColumnDesc extends ColumnDesc implements Serializable {

    //单选 键值对
    private Map<String, String> aryOptions= new LinkedHashMap<>();

    public Map<String, String> getAryOptions() {
        return aryOptions;
    }

    public void setAryOptions(Map<String, String> aryOptions) {
        this.aryOptions = aryOptions;
    }

    @Override
    public void specialInit(IBusinessColumn iBusinessColumn) {
        EnumInter.BusinessModelEnum.BusinessColumnEnum colEnum = (EnumInter.BusinessModelEnum.BusinessColumnEnum) iBusinessColumn.getColType();
        if (EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Boolean == colEnum) {
            //如果是是否类型
            aryOptions.put("1", "是");
            aryOptions.put("0", "否");
        }
    }
}
