package com.orient.web.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by enjoy on 2016/3/21 0021
 */
public class ExtComboboxResponseData<T> extends CommonResponseData  implements Serializable {

    private long totalProperty = 0l;
    private List<T> results = new ArrayList<>();

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
