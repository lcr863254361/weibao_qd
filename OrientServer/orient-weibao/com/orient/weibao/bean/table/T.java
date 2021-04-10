package com.orient.weibao.bean.table;

import java.io.Serializable;
/**
 * @author fangbin
 * 2020/3/23
 */
public class T implements Serializable {
    protected int rowspan=1;

    protected int colspan=1;
    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public  void setRowspan(int row){
        this.rowspan = row;

    }

    public int getRowspan() {
        return rowspan;
    }




    public T() {
    }

    public T(String content) {
        this.content = content;
    }

    protected String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
