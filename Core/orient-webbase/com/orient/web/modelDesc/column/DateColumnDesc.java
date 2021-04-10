package com.orient.web.modelDesc.column;

import com.orient.businessmodel.bean.IBusinessColumn;

import java.io.Serializable;

/**
 * 日期类型表单元素描述
 *
 * @author enjoy
 * @creare 2016-03-30 9:44
 */
public class DateColumnDesc extends ColumnDesc implements Serializable {

    //默认的时间格式
    private String dateFmt = "yyyy-MM-dd";

    public String getDateFmt() {
        return dateFmt;
    }

    public void setDateFmt(String dateFmt) {
        this.dateFmt = dateFmt;
    }

    @Override
    public void specialInit(IBusinessColumn iBusinessColumn) {

    }
}
