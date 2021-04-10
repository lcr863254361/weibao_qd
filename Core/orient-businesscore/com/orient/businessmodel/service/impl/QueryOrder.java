package com.orient.businessmodel.service.impl;

/**
 * represent a sql order
 *
 * @author Seraph
 *         2016-08-24 下午4:09
 */
public class QueryOrder {

    public QueryOrder(String colName, boolean asc){
        this.colName = colName;
        this.asc = asc;
    }

    public static QueryOrder asc(String colName){
        return new QueryOrder(colName, true);
    }

    public static QueryOrder desc(String colName){
        return new QueryOrder(colName, false);
    }

    public static final QueryOrder INVALID_ORDER = new QueryOrder(null, false);

    private boolean asc;
    private String colName;

    public boolean isAsc() {
        return asc;
    }

    public String getColName() {
        return colName;
    }
}
