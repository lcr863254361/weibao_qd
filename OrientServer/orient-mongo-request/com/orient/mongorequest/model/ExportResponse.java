package com.orient.mongorequest.model;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-06-13 15:46
 */
public class ExportResponse extends CommonResponse {

    private Integer rowCount;
    private List<String> typeList;
    private List<String> pathList;
    private List<String> colList;

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public List<String> getPathList() {
        return pathList;
    }

    public void setPathList(List<String> pathList) {
        this.pathList = pathList;
    }

    public List<String> getColList() {
        return colList;
    }

    public void setColList(List<String> colList) {
        this.colList = colList;
    }

}
