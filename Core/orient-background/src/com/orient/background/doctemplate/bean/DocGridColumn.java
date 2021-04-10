package com.orient.background.doctemplate.bean;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2016-12-07 8:55 PM
 */
public class DocGridColumn implements Serializable{

    private String columnName;

    private String columnDisplayName;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnDisplayName() {
        return columnDisplayName;
    }

    public void setColumnDisplayName(String columnDisplayName) {
        this.columnDisplayName = columnDisplayName;
    }

    public DocGridColumn(String columnName, String columnDisplayName) {
        this.columnName = columnName;
        this.columnDisplayName = columnDisplayName;
    }
}
