package com.orient.web.modelDesc.column;

import com.orient.businessmodel.bean.IBusinessColumn;

import java.io.Serializable;

/**
 * 单选下拉框表单元素描述
 *
 * @author enjoy
 * @creare 2016-03-30 10:12
 */
public class SingleEnumColumnDesc extends EnumColumnDesc implements Serializable {

    @Override
    public void specialInit(IBusinessColumn iBusinessColumn) {
        super.specialInit(iBusinessColumn);
    }
}
