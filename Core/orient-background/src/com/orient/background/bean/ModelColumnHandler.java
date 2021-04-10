package com.orient.background.bean;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author enjoyjava
 * @create 2016-12-01 2:37 PM
 */
public class ModelColumnHandler implements Serializable{

    private String columnName;

    private String docHandler;

    private String docHandlerId;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDocHandler() {
        return docHandler;
    }

    public void setDocHandler(String docHandler) {
        this.docHandler = docHandler;
    }

    public String getDocHandlerId() {
        return docHandlerId;
    }

    public void setDocHandlerId(String docHandlerId) {
        this.docHandlerId = docHandlerId;
    }


}
