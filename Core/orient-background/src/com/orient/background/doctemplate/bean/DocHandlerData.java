package com.orient.background.doctemplate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2016-12-07 8:20 PM
 */
public class DocHandlerData<T> implements Serializable{

    private List<Map> originalData = new ArrayList<>();

    private T afterDataChange;

    public List<Map> getOriginalData() {
        return originalData;
    }

    public void setOriginalData(List<Map> originalData) {
        this.originalData = originalData;
    }

    public T getAfterDataChange() {
        return afterDataChange;
    }

    public void setAfterDataChange(T afterDataChange) {
        this.afterDataChange = afterDataChange;
    }
}
