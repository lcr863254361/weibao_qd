package com.orient.web.modelDesc.column;

import com.orient.businessmodel.bean.IBusinessColumn;

import java.io.Serializable;

/**
 * 多选下拉框表单描述
 *
 * @author enjoy
 * @creare 2016-03-30 10:15
 */
public class MultiEnumColumnDesc extends EnumColumnDesc implements Serializable {

    private String selectJson = "";

    public String getSelectJson() {
        return selectJson;
    }

    public void setSelectJson(String selectJson) {
        this.selectJson = selectJson;
    }

    @Override
    public void specialInit(IBusinessColumn iBusinessColumn) {
        super.specialInit(iBusinessColumn);
        super.getAryOptions().entrySet().forEach(entry->{
            String key = entry.getKey();
            String value = entry.getValue();
            selectJson += key + "$TDM-MID$" + value + "$TDM-END$";
        });
    }
}
