package com.orient.web.base;

import java.io.Serializable;

/**
 * Created by enjoy on 2016/3/23 0023.
 * Ajax 请求泛型结果类
 */
public class AjaxResponseData<T> extends CommonResponseData implements Serializable {

    private T results;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public AjaxResponseData() {

    }

    public AjaxResponseData(T results) {
        this.results = results;
    }
}
