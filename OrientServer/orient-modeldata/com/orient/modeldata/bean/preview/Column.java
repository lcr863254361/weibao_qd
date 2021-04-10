package com.orient.modeldata.bean.preview;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-23 10:23
 */
public class Column {
    private String header;
    private String dataIndex;
    private int flex = 1;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }

    public int getFlex() {
        return flex;
    }

    public void setFlex(int flex) {
        this.flex = flex;
    }
}
