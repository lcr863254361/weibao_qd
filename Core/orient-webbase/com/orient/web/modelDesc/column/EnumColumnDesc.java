package com.orient.web.modelDesc.column;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.metamodel.metadomain.Restriction;
import com.orient.metamodel.operationinterface.IEnum;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 下拉框表单元素描述
 *
 * @author enjoy
 * @creare 2016-03-30 10:05
 */
public class EnumColumnDesc extends ColumnDesc implements Serializable {

    //下拉框选择元素
    private Map<String, String> aryOptions = new LinkedHashMap<>();

    public Map<String, String> getAryOptions() {
        return aryOptions;
    }

    public void setAryOptions(Map<String, String> aryOptions) {
        this.aryOptions = aryOptions;
    }

    @Override
    public void specialInit(IBusinessColumn iBusinessColumn) {
        Restriction restriction = iBusinessColumn.getRestriction();
        //得到枚举值
        List<IEnum> enums = restriction.getAllEnums();
        enums.forEach(iEnum -> {
            if (iEnum.isOpen()) {
                String value = iEnum.getValue();
                String display_value = iEnum.getDisplayValue();
                aryOptions.put(value, display_value);
            }
        });
    }
}
