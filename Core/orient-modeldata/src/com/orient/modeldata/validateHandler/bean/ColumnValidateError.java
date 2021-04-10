package com.orient.modeldata.validateHandler.bean;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class ColumnValidateError extends ValidateError {

    private String columnId;

    private String columnSName;

    private String columnDisplayName;

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnSName() {
        return columnSName;
    }

    public void setColumnSName(String columnSName) {
        this.columnSName = columnSName;
    }

    public String getColumnDisplayName() {
        return columnDisplayName;
    }

    public void setColumnDisplayName(String columnDisplayName) {
        this.columnDisplayName = columnDisplayName;
    }

    public ColumnValidateError(String columnId, String columnSName, String columnDisplayName) {
        this.columnId = columnId;
        this.columnDisplayName = columnDisplayName;
        this.columnSName = columnSName;
    }
}
