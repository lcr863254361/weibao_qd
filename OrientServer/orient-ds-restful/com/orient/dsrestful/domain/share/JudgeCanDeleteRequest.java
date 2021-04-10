package com.orient.dsrestful.domain.share;

import com.orient.dsrestful.domain.CommonSchema;

/**
 * Created by GNY on 2018/3/27
 */
public class JudgeCanDeleteRequest extends CommonSchema {

    private String columnName;
    private String tableName;
    private int type;  //type=0表示判断数据类是否可以被删除；type=1表示判断普通属性、统计属性是否可以删除

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
