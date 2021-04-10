package com.aptx.utils.orm;

import java.util.List;

public class PageResult<T> {
    private List<T> results;
    private long total;

    public PageResult(List<T> results, long total){
        this.results = results;
        this.total = total;
    }

    public PageResult(){

    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
