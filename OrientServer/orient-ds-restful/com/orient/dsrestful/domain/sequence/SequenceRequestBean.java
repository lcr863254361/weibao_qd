package com.orient.dsrestful.domain.sequence;

import com.orient.dsrestful.domain.CommonSchema;

/**
 * Created by GNY on 2018/3/26
 */
public class SequenceRequestBean extends CommonSchema {

    private String columnName;
    private String tableName;
    private int initialValue;
    private int intervalValue = 1;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public int getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(int initialValue) {
        this.initialValue = initialValue;
    }

    public int getIntervalValue() {
        return intervalValue;
    }

    public void setIntervalValue(int intervalValue) {
        this.intervalValue = intervalValue;
    }

}
