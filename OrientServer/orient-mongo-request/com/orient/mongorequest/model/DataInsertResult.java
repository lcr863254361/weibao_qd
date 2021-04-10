package com.orient.mongorequest.model;

import java.io.Serializable;
import java.util.List;

public class DataInsertResult implements Serializable {

    private String fileName;
    private List<String> columnList;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<String> columnList) {
        this.columnList = columnList;
    }

}
