package com.orient.web.base;

import java.util.List;

/**
 * Created by duanduanpan on 16-3-16.
 * Ext 表格通用返回值
 */
public class ExtGridData<T> extends CommonResponseData {

    public ExtGridData(List<T> results, long totalProperty){
        this.results = results;
        this.totalProperty = totalProperty;
    }

    public ExtGridData(){

    }

    private long totalProperty;
    private List<T> results;

    public long getTotalProperty() {
        return totalProperty;
    }

    public void setTotalProperty(long totalProperty) {
        this.totalProperty = totalProperty;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

}
