package com.orient.background.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author panduanduan
 * @create 2017-03-02 1:46 PM
 */
public class CompareResult<T> implements Serializable {

    private List<T> toCreateData = new ArrayList<>();

    private List<T> toUpdateData = new ArrayList<>();

    private List<T> toDeleteData = new ArrayList<>();

    private Map<String,T> invalidData = new HashMap<>();

    public List<T> getToCreateData() {
        return toCreateData;
    }

    public void setToCreateData(List<T> toCreateData) {
        this.toCreateData = toCreateData;
    }

    public List<T> getToUpdateData() {
        return toUpdateData;
    }

    public void setToUpdateData(List<T> toUpdateData) {
        this.toUpdateData = toUpdateData;
    }

    public List<T> getToDeleteData() {
        return toDeleteData;
    }

    public void setToDeleteData(List<T> toDeleteData) {
        this.toDeleteData = toDeleteData;
    }

    public Map<String, T> getInvalidData() {
        return invalidData;
    }

    public void setInvalidData(Map<String, T> invalidData) {
        this.invalidData = invalidData;
    }
}
