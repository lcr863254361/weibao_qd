package com.orient.web.modelDesc.column;

import com.orient.businessmodel.bean.IBusinessColumn;

import java.io.Serializable;

/**
 * 普通字符串文本描述
 *
 * @author enjoy
 * @creare 2016-03-30 9:38
 */
public class SimpleColumnDesc extends ColumnDesc implements Serializable {

    private String selector;

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    @Override
    public void specialInit(IBusinessColumn iBusinessColumn) {
        selector = iBusinessColumn.getCol().getSelector();
    }
}
